package com.lgp.myapicommon.service;

/**
 * 用户接口服务
 *
 * @author lgp
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计次数+1
     * @param interfaceInfoId 接口主键
     * @param userId 用户主键
     * @return boolean
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
     * 是否还有接口调用次数
     * @param interfaceInfoId 接口主键
     * @param userId 用户主键
     * @return boolean
     */
    boolean leftNumToInvoke(long interfaceInfoId, long userId);
}
