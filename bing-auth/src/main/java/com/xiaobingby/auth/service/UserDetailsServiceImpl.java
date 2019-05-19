package com.xiaobingby.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author XiaoBingBy
 * @date 2018-08-26 12:00
 * @since 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mockUser(username);
    }

    /**
     * 构造假用户信息
     *
     * @param username
     * @return
     */
    private UserDetails mockUser(String username) {
        // 根据用户名查找用户信息
        // 根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        logger.info("username: {} password: {}", username, password);
        // 是否可用-账号是否被删除
        boolean enabled = true;
        // 账号没有过期-账号是否被冻结
        boolean accountNonExpired = true;
        // 密码是否过
        boolean credentialsNonExpired = true;
        // 账号是不是锁定了
        boolean accountNonLocked = true;
        return new User(username, password,
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER,sys_super_admin"));
    }

}
