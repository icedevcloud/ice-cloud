package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icedevcloud.upms.entity.SysRole;
import com.icedevcloud.upms.entity.SysUser;
import com.icedevcloud.upms.mapper.SysRoleMapper;
import com.icedevcloud.upms.service.ISysRolePermissionService;
import com.icedevcloud.upms.service.ISysRoleService;
import com.icedevcloud.upms.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sys_role 角色表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delRole(Long id) {
        this.removeById(id);
        iSysUserRoleService.removeUserRoleByRoleId(id);
        iSysRolePermissionService.removeRolePermissionByRoleId(id);
        return Boolean.TRUE;
    }

    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    public List<SysUser> roleIdByUsers(Long roleId) {
        List<SysUser> sysUsers = baseMapper.roleIdByUsers(roleId);
        sysUsers.forEach(item -> item.setPassword(""));
        return baseMapper.roleIdByUsers(roleId);
    }

}
