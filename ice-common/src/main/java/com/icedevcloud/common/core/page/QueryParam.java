package com.icedevcloud.common.core.page;

import lombok.Data;

/**
 * 请求查询参数
 * @author XiaoBingBy
 * @date 2018-09-05 10:58
 * @since 1.0
 */
@Data
public class QueryParam<T> {

    /**
     * 字段名称
     */
    private String column;

    /**
     * 查询条件
     */
    private String type;

    /**
     * 匹配值
     */
    private String value;

}
