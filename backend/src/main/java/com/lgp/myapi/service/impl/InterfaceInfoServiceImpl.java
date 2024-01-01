package com.lgp.myapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapi.service.InterfaceInfoService;
import com.lgp.myapi.model.entity.InterfaceInfo;
import com.lgp.myapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author 86158
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-01-01 16:45:26
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String method = interfaceInfo.getMethod();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String description = interfaceInfo.getDescription();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, method, url, requestHeader, responseHeader, description)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(url) && url.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }
}




