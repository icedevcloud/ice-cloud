package com.xiaobingby.upms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaobingby.common.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**", "/upms/user/loadUserByUsername/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint())
                .accessDeniedHandler(new AccessDeniedHandler())
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices());
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(redisTokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix("bing_token:"); // 自定义Redis前缀
        return tokenStore;
    }

    public class AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

        private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

        private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
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

    public class AccessDeniedHandler extends OAuth2AccessDeniedHandler {

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


}
