package com.lgp.myapicommon.service;

import com.lgp.myapicommon.model.entity.User;


/**
 * 用户服务
 *
 * @author lgp
 */
public interface InnerUserService {
    /**
     * 获取密钥对应User
     * @param accessKey 密钥
     * @return user
     */
    User getInvokeUser(String accessKey);

}
