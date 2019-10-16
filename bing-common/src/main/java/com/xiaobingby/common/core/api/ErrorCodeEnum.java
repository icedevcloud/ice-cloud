package com.xiaobingby.common.core.api;

public enum ErrorCodeEnum implements IErrorCode {

    SUCCESS(200, "执行成功"),
    FAILED(500, "操作失败");

    private Integer code;

    private String message;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCodeEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
