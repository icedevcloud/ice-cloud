package com.xiaobingby.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import com.xiaobingby.upms.dto.UserDto;
import com.xiaobingby.upms.entity.SysUser;

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
    boolean addUser(UserDto userDto);

    /**
     * 更新用户
     *
     * @param userDto
     * @return
     */
    boolean updateUser(UserDto userDto);

    /**
     * 用户Id 删除用户
     *
     * @param id
     * @return
     */
    boolean removeUserById(Long id);

    /**
     * 用户ID 查询用户
     *
     * @param id
     * @return
     */
    UserDto findUserRolesInfo(Long id);

}
