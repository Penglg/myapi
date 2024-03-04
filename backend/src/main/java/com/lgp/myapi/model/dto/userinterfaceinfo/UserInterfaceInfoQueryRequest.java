package com.lgp.myapi.model.dto.userinterfaceinfo;

import com.lgp.myapi.common.PageRequest;

import java.io.Serializable;

/**
 * @description:
 * @auther: 赖高鹏
 * @date: 2024/3/4 19:32
 */
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 调用者
     */
    private Long userId;

    /**
     * 被调用接口
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 接口调用状态，0-正常，1-禁用
     */
    private Integer status;
}
