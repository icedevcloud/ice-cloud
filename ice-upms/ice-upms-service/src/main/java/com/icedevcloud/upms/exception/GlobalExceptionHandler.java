package com.icedevcloud.upms.exception;

import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.exception.ApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public R apiExceptionHandler(ApiException e) {
        return R.failed(e.getErrorCode());
    }

}
