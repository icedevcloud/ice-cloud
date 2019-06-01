package com.xiaobingby.common.core.api;

import com.xiaobingby.common.core.exception.ApiException;
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
    private String msg;

    public R() {
        // to do nothing
    }

    public R(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ErrorCode.FAILED);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static <T> R<T> ok(T data) {
        ErrorCode aec = ErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ErrorCode.FAILED;
        }
        return restResult(data, aec);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ErrorCode.FAILED.getCode(), msg);
    }

    public static <T> R<T> failed(IErrorCode errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> R<T> restResult(T data, IErrorCode errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> R<T> restResult(T data, Integer code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean ok() {
        return ErrorCode.SUCCESS.getCode() == code;
    }

    /**
     * 服务间调用非业务正常，异常直接释放
     */
    public T serviceData() {
        if (!ok()) {
            throw new ApiException(this.msg);
        }
        return data;
    }

}
