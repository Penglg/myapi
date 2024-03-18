package com.lgp.myapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapi.mapper.InterfaceInfoMapper;
import com.lgp.myapicommon.model.entity.InterfaceInfo;
import com.lgp.myapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @auther: lgp
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {


    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getUrl, path)
                .eq(InterfaceInfo::getMethod, method);

        return interfaceInfoMapper.selectOne(wrapper);
    }
}
