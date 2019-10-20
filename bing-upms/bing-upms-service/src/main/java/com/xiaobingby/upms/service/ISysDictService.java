package com.xiaobingby.upms.service;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.core.page.PageParam;
import com.xiaobingby.upms.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysDictService extends IService<SysDict> {

    R pageList(PageParam pageParam, Long pid);

}
