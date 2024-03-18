package com.lgp.myapigateway;

import com.lgp.myapiclientsdk.utils.SignUtils;
import com.lgp.myapicommon.model.entity.InterfaceInfo;
import com.lgp.myapicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.lgp.myapicommon.service.InnerUserService;
import com.lgp.myapicommon.service.InnerInterfaceInfoService;
import com.lgp.myapicommon.service.InnerUserInterfaceInfoService;

/**
 * 全局拦截器
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService userService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;

    private static final String HOST_NAME = "http://localhost:8123";

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String requestId = request.getId();
        String path = request.getPath().value();
        String method = String.valueOf(request.getMethod());
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求唯一标识: " + requestId);
        log.info("请求路径: " + path);
        log.info("请求方法: " + method);
        log.info("请求参数: " + queryParams);
        log.info("请求来源地址: " + sourceAddress);

        // 黑白名单
        ServerHttpResponse response = exchange.getResponse();
//        // 请求地址不在白名单内，返回错误信息
//        if (!IP_WHITE_LIST.contains(sourceAddress)) {
//            response.setStatusCode(HttpStatus.FORBIDDEN);
//            return response.setComplete();
//        }

        // 用户鉴权
        String accessKey = request.getHeaders().getFirst("accessKey");
        String nonce = request.getHeaders().getFirst("nonce");
        String timestamp = request.getHeaders().getFirst("timestamp");
        String sign = request.getHeaders().getFirst("sign");
        String body = request.getHeaders().getFirst("body");
        // 查看系统是否已分配密钥给用户
        User invokeUser = null;
        try {
            invokeUser = userService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }

        // 随机数是四位的，因此不能大于10000
        if (nonce != null && Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }
        // 时间和当前时间之差不能超过 5 分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES = (long) (60 * 5);
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }

        // 从数据库获取secret生成签名进行比对
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }

        // 请求的模拟接口是否存在及其请求方法是否匹配
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(HOST_NAME + path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleInvokeError(response);
        }

        // 判断是否有调用次数
        boolean leftNumToInvoke = false;
        try {
            leftNumToInvoke = userInterfaceInfoService.leftNumToInvoke(interfaceInfo.getId(), invokeUser.getId());
        } catch (Exception e) {
            log.error("getLeftNumToInvoke error", e);
        }
        if (!leftNumToInvoke) {
            return handleInvokeError(response);
        }

        // 5. 请求转发，调用模拟接口
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId,
                                     long invokeUserId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 在调用了转发的接口之后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值中写数据
                            // 返回结果，拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用成功后调用次数+1
                                try {
                                    userInterfaceInfoService.invokeCount(interfaceInfoId, invokeUserId);
                                } catch (Exception e) {
                                    log.error("invokeCount error", e);
                                }
                                
                                // 返回值
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                //释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                // 构建返回日志
                                StringBuilder sb = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);
                                sb.append(data);
                                log.info(sb.toString(), rspArgs.toArray());
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 将response对象设置为装饰过的response
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 降级处理数据
            return chain.filter(exchange);

        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}