package com.icedevcloud.upms.dto;

import com.icedevcloud.upms.entity.SysPermission;
import com.icedevcloud.upms.entity.SysRole;
import com.icedevcloud.upms.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @author XiaoBingBy
 * @date 2019-01-08 19:48
 * @since 1.0
 */
@Data
public class SysUserDetailsDto extends SysUser {

    private List<SysRole> roles;

    private List<SysPermission> permissions;

}
