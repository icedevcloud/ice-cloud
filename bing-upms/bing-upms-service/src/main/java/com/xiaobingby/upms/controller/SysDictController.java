package com.xiaobingby.upms.controller;


import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.core.page.PageParams;
import com.xiaobingby.upms.entity.SysDict;
import com.xiaobingby.upms.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Api(value = "字典管理接口类", tags = "字典管理", description = "字典管理")
@RestController
@RequestMapping("/dict")
public class SysDictController extends BaseController<ISysDictService, SysDict, Long> {

    @Autowired
    private ISysDictService iSysDictService;

    @ApiOperation(value = "字典查询分页接口", notes = "字典查询分页接口", produces = "application/json")
    @PostMapping("/page")
    @Override
    public com.xiaobingby.common.core.api.R pageList(@RequestBody PageParams pageParams) {
        return super.pageList(pageParams);
    }

    @ApiOperation(value = "添加字典接口", notes = "添加字典接口", produces = "application/json")
    @PostMapping
    public R addDict(@RequestBody SysDict sysDict) {
        return R.ok(iSysDictService.save(sysDict));
    }

    @ApiOperation(value = "修改字典接口", notes = "修改字典接口", produces = "application/json")
    @PutMapping
    public R updateDict(@RequestBody SysDict sysDict) {
        return R.ok(iSysDictService.updateById(sysDict));
    }

    @ApiOperation(value = "删除字典接口", notes = "删除字典接口", produces = "application/json")
    @DeleteMapping("{ids}")
    public R delDict(@PathVariable Long[] ids) {
        return R.ok(iSysDictService.removeByIds(Arrays.asList(ids)));
    }

}
