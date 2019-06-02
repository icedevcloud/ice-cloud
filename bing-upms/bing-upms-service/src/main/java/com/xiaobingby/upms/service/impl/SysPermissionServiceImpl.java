package com.xiaobingby.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.entity.SysRolePermission;
import com.xiaobingby.upms.mapper.SysPermissionMapper;
import com.xiaobingby.upms.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobingby.upms.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    @Transactional
    @Override
    public boolean delPermission(Long[] ids) {
        boolean remove = this.removeByIds(Arrays.asList(ids));

        boolean remove1 = iSysRolePermissionService.remove(Wrappers.<SysRolePermission>update().lambda()
                .in(SysRolePermission::getPermissionId, Arrays.asList(ids))
        );
        return remove1;
    }

}
