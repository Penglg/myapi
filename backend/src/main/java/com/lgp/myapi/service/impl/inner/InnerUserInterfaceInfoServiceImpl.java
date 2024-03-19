package com.lgp.myapi.service.impl.inner;

import com.lgp.myapi.service.UserInterfaceInfoService;
import com.lgp.myapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 *
 * @auther: lgp
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    @Override
    public boolean leftNumToInvoke(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.leftNumToInvoke(interfaceInfoId, userId);
    }
}
