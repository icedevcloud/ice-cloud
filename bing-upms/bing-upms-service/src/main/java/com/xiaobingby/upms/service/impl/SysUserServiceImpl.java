package com.xiaobingby.upms.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import com.xiaobingby.upms.dto.UserDto;
import com.xiaobingby.upms.entity.*;
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

            List<SysRole> sysRoles = findUserRole(sysUser.getId());

            List<SysPermission> sysPermissions = findRolePermission(sysRoles);

            sysUserDetailsDto.setRoles(sysRoles);
            sysUserDetailsDto.setPermissions(sysPermissions);
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
        List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userDto.getId());
            userRole.setRoleId(Long.valueOf(roleId));
            return userRole;
        }).collect(Collectors.toList());
        boolean save2 = iSysUserRoleService.saveBatch(sysUserRoles);
        return save2;
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
        List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userDto.getId());
            userRole.setRoleId(Long.valueOf(roleId));
            return userRole;
        }).collect(Collectors.toList());
        boolean save2 = iSysUserRoleService.saveBatch(sysUserRoles);
        return save2;
    }

    @Transactional
    @Override
    public boolean removeUserByIds(Long[] ids) {
        boolean remove = this.removeByIds(Arrays.asList(ids));

        boolean remove1 = iSysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .in(SysUserRole::getUserId, Arrays.asList(ids))
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

    /**
     * 查询用户角色
     *
     * @param id
     * @return
     */
    private List<SysRole> findUserRole(Long id) {
        QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", id);
        List<SysUserRole> userRoles = iSysUserRoleService.list(userRoleQueryWrapper);

        ArrayList<Long> roleIds = new ArrayList<>();
        userRoles.parallelStream().forEach(item -> {
            roleIds.add(item.getRoleId());
        });
        List<SysRole> sysRoles = (List) iSysRoleService.listByIds(roleIds);
        return sysRoles;
    }

    /**
     * 查询角色权限
     *
     * @param roles
     * @return
     */
    private List<SysPermission> findRolePermission(List<SysRole> roles) {
        List<Long> roleIds = new ArrayList<>();
        roles.parallelStream().forEach(item -> {
            roleIds.add(item.getId());
        });
        List<SysRolePermission> sysRolePermissions = iSysRolePermissionService.list(Wrappers.<SysRolePermission>lambdaQuery()
                .in(SysRolePermission::getRoleId, roleIds)
        );

        ArrayList<Long> permissionIds = new ArrayList<>();
        sysRolePermissions.parallelStream().forEach(item -> {
            permissionIds.add(item.getPermissionId());
        });
        List<SysPermission> sysPermissions = iSysPermissionService.list(Wrappers.<SysPermission>lambdaQuery()
                .in(SysPermission::getId, permissionIds)
                .eq(SysPermission::getType, 2)
        );
        return sysPermissions;
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
