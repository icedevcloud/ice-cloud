package com.icedevcloud.common.core.api;

/**
 * REST API 错误码接口
 *
 * @see <p>源码</p> com.baomidou.mybatisplus.extension.api.IErrorCode
 */
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
