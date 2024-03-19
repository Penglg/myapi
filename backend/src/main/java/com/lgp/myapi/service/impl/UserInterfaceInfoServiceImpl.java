package com.lgp.myapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapicommon.model.entity.UserInterfaceInfo;
import com.lgp.myapi.service.UserInterfaceInfoService;
import com.lgp.myapi.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86158
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-03-04 18:52:46
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();

        // 创建时，所有参数必须非空
        if (add) {
            if (userId == null || interfaceInfoId == null || totalNum == null || leftNum == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            // TODO: 2024/3/4 : 查询user和interfaceInfo，判断是否存在，不存在则抛异常
            if (userId <= 0 || interfaceInfoId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }

        if (totalNum < 0 || leftNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "调用次数不能小于0");

        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId)
                // TODO: 2024/3/4 考虑并发，加锁 
                // 大于0
                .gt(UserInterfaceInfo::getLeftNum, 0)
                .setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

    @Override
    public boolean leftNumToInvoke(long interfaceInfoId, long userId) {
        // 校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return userInterfaceInfo.getLeftNum() > 0;
    }

    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
    }
}




