package com.lgp.myapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装id参数
 * 便于json参数传递
 *
 * @author lgp
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}