package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sys_user_role 用户 角色关联表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 用户Id 删除用户角色关系
     *
     * @param userId
     * @return
     */
    Boolean removeUserRoleByUserId(Long userId);

    /**
     * 角色Id 删除角色用户关系
     *
     * @param roleId
     * @return
     */
    Boolean removeUserRoleByRoleId(Long roleId);

}
