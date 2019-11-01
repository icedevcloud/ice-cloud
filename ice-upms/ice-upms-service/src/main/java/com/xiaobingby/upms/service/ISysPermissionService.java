package com.xiaobingby.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.vo.AntRolePermissionTreeVo;
import com.xiaobingby.upms.vo.AntRouterTreeVo;
import com.xiaobingby.upms.vo.PermissionTreeVo;

import java.util.ArrayList;
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
     * 嵌套子表格权限管理展
     *
     * @return
     */
    List<PermissionTreeVo> getPermissionTableTree();

    /**
     * 所有权限菜单
     *
     * @return
     */
    ArrayList<PermissionTreeVo> getPermissionMenuTree();

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    boolean delPermission(Long id);

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
