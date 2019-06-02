package com.xiaobingby.upms.entity;

import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sys_role 角色表
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysRole对象", description="sys_role 角色表")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "描述")
    private String description;

}
