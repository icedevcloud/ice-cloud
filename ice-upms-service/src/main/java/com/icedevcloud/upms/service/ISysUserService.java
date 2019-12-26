package com.icedevcloud.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icedevcloud.upms.dto.SysUserDetailsDto;
import com.icedevcloud.upms.dto.UserDto;
import com.icedevcloud.upms.entity.SysUser;

/**
 * <p>
 * sys_user 用户表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户名 查询用户信息接口
     *
     * @param username
     * @return
     */
    SysUserDetailsDto loadUserByUsername(String username);

    /**
     * 添加用户
     *
     * @param userDto
     * @return
     */
    Boolean addUser(UserDto userDto);

    /**
     * 更新用户角色关联信息
     *
     * @param userDto
     * @return
     */
    Boolean updateUserAndUserRole(UserDto userDto);

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    Boolean updateUse(SysUser sysUser);

    /**
     * 用户Id 删除用户
     *
     * @param id
     * @return
     */
    Boolean removeUserById(Long id);

    /**
     * 用户ID 查询用户
     *
     * @param id
     * @return
     */
    UserDto findUserRolesInfo(Long id);

}
