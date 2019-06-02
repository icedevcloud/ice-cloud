package com.xiaobingby.upms.entity;

import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sys_user 用户表
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="sys_user 用户表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "手机号是否验证 0、否 1、是")
    private Integer phoneVerified;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "邮箱是否验证 0、否 1、是")
    private Integer emailVerified;

}
