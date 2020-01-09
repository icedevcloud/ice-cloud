package com.icedevcloud.common.core.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodeEnum implements IErrorCode {

    /**
     * 成功
     */
    SUCCESS(200, "执行成功"),
    /**
     * 失败
     */
    FAILED(500, "操作失败");

    private Integer code;

    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
