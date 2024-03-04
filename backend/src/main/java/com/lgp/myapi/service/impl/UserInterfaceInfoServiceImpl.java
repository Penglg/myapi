package com.lgp.myapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}




