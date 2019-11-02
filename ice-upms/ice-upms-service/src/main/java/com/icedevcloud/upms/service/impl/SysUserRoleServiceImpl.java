package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icedevcloud.upms.entity.SysUserRole;
import com.icedevcloud.upms.mapper.SysUserRoleMapper;
import com.icedevcloud.upms.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sys_user_role 用户 角色关联表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public Boolean removeUserRoleByUserId(Long userId) {
        return this.remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userId));
    }

    @Override
    public Boolean removeUserRoleByRoleId(Long roleId) {
        return this.remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getRoleId, roleId));
    }

}
