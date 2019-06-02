package com.xiaobingby.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.dto.UserDetailsDto;
import com.xiaobingby.upms.dto.UserDto;
import com.xiaobingby.upms.entity.*;
import com.xiaobingby.upms.mapper.SysUserMapper;
import com.xiaobingby.upms.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public UserDetailsDto loadUserByUsername(String username) {
        UserDetailsDto userDetailsDto = null;

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser sysUser = this.getOne(queryWrapper);

        if (sysUser != null) {
            userDetailsDto = new UserDetailsDto();
            BeanUtils.copyProperties(sysUser, userDetailsDto);

            List<SysRole> sysRoles = findUserRole(sysUser.getId());

            List<SysPermission> sysPermissions = findRolePermission(sysRoles);

            userDetailsDto.setRoles(sysRoles);
            userDetailsDto.setPermissions(sysPermissions);
        }
        return userDetailsDto;
    }

    @Transactional
    @Override
    public boolean addUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        boolean save1 = this.save(sysUser);

        Collection<Long> roleIds = userDto.getRoleIds();
        List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userDto.getId());
            userRole.setRoleId(roleId);
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
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        boolean save = this.updateById(sysUser);

        boolean remove = iSysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, sysUser.getId())
        );

        Collection<Long> roleIds = userDto.getRoleIds();
        List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userDto.getId());
            userRole.setRoleId(roleId);
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
        List<SysRole> sysRoles = (List)iSysRoleService.listByIds(roleIds);
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
        QueryWrapper<SysRolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.in("role_id", roleIds);
        List<SysRolePermission> sysRolePermissions = iSysRolePermissionService.list(rolePermissionQueryWrapper);

        ArrayList<Long> permissionIds = new ArrayList<>();
        sysRolePermissions.parallelStream().forEach(item -> {
            permissionIds.add(item.getPermissionId());
        });
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("id", permissionIds);
        permissionQueryWrapper.eq("type", "1");
        List<SysPermission> sysPermissions = iSysPermissionService.list(permissionQueryWrapper);
        return sysPermissions;
    }

}
