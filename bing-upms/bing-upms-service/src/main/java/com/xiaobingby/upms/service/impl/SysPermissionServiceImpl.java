package com.xiaobingby.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.common.security.dto.UserDetailsDto;
import com.xiaobingby.common.security.util.SecurityUtils;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.entity.SysRolePermission;
import com.xiaobingby.upms.mapper.SysPermissionMapper;
import com.xiaobingby.upms.service.ISysPermissionService;
import com.xiaobingby.upms.service.ISysRolePermissionService;
import com.xiaobingby.upms.vo.MenuMeta;
import com.xiaobingby.upms.vo.MenuTreeVo;
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

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

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

    @Override
    public List<MenuTreeVo> getUserMenuTree() {
        UserDetailsDto userDetails = SecurityUtils.getUserDetails();
        List<SysPermission> userMenus = sysPermissionMapper.findUserMenus(userDetails.getId());
        ArrayList<MenuTreeVo> menuTreeVos = new ArrayList<>();
        // 构造一级菜单
        userMenus.forEach(item -> {
            if (item.getPid() == 0) {
                GenOneMenuTreeVo(menuTreeVos, item);
            }
        });

        // 构造二级菜单
        menuTreeVos.forEach(i -> {
            ArrayList<MenuTreeVo> subMenuTreeVos = new ArrayList<>();
            userMenus.forEach(j -> {
                if (i.getId() == j.getPid()) {
                    GenOneMenuTreeVo(subMenuTreeVos, j);
                }
            });
            i.setChildren(subMenuTreeVos);
        });
        return menuTreeVos;
    }

    @Override
    public List<SysPermission> listPermissionByRoleId(Long roleId) {
        return baseMapper.listPermissionByRoleId(roleId);
    }

    /**
     * 修改是否选中节点属性
     *
     * @param sysRolePermission
     * @param permissionTree
     */
    private void modifyPermissionTreeVo(SysRolePermission sysRolePermission, List<PermissionTreeVo> permissionTree) {
        permissionTree.forEach(item -> {
            if (sysRolePermission.getPermissionId().longValue() == item.getId().longValue()) {
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

    /**
     * Permission To ArrayList<MenuTreeVo> meGenOneMenuTreeVonuTreeVos
     *
     * @param menuTreeVos
     * @param item
     */
    private void GenOneMenuTreeVo(ArrayList<MenuTreeVo> menuTreeVos, SysPermission item) {
        MenuTreeVo menuTreeVo = new MenuTreeVo();
        menuTreeVo.setId(item.getId());
        menuTreeVo.setPath(item.getPath());
        menuTreeVo.setName(item.getName());
        menuTreeVo.setComponent(item.getComponent());
        MenuMeta menuMeta = new MenuMeta();
        menuMeta.setTitle(item.getTitle());
        menuMeta.setIcon(item.getIcon());
        menuMeta.setHideInBread(item.getHideInBread());
        menuMeta.setHideInMenu(item.getHideInMenu());
        menuMeta.setNotCache(item.getNotCache());
        menuTreeVo.setMeta(menuMeta);
        menuTreeVos.add(menuTreeVo);
    }

}
