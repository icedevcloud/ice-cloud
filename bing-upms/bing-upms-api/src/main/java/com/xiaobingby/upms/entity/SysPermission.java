package com.xiaobingby.upms.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sys_permission 权限表
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysPermission对象", description="sys_permission 权限表")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    @ApiModelProperty(value = "权限名称")
    private String title;

    @ApiModelProperty(value = "路由名称")
    private String name;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "组件名称")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "网页链接")
    private String url;

    @ApiModelProperty(value = "权限编码")
    private String permCode;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "排序号")
    private BigDecimal sort;

    @ApiModelProperty(value = "类型 0.菜单 1 按钮")
    private Integer type;

    @ApiModelProperty(value = "设为true后在左侧菜单不会显示该页面选项")
    private Boolean hideInMenu;

    @ApiModelProperty(value = "设为true后在顶部显示面包屑")
    private Boolean hideInBread;

    @ApiModelProperty(value = "设为true后页面不会缓存")
    private Boolean notCache;

    @ApiModelProperty(value = "描述")
    private String description;

}
