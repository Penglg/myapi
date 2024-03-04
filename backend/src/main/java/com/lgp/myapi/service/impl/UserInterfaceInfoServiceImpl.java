package com.lgp.myapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapi.model.entity.UserInterfaceInfo;
import com.lgp.myapi.service.UserInterfaceInfoService;
import com.lgp.myapi.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 86158
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-03-04 18:52:46
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

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
}




