package com.xiaobingby.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.entity.SysRole;
import com.xiaobingby.upms.entity.SysRolePermission;
import com.xiaobingby.upms.entity.SysUser;
import com.xiaobingby.upms.entity.SysUserRole;
import com.xiaobingby.upms.mapper.SysRoleMapper;
import com.xiaobingby.upms.service.ISysRolePermissionService;
import com.xiaobingby.upms.service.ISysRoleService;
import com.xiaobingby.upms.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Transactional
    @Override
    public boolean delRole(Long[] ids) {
        boolean remove = this.removeByIds(Arrays.asList(ids));

        boolean remove1 = iSysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .in(SysUserRole::getRoleId, Arrays.asList(ids))
        );

        boolean remove2 = iSysRolePermissionService.remove(Wrappers.<SysRolePermission>update().lambda()
                .in(SysRolePermission::getRoleId, Arrays.asList(ids))
        );
        return remove2;
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
