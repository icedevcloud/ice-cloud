package com.xiaobingby.upms.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import com.xiaobingby.upms.dto.UserDto;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.entity.SysRole;
import com.xiaobingby.upms.entity.SysUser;
import com.xiaobingby.upms.entity.SysUserRole;
import com.xiaobingby.upms.mapper.SysUserMapper;
import com.xiaobingby.upms.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sys_user 用户表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public SysUserDetailsDto loadUserByUsername(String username) {
        SysUserDetailsDto sysUserDetailsDto = null;

        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username)
        );

        if (sysUser != null) {
            sysUserDetailsDto = new SysUserDetailsDto();
            BeanUtils.copyProperties(sysUser, sysUserDetailsDto);

            List<SysRole> sysRoles = iSysRoleService.listRolesByUserId(sysUser.getId());

            List<SysPermission> sysPermissionsAll = new ArrayList<>();
            sysRoles.forEach(item -> {
                List<SysPermission> sysRolePermissions = iSysPermissionService.listPermissionByRoleId(item.getId());
                sysPermissionsAll.addAll(sysRolePermissions);
            });

            sysUserDetailsDto.setRoles(sysRoles);
            sysUserDetailsDto.setPermissions(sysPermissionsAll);
        }
        return sysUserDetailsDto;
    }

    @Transactional
    @Override
    public boolean addUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);

        String password = RandomUtil.randomString(8);
        String encode = passwordEncoder.encode(password);
        sendPasswordMail(userDto.getUsername(), password, userDto.getEmail());
        sysUser.setPassword(encode);

        boolean save1 = this.save(sysUser);

        Collection<String> roleIds = userDto.getRoleIds();
        if (roleIds != null) {
            List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userDto.getId());
                userRole.setRoleId(Long.valueOf(roleId));
                return userRole;
            }).collect(Collectors.toList());
            boolean save2 = iSysUserRoleService.saveBatch(sysUserRoles);
        }
        return save1;
    }

    @Transactional
    @Override
    public boolean updateUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        if (!StringUtils.isEmpty(sysUser.getPassword())) {
            //sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        boolean save = this.updateById(sysUser);

        boolean remove = iSysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, sysUser.getId())
        );

        Collection<String> roleIds = userDto.getRoleIds();
        if (roleIds != null) {
            List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userDto.getId());
                userRole.setRoleId(Long.valueOf(roleId));
                return userRole;
            }).collect(Collectors.toList());
            boolean save2 = iSysUserRoleService.saveBatch(sysUserRoles);
        }
        return save;
    }

    @Transactional
    @Override
    public boolean removeUserByIds(Long id) {
        boolean remove = this.removeById(id);

        boolean remove1 = iSysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, id)
        );
        return remove1;
    }

    @Override
    public UserDto findUserRolesInfo(Long id) {
        SysUser sysUser = this.getById(id);
        // 查询用户关联角色
        List<SysUserRole> sysUserRoles = iSysUserRoleService.list(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, sysUser.getId())
        );
        ArrayList<String> roleIds = new ArrayList<>();
        sysUserRoles.forEach(item -> {
            roleIds.add(String.valueOf(item.getRoleId()));
        });
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(sysUser, userDto);
        userDto.setRoleIds(roleIds);
        return userDto;
    }

    private void sendPasswordMail(String userName, String password, String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("fanshuye1304@163.com");//发送者.
        message.setTo(email);//接收者.
        message.setSubject("账号密码");//邮件主题.
        message.setText("用户名：" + userName + "密码：" + password);//邮件内容.
        mailSender.send(message);//发送邮件
        log.info("{}", JSONUtil.toJsonStr(message));
    }

}
