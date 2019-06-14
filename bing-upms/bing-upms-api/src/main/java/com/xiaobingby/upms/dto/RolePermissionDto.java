package com.xiaobingby.upms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分配角色权限Dto
 * @author XiaoBingBy
 * @date 2018-12-11 11:12
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class RolePermissionDto {

    private Long roleId;

    private Long[] permIds;

}
