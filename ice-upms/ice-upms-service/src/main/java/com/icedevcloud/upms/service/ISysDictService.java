package com.icedevcloud.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.upms.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * sys_dict 字典表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * pid 字典分页
     *
     * @param pageParam
     * @param pid
     * @return
     */
    R<IPage<SysDict>> pageList(PageParam pageParam, Long pid);

}
