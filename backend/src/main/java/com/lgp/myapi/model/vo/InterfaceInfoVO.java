package com.lgp.myapi.model.vo;

import com.lgp.myapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口信息封装试图
 *
 * @auther: lgp
 */
// 使用父类的属性
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo{

    /**
     * 接口被调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
