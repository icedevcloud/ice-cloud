package com.xiaobingby.flow.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.xiaobingby.common.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiex
 * @since 2019-10-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysWorkFlow对象", description="")
public class SysWorkFlow {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "流程名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @ApiModelProperty(value = "步骤   这里面应该是存一个list dto的json字符串")
    private String step;

    @ApiModelProperty(value = "1 按照角色；2  按照用户；")
    private String type;


}
