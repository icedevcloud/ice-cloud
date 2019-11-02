package com.icedevcloud.common.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO 修改常量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO 修改常量
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO 修改常量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // TODO 修改常量
    private LocalDateTime updateTime;

}
