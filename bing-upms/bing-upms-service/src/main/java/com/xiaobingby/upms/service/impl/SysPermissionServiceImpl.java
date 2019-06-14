package com.xiaobingby.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.entity.SysRolePermission;
import com.xiaobingby.upms.mapper.SysPermissionMapper;
import com.xiaobingby.upms.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.service.ISysRolePermissionService;
import com.xiaobingby.upms.vo.PermissionTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * sys_permission 权限表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;

    /**
     * 构造后台菜单Tree
     *
     * @return
     */
    public List<PermissionTreeVo> getPermissionTree() {
        ArrayList<PermissionTreeVo> permissionTreeVos = new ArrayList<>();
        List<SysPermission> permissions = this.list(Wrappers.<SysPermission>lambdaQuery()
                .orderByAsc(SysPermission::getSort)
        );
        for (SysPermission permission : permissions) {
            if (permission.getPid() == 0) {
                PermissionTreeVo permissionTreeVo = new PermissionTreeVo();
                BeanUtils.copyProperties(permission, permissionTreeVo);
                genPermissionTreeVo(permissionTreeVo, permissions);
                permissionTreeVos.add(permissionTreeVo);
            }
        }
        return permissionTreeVos;
    }

    /**
     * 递归遍历节点
     *
     * @param permissionTreeVo
     * @param permissions
     */
    private void genPermissionTreeVo(PermissionTreeVo permissionTreeVo, List<SysPermission> permissions) {
        ArrayList<PermissionTreeVo> permissionTreeVos = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permissionTreeVo.getId().longValue() == permission.getPid().longValue()) {
                PermissionTreeVo temp = new PermissionTreeVo();
                BeanUtils.copyProperties(permission, temp);
                genPermissionTreeVo(temp, permissions);
                permissionTreeVos.add(temp);
            }
        }
        permissionTreeVo.setChildren(permissionTreeVos);
    }

    @Transactional
    @Override
    public boolean delPermission(Long[] ids) {
        boolean remove = this.removeByIds(Arrays.asList(ids));

        boolean remove1 = iSysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaUpdate()
                .in(SysRolePermission::getPermissionId, Arrays.asList(ids))
        );
        return remove1;
    }

    @Override
    public List<PermissionTreeVo> getRolePermissionTree(Long roleId) {
        // 查询角色拥有权限ID
        List<SysRolePermission> sysRolePermissions = iSysRolePermissionService.list(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, roleId)
        );
        List<PermissionTreeVo> permissionTree = this.getPermissionTree();
        sysRolePermissions.forEach(rolePermission -> {
            modifyPermissionTreeVo(rolePermission, permissionTree);
        });
        modifyPermissionTreeVo(permissionTree);
        return permissionTree;
    }

    /**
     * 修改是否选中节点属性
     *
     * @param sysRolePermission
     * @param permissionTree
     */
    private void modifyPermissionTreeVo(SysRolePermission sysRolePermission, List<PermissionTreeVo> permissionTree) {
        permissionTree.forEach(item -> {
            if (sysRolePermission.getPermissionId() == item.getId()) {
                item.setChecked(true);
                return;
            }
            if (item.getChildren() != null) {
                this.modifyPermissionTreeVo(sysRolePermission, item.getChildren());
            }
        });
    }

    /**
     * 修改所有节点数据
     *
     * @param permissionTree
     */
    private void modifyPermissionTreeVo(List<PermissionTreeVo> permissionTree) {
        permissionTree.forEach(item -> {
            item.setExpand(true);
            if (item.getChildren() != null) {
                this.modifyPermissionTreeVo(item.getChildren());
            }
        });
    }

}
