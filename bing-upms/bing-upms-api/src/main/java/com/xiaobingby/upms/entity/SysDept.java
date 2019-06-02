package com.xiaobingby.upms.entity;

import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sys_organization 组织机构表
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysDept对象", description="sys_organization 组织机构表")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    private Long pid;

    @ApiModelProperty(value = "组织机构名称")
    private String name;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;

}
