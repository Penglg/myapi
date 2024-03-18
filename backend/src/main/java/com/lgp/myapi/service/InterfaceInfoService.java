package com.lgp.myapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgp.myapicommon.model.entity.InterfaceInfo;

/**
* @author 86158
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-01-01 16:45:26
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
