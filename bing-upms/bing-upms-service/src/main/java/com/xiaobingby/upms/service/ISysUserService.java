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

    SysUserDetailsDto loadUserByUsername(String username);

    boolean addUser(UserDto userDto);

    boolean updateUser(UserDto userDto);

    boolean removeUserByIds(Long id);

    UserDto findUserRolesInfo(Long id);

}
