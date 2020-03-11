package com.icedevcloud.auth.aspect;

import com.icedevcloud.common.core.api.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * 更改token返回的数据格式
 */
@Aspect
@Component
public class TokenAspect {

    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.*AccessToken(..))")
    public void tokenPointcut() {

    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.handleException(..))")
    public void oAuth2ExceptionPointcut() {

    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     */
    @Around("tokenPointcut()")
    public Object tokenAround(ProceedingJoinPoint pjp) throws Throwable {
        Object o = pjp.proceed();
        if (o instanceof ResponseEntity) {
            HttpHeaders headers = ((ResponseEntity) o).getHeaders();
            OAuth2AccessToken accessToken = (OAuth2AccessToken) ((ResponseEntity) o).getBody();
            return new ResponseEntity<R<OAuth2AccessToken>>(R.ok(accessToken), headers, HttpStatus.OK);
        }
        return o;
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     */
    //@Around("oAuth2ExceptionPointcut()")
//    public Object oAuth2ExceptionAround(ProceedingJoinPoint pjp) throws Throwable {
//        Object o = pjp.proceed();
//        if (o instanceof ResponseEntity) {
//            HttpHeaders headers = ((ResponseEntity) o).getHeaders();
//            OAuth2Exception ase = (OAuth2Exception) ((ResponseEntity) o).getBody();
//            HttpStatus statusCode = ((ResponseEntity) o).getStatusCode();
//            int status = ((ResponseEntity) o).getStatusCodeValue();
//            return new ResponseEntity<R<OAuth2Exception>>(R.error(status, ase, ase.getMessage()), headers, statusCode);
//        }
//        return o;
//    }

}