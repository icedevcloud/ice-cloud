package com.icedevcloud.common.security.util;

import com.icedevcloud.common.security.dto.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security 工具类获取用户信息
 *
 * @author XiaoBingBy
 * @date 2019-01-14 23:14
 * @since 1.0
 */
public class SecurityUtils {

    /**
     * 获取 Authentication
     *
     * @return
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取 UserDetailsDto
     *
     * @return
     */
    public static UserDetailsDto getUserDetails() {
        Authentication authentication = getAuthentication();
        UserDetailsDto principal = (UserDetailsDto) authentication.getPrincipal();
        return principal;
    }

}
