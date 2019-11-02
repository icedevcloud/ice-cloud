package com.icedevcloud.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icedevcloud.auth.validate.ValidateCodeException;
import com.icedevcloud.common.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常Handler处理类
 *
 * @author XiaoBingBy
 * @date 2018-11-15 22:50
 * @since 1.0
 */
@Component
public class AuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof ValidateCodeException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            R<Object> objectR = new R<>();
            objectR.setCode(HttpStatus.BAD_REQUEST.value());
            objectR.setData(exception);
            objectR.setMessage(exception.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(objectR));
        }
    }

}
