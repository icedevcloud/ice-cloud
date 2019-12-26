package com.icedevcloud.upms.exception;

import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.exception.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R apiExceptionHandler(Exception e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            return R.failed(apiException.getErrorCode());
        } else {
            return R.failed(e.getMessage());
        }
    }

}
