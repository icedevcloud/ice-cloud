package com.xiaobingby.common.security.component;

import com.xiaobingby.common.core.api.R;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.*;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝
 */
public class RAccessDeniedHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {

    private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        try {
            ResponseEntity<OAuth2Exception> translate = exceptionTranslator.translate(authException);
            R<Object> objectR = new R<>();
            objectR.setCode(translate.getStatusCodeValue());
            objectR.setData(translate.getBody());
            objectR.setMsg(translate.getBody().getMessage());
            ResponseEntity<?> responseEntity = enhanceResponse(translate, authException);
            HttpHeaders headers = responseEntity.getHeaders();
            HttpStatus statusCode = responseEntity.getStatusCode();
            ResponseEntity<R<Object>> rResponseEntity = new ResponseEntity<R<Object>>(objectR, headers, statusCode);
            exceptionRenderer.handleHttpEntityResponse(rResponseEntity, new ServletWebRequest(request, response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
