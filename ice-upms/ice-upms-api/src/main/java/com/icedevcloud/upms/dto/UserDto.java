package com.icedevcloud.upms.dto;

import com.icedevcloud.upms.entity.SysUser;
import lombok.Data;

import java.util.Collection;

/**
 * 增加用户DTO
 *
 * @author XiaoBingBy
 * @date 2019-01-09 17:09
 * @since 1.0
 */
@Data
public class UserDto extends SysUser {

//    private Collection<Role> roles;

    private Collection<String> roleIds;

}
