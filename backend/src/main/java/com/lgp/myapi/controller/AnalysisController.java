package com.lgp.myapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lgp.myapi.annotation.AuthCheck;
import com.lgp.myapi.common.BaseResponse;
import com.lgp.myapi.common.ErrorCode;
import com.lgp.myapi.common.ResultUtils;
import com.lgp.myapi.exception.BusinessException;
import com.lgp.myapi.mapper.UserInterfaceInfoMapper;
import com.lgp.myapi.model.vo.InterfaceInfoVO;
import com.lgp.myapi.service.InterfaceInfoService;
import com.lgp.myapicommon.model.entity.InterfaceInfo;
import com.lgp.myapicommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据分析控制器
 *
 * @auther: lgp
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(5);
        // 获取interfaceId与userInterfaceInfo映射的map
        Map<Long, List<UserInterfaceInfo>> interfaceIdObjMap = userInterfaceInfos.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        // 获取各个接口信息
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, interfaceIdObjMap.keySet());
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(queryWrapper);

        if (CollectionUtils.isEmpty(interfaceInfos)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<InterfaceInfoVO> voList = interfaceInfos.stream().map(interfaceInfo -> {
            InterfaceInfoVO vo = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, vo);
            UserInterfaceInfo userInterfaceInfo = interfaceIdObjMap.get(interfaceInfo.getId()).get(0);
            vo.setTotalNum(userInterfaceInfo.getTotalNum());
            return vo;
        }).collect(Collectors.toList());

        return ResultUtils.success(voList);
    }
}
