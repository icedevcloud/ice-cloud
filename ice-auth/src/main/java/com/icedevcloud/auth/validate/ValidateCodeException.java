package com.icedevcloud.auth.validate;

import org.springframework.security.core.AuthenticationException;


/**
 * 验证码异常类
 *
 * @author XiaoBingBy
 * @date 2018-12-17 22:59
 * @since 1.0
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 1;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
