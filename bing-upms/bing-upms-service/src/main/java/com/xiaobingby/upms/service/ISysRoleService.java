package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

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

    boolean delRole(Long[] ids);

    /**
     * 用户ID查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(Long userId);

}
