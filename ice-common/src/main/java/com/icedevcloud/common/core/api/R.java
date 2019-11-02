package com.icedevcloud.common.core.api;

import com.icedevcloud.common.core.exception.ApiException;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * REST API 返回结果
 *
 * @param <T>
 * @see <p>源码</p> com.baomidou.mybatisplus.extension.api.R
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 业务错误码
     */
    private Integer code;

    /**
     * 结果集
     */
    private T data;

    /**
     * 描述
     */
    private String message;

    public R() {
        // to do nothing
    }

    public R(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ErrorCodeEnum.FAILED);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static <T> R<T> ok(T data) {
        ErrorCodeEnum aec = ErrorCodeEnum.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ErrorCodeEnum.FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> R<T> failed(String message) {
        return restResult(null, ErrorCodeEnum.FAILED.getCode(), message);
    }

    public static <T> R<T> failed(IErrorCode errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> R<T> restResult(T data, IErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMessage());
    }

    private static <T> R<T> restResult(T data, Integer code, String message) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }

    public boolean ok() {
        return ErrorCodeEnum.SUCCESS.getCode() == code;
    }

    /**
     * 服务间调用非业务正常，异常直接释放
     */
    public T serviceData() {
        if (!ok()) {
            throw new ApiException(this.message);
        }
        return data;
    }

}
