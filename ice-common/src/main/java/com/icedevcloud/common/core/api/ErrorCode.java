package com.icedevcloud.common.core.api;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ErrorCode implements IErrorCode {

    private Integer code;

    private String message;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
