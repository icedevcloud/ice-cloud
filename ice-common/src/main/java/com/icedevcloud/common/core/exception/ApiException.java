package com.icedevcloud.common.core.exception;

import com.icedevcloud.common.core.api.ErrorCode;
import com.icedevcloud.common.core.api.IErrorCode;

/**
 * REST API 请求异常类
 */
public class ApiException extends RuntimeException {

    /**
     * 错误码
     */
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        this.errorCode = new ErrorCode(500, message);
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.errorCode = new ErrorCode(code, message);
    }

    public ApiException(Integer code, String message, Exception e) {
        super(message);
        this.errorCode = new ErrorCode(code, message);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
