package com.lgp.myapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 创建请求
 * @auther: 赖高鹏
 * @date: 2024/3/4 19:32
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {
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
}
