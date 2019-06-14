package com.xiaobingby.upms.service;

import com.xiaobingby.upms.dto.RolePermissionDto;
import com.xiaobingby.upms.entity.SysRolePermission;
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

    boolean updateRolePermission(RolePermissionDto rolePermissionDto);

}
