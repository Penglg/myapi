package com.lgp.myapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 修改请求
 * @auther: 赖高鹏
 * @date: 2024/3/4 19:32
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

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
