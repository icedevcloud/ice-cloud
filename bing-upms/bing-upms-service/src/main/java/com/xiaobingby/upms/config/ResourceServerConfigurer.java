package com.xiaobingby.upms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaobingby.common.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

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
                .authenticationEntryPoint(new CAuthenticationEntryPoint())
                .accessDeniedHandler(new CAccessDeniedHandler())
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

    public class CAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

        @Override
        public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
            try {
                ResponseEntity<OAuth2Exception> translate = exceptionTranslator.translate(authenticationException);
                OAuth2Exception body = translate.getBody();
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Pragma", "no-cache");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(translate.getStatusCodeValue());
                R<Object> objectR = new R<>();
                objectR.setCode(translate.getStatusCodeValue());
                objectR.setData(body);
                objectR.setMsg(body.getMessage());
                //R.error(translate.getStatusCodeValue(), body, body.getMessage();
                response.getWriter().write(objectMapper.writeValueAsString(objectR));
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class CAccessDeniedHandler implements AccessDeniedHandler {

        private WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator = new DefaultWebResponseExceptionTranslator();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            try {
                ResponseEntity<OAuth2Exception> translate = exceptionTranslator.translate(accessDeniedException);
                OAuth2Exception body = translate.getBody();
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Pragma", "no-cache");
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(translate.getStatusCodeValue());
                R<Object> objectR = new R<>();
                objectR.setCode(translate.getStatusCodeValue());
                objectR.setData(body);
                objectR.setMsg(body.getMessage());
                //R.error(translate.getStatusCodeValue(), body, body.getMessage();
                response.getWriter().write(objectMapper.writeValueAsString(objectR));
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
