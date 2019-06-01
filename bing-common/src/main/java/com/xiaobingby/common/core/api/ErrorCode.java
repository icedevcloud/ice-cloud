package com.xiaobingby.common.core.api;

public enum ErrorCode implements IErrorCode {

    SUCCESS(200, "执行成功"),
    FAILED(500, "操作失败");

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
