package com.lgp.myapinterface.controller;

import com.lgp.myapiclientsdk.model.User;
import com.lgp.myapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @auther: 赖高鹏
 * @date: 2024/2/7 11:16
 */
@RestController
@RequestMapping("/name")
public class NameController {
        @GetMapping("/get")
        public String getNameByGet(String name) {
            return "GET 你的名字是" + name;
        }

        @PostMapping("/post")
        public String getNameByPost(@RequestParam String name){
            return "POST 获取的名字" + name ;
        }

        @PostMapping("/user")
        public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
            String accessKey = request.getHeader("accessKey");
            String nonce = request.getHeader("nonce");
            String timestamp = request.getHeader("timestamp");
            String sign = request.getHeader("sign");
            String body = request.getHeader("body");
            // todo 实际情况应该是去数据库中查是否已分配给用户
            if (!accessKey.equals("admin")) {
                throw new RuntimeException("无权限");
            }
            if (Long.parseLong(nonce) > 10000) {
                throw new RuntimeException("无权限");
            }
            // todo 时间和当前时间之差不能超过 5 分钟
            // if (timestamp) {
            //
            // }

            // todo 实际情况中是从数据库中查出 secretKey
            String serverSign = SignUtils.getSign(body, "abcdefgh");
            if (!sign.equals(serverSign)) {
                throw new RuntimeException("无权限");
            }
            return "POST 用户名字是" + user.getUsername();
        }
}
