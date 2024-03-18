package com.lgp.myapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapi.mapper.UserMapper;
import com.lgp.myapicommon.model.entity.User;
import com.lgp.myapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @auther: lgp
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey, accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}
