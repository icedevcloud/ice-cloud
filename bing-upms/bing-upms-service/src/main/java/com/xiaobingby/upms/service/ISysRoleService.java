package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.entity.SysUser;

import java.util.List;

/**
 * <p>
 * sys_role 角色表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean delRole(Long id);

    /**
     * 用户ID查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Long userId);

    /**
     * 通过角色ID查询所有用户
     *
     * @param roleId
     * @return
     */
    List<SysUser> roleIdByUsers(Long roleId);

}
