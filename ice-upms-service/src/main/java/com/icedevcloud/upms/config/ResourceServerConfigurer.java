package com.icedevcloud.upms.config;

import com.icedevcloud.common.core.constant.SecurityConstants;
import com.icedevcloud.common.security.component.RAccessDeniedHandler;
import com.icedevcloud.common.security.component.RAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private RAuthenticationEntryPoint rAuthenticationEntryPoint = new RAuthenticationEntryPoint();

    private RAccessDeniedHandler rAccessDeniedHandler = new RAccessDeniedHandler();

    @Autowired
    private IgnorePropertiesConfig ignorePropertiesConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        ignorePropertiesConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(rAuthenticationEntryPoint)
                .accessDeniedHandler(rAccessDeniedHandler)
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
        tokenStore.setPrefix(SecurityConstants.TOKEN_STORE_PREFIX); // 自定义Redis前缀
        return tokenStore;
    }

}
