package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icedevcloud.upms.dto.RolePermissionDto;
import com.icedevcloud.upms.entity.SysRolePermission;
import com.icedevcloud.upms.mapper.SysRolePermissionMapper;
import com.icedevcloud.upms.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * <p>
 * sys_role_permission 角色 权限关联表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRolePermissionByRoleId(RolePermissionDto rolePermissionDto) {
        this.remove(Wrappers.<SysRolePermission>lambdaUpdate()
                .eq(SysRolePermission::getRoleId, rolePermissionDto.getRoleId())
        );
        // 构造插入数据
        ArrayList<SysRolePermission> rolePermissions = new ArrayList<>();
        for (Long permId : rolePermissionDto.getPermIds()) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(rolePermissionDto.getRoleId());
            rolePermission.setPermissionId(permId);
            rolePermissions.add(rolePermission);
        }
        if (rolePermissions.size() >= 1) {
            this.saveBatch(rolePermissions);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeRolePermissionByPermissionId(Long permissionId) {
        return this.remove(Wrappers.<SysRolePermission>lambdaUpdate().eq(SysRolePermission::getPermissionId, permissionId));
    }

    @Override
    public Boolean removeRolePermissionByRoleId(Long roleId) {
        return this.remove(Wrappers.<SysRolePermission>update().lambda().eq(SysRolePermission::getRoleId, roleId));
    }
}
