package com.lgp.myapi.service;

import com.lgp.myapi.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86158
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-03-04 18:52:46
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId, long userId);
}
