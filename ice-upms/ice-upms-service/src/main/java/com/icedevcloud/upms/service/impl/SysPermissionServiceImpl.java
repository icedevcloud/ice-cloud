package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icedevcloud.common.core.api.Assert;
import com.icedevcloud.upms.entity.SysPermission;
import com.icedevcloud.upms.entity.SysRolePermission;
import com.icedevcloud.upms.mapper.SysPermissionMapper;
import com.icedevcloud.upms.service.ISysPermissionService;
import com.icedevcloud.upms.service.ISysRolePermissionService;
import com.icedevcloud.upms.vo.AntRolePermissionTreeVo;
import com.icedevcloud.upms.vo.AntRouterTreeVo;
import com.icedevcloud.upms.vo.PermissionTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        genPermissionTreeVo(permissionTreeVos, permissions);
        return permissionTreeVos;
    }

    /**
     * 构建权限子节点
     *
     * @param permissionTreeVos
     * @param permissions
     */
    private void genPermissionTreeVo(ArrayList<PermissionTreeVo> permissionTreeVos, List<SysPermission> permissions) {
        for (SysPermission permission : permissions) {
            if (permission.getPid() == 0) {
                PermissionTreeVo permissionTreeVo = new PermissionTreeVo();
                BeanUtils.copyProperties(permission, permissionTreeVo);
                permissionTreeVo.setKey(String.valueOf(permission.getId()));
                genSubPermissionTreeVo(permissionTreeVo, permissions);
                permissionTreeVos.add(permissionTreeVo);
            }
        }
    }

    /**
     * 构建权限子节点 递归遍历节点
     *
     * @param permissionTreeVo
     * @param permissions
     */
    private void genSubPermissionTreeVo(PermissionTreeVo permissionTreeVo, List<SysPermission> permissions) {
        ArrayList<PermissionTreeVo> permissionTreeVos = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permissionTreeVo.getId().longValue() == permission.getPid().longValue()) {
                PermissionTreeVo temp = new PermissionTreeVo();
                BeanUtils.copyProperties(permission, temp);
                temp.setKey(String.valueOf(permission.getId()));
                genSubPermissionTreeVo(temp, permissions);
                permissionTreeVos.add(temp);
            }
        }
        permissionTreeVo.setChildren(permissionTreeVos.size() >= 1 ? permissionTreeVos : null);
    }

    @Override
    public List<PermissionTreeVo> getPermissionTableTree() {
        List<PermissionTreeVo> permissionTree = this.getPermissionTree();
        return permissionTree;
    }

    @Override
    public ArrayList<PermissionTreeVo> getPermissionMenuTree() {
        ArrayList<PermissionTreeVo> permissionTreeVos = new ArrayList<>();
        List<SysPermission> permissions = this.list(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getType, 0)
                .orderByAsc(SysPermission::getSort)
        );
        genPermissionTreeVo(permissionTreeVos, permissions);
        return permissionTreeVos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delPermission(Long id) {
        int count = this.count(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getPid, id));
        Assert.fail(count >= 1, "存在子节点,不允许删除!");

        this.removeById(id);
        iSysRolePermissionService.removeRolePermissionByPermissionId(id);
        return Boolean.TRUE;
    }

    @Override
    public AntRolePermissionTreeVo getRolePermissionTree(Long roleId) {
        // 查询角色拥有权限ID
        List<SysRolePermission> sysRolePermissions = iSysRolePermissionService.list(Wrappers.<SysRolePermission>lambdaQuery()
                .eq(SysRolePermission::getRoleId, roleId)
        );

        List<PermissionTreeVo> permissionTree = this.getPermissionTree();
        permissionTree.forEach(item -> {
            item.setKey(String.valueOf(item.getId()));
        });

        ArrayList<String> ids = new ArrayList<>();
        sysRolePermissions.forEach(item -> {
            ids.add(String.valueOf(item.getPermissionId()));
        });
        modifyPermissionTreeVo(permissionTree);

        AntRolePermissionTreeVo antRolePermissionTreeVo = new AntRolePermissionTreeVo();
        antRolePermissionTreeVo.setIds(ids);
        antRolePermissionTreeVo.setRecord(permissionTree);
        return antRolePermissionTreeVo;
    }

    /**
     * 递归获取生成 ids 数组
     *
     * @param permissionTree
     * @param ids
     */
    private void genAllPermissionTreeIds(List<PermissionTreeVo> permissionTree, ArrayList<String> ids) {
        if (ids == null) {
            return;
        }
        for (PermissionTreeVo permissionTreeVo : permissionTree) {
            Long id = permissionTreeVo.getId();
            ids.add(String.valueOf(id));
            if (permissionTreeVo.getChildren() != null) {
                genAllPermissionTreeIds(permissionTreeVo.getChildren(), ids);
            }
        }
    }

    @Override
    public List<SysPermission> listPermissionByRoleId(Long roleId) {
        return baseMapper.listPermissionByRoleId(roleId);
    }

    @Override
    public List<AntRouterTreeVo> getRouterByUser(Long userId) {
        List<SysPermission> permissions = baseMapper.findUserMenus(userId);
        ArrayList<AntRouterTreeVo> antRouterTreeVos = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (permission.getPid() == 0) {
                AntRouterTreeVo antRouterTreeVo = new AntRouterTreeVo();
                BeanUtils.copyProperties(permission, antRouterTreeVo);
                antRouterTreeVo.setKey(permission.getName());
                genAntRouterTreeVo(antRouterTreeVo, permissions);
                antRouterTreeVos.add(antRouterTreeVo);
            }
        }
        return antRouterTreeVos;
    }

    /**
     * 递归构建Ant菜单
     *
     * @param antRouterTreeVo
     * @param permissions
     */
    private void genAntRouterTreeVo(AntRouterTreeVo antRouterTreeVo, List<SysPermission> permissions) {
        ArrayList<AntRouterTreeVo> antRouterTreeVos = new ArrayList<>();
        for (SysPermission permission : permissions) {
            if (antRouterTreeVo.getId().longValue() == permission.getPid().longValue()) {
                AntRouterTreeVo temp = new AntRouterTreeVo();
                BeanUtils.copyProperties(permission, temp);
                temp.setKey(permission.getName());
                genAntRouterTreeVo(temp, permissions);
                antRouterTreeVos.add(temp);
            }
        }
        antRouterTreeVo.setChildren(antRouterTreeVos.size() >= 1 ? antRouterTreeVos : null);
    }

    /**
     * 修改是否选中节点属性
     *
     * @param sysRolePermission
     * @param permissionTree
     */
    private void modifyPermissionTreeVo(SysRolePermission sysRolePermission, List<PermissionTreeVo> permissionTree) {
        permissionTree.forEach(item -> {
            item.setKey(String.valueOf(item.getId()));
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
            item.setKey(String.valueOf(item.getId()));
            if (item.getChildren() != null) {
                this.modifyPermissionTreeVo(item.getChildren());
            }
        });
    }

}
