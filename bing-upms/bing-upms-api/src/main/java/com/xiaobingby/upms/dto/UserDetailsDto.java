package com.xiaobingby.upms.dto;

import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.entity.SysRole;
import com.xiaobingby.upms.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author XiaoBingBy
 * @date 2019-01-08 19:48
 * @since 1.0
 */
@Data
public class UserDetailsDto extends SysUser {

    private List<SysRole> roles;

    private List<SysPermission> permissions;

}
