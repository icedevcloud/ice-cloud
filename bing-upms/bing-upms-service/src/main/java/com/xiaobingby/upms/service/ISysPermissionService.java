package com.xiaobingby.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.vo.MenuTreeVo;
import com.xiaobingby.upms.vo.PermissionTreeVo;

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

    List<PermissionTreeVo> getPermissionTree();

    boolean delPermission(Long[] ids);

    List<PermissionTreeVo> getRolePermissionTree(Long roleId);

    List<MenuTreeVo> getUserMenuTree();

    /**
     * 角色ID查询权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> listPermissionByRoleId(Long roleId);

}
