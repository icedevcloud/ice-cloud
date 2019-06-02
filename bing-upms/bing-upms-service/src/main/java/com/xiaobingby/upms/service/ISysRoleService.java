package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
