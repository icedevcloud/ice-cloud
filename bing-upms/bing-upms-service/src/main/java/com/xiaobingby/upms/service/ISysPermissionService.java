package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sys_permission 权限表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysPermissionService extends IService<SysPermission> {

    boolean delPermission(Long[] ids);

}
