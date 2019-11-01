package com.xiaobingby.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobingby.upms.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sys_permission 权限表 Mapper 接口
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> findUserMenus(Long id);

    /**
     * 角色ID查询权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> listPermissionByRoleId(@Param("roleId") Long roleId);

}
