package com.xiaobingby.auth.service;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.security.dto.UserDetailsDto;
import com.xiaobingby.upms.api.feign.IUserFeign;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

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

    @Autowired
    private IUserFeign iUserFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        R<SysUserDetailsDto> sysUserDetailsDtoR = iUserFeign.loadUserByUsername(username);

        if (sysUserDetailsDtoR == null || sysUserDetailsDtoR.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserDetails userDetails = generatorUserDetails(sysUserDetailsDtoR.getData());

        return userDetails;
    }

    private UserDetails generatorUserDetails(SysUserDetailsDto sysUserDetailsDto) {
        // 是否可用-账号是否被删除
        boolean enabled = true;
        // 账号没有过期-账号是否被冻结
        boolean accountNonExpired = true;
        // 密码是否过
        boolean credentialsNonExpired = true;
        // 账号是不是锁定了
        boolean accountNonLocked = true;
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sysUserDetailsDto.getRoles())) {
            sysUserDetailsDto.getRoles().parallelStream().forEach(item -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(item.getRoleCode()));
            });
        }
        if (!CollectionUtils.isEmpty(sysUserDetailsDto.getPermissions())) {
            sysUserDetailsDto.getPermissions().parallelStream().forEach(item -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(item.getPermCode()));
            });
        }
        return new UserDetailsDto(sysUserDetailsDto.getId(), sysUserDetailsDto.getUsername(), sysUserDetailsDto.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
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
