package com.xiaobingby.upms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单扩展参数
 * @author XiaoBingBy
 * @date 2018-12-04 14:46
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MenuMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否隐藏面包条
     */
    private boolean hideInBread;

    /**
     * 是否隐藏菜单
     */
    private boolean hideInMenu;

    /**
     * 是否路由缓存
     */
    private boolean notCache;

}
