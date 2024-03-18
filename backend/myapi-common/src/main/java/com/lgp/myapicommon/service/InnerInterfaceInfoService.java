package com.lgp.myapicommon.service;

import com.lgp.myapicommon.model.entity.InterfaceInfo;

/**
 * 接口服务
 *
 * @author lgp
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询接口是否存在
     * @param path 调用路径
     * @param method 调用放啊
     * @return InterfaceInfo
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
