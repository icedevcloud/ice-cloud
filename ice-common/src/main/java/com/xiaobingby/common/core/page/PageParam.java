package com.xiaobingby.common.core.page;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author XiaoBingBy
 * @date 2018-09-05 11:10
 * @since 1.0
 */
@Data
public class PageParam {

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 请求条件
     */
    private ArrayList<QueryParam> queryParams;

}
