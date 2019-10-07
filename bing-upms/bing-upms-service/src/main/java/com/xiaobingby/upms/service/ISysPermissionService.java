package com.xiaobingby.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.vo.*;

import java.util.List;

/**
 * <p>
 * sys_permission 权限表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 嵌套子表格权限管理展接口
     *
     * @return
     */
    List<PermissionTableTreeVo> getPermissionTableTree();

    /**
     * 删除权限
     *
     * @param ids
     * @return
     */
    boolean delPermission(Long[] ids);

    /**
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    AntRolePermissionTreeVo getRolePermissionTree(Long roleId);

    /**
     * 角色ID查询权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> listPermissionByRoleId(Long roleId);

    /**
     * Ant 前端路由接口
     *
     * @return
     */
    List<AntRouterTreeVo> getRouterByUser(Long userId);

}
