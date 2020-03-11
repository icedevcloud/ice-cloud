package com.icedevcloud.upms.service;

import com.icedevcloud.upms.dto.RolePermissionDto;
import com.icedevcloud.upms.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sys_role_permission 角色 权限关联表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 角色ID 更新角色权限
     *
     * @param rolePermissionDto
     * @return
     */
    boolean updateRolePermissionByRoleId(RolePermissionDto rolePermissionDto);

    /**
     * 权限Id 删除角色权限关系
     *
     * @param permissionId
     * @return
     */
    Boolean removeRolePermissionByPermissionId(Long permissionId);

    /**
     * 角色Id 删除角色权限关系
     *
     * @param roleId
     * @return
     */
    Boolean removeRolePermissionByRoleId(Long roleId);

}
