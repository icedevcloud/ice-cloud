package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.common.core.page.QueryParam;
import com.icedevcloud.upms.entity.SysDict;
import com.icedevcloud.upms.mapper.SysDictMapper;
import com.icedevcloud.upms.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public R<IPage<SysDict>> pageList(PageParam pageParam, Long pid) {
        ArrayList<QueryParam> querys = pageParam.getQueryParams();
        Page<SysDict> page = new Page<SysDict>(pageParam.getCurrent(), pageParam.getPageSize());
        QueryWrapper<SysDict> tQueryWrapper = new QueryWrapper<>();
        if (querys != null) {
            querys.forEach(query -> {
                if ("eq".equals(query.getType()) && StringUtils.isNotEmpty(query.getValue())) {
                    tQueryWrapper.eq(query.getColumn(), query.getValue());
                }
                if ("like".equals(query.getType()) && StringUtils.isNotEmpty(query.getValue())) {
                    tQueryWrapper.like(query.getColumn(), query.getValue());
                }
            });
        }
        tQueryWrapper.eq("pid", pid);
        tQueryWrapper.orderByDesc("create_time");
        IPage<SysDict> retData = this.page(page, tQueryWrapper);
        return R.ok(retData);
    }

}
