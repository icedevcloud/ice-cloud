package com.icedevcloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.common.core.page.QueryParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 顶级 Controller
 *
 * @author XiaoBingBy
 * @date 2018-09-04 15:21
 * @since 1.0
 */
public class BaseController<BaseServiceImpl extends IService<T>, T, PK extends Serializable> {

    private static final String EQ = "eq";

    private static final String LIKE = "like";

    @Autowired(required = false)
    private BaseServiceImpl baseService;

    public R pageList(PageParam pageParam) {
        ArrayList<QueryParam> querys = pageParam.getQueryParams();
        Page<T> page = new Page<T>(pageParam.getCurrent(), pageParam.getPageSize());
        QueryWrapper<T> tQueryWrapper = new QueryWrapper<>();
        if (querys != null) {
            querys.forEach(query -> {
                if (EQ.equals(query.getType()) && StringUtils.isNotEmpty(query.getValue())) {
                    tQueryWrapper.eq(query.getColumn(), query.getValue());
                }
                if (LIKE.equals(query.getType()) && StringUtils.isNotEmpty(query.getValue())) {
                    tQueryWrapper.like(query.getColumn(), query.getValue());
                }
            });
        }
        tQueryWrapper.orderByDesc("create_time");
        IPage<T> retData = baseService.page(page, tQueryWrapper);
        return R.ok(retData);
    }

}
