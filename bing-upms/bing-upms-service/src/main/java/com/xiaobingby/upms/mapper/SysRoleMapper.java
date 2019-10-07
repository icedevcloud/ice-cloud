package com.xiaobingby.upms.mapper;

import com.xiaobingby.upms.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobingby.upms.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sys_role 角色表 Mapper 接口
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 用户ID查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(@Param("userId") Long userId);

    /**
     * 通过角色ID查询所有用户
     *
     * @param roleId
     * @return
     */
    List<SysUser> roleIdByUsers(@Param("roleId") Long roleId);

}
