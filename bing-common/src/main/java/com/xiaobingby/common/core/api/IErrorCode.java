package com.xiaobingby.common.core.api;

public interface IErrorCode {

    /**
     * 错误编码 -1、失败 0、成功
     */
    Integer getCode();

    /**
     * 错误描述
     */
    String getMessage();

}
