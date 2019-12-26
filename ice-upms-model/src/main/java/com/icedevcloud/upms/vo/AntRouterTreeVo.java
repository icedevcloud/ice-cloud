package com.icedevcloud.upms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 前端树形结构
 *
 * @author XiaoBingBy
 * @date 2018-12-04 14:46
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class AntRouterTreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String name;

    private String key;

    private String path;

    private String component;

    private String redirect;

    private String icon;

    private Boolean hidden;

    private Boolean cache;

    private List<AntRouterTreeVo> children;

}
