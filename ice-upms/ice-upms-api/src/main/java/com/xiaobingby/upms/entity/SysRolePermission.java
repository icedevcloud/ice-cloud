package com.xiaobingby.upms.entity;

import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sys_role_permission 角色 权限关联表
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysRolePermission对象", description="sys_role_permission 角色 权限关联表")
public class SysRolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    private Long permissionId;

}
