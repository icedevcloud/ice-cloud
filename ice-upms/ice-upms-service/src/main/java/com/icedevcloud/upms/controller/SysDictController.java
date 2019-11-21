package com.icedevcloud.upms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.upms.entity.SysDict;
import com.icedevcloud.upms.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
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

    @ApiOperation(value = "字典查询父级分页接口", notes = "字典查询父级分页接口", produces = "application/json")
    @GetMapping("/page")
    @Override
    public R<IPage<SysDict>> pageList(PageParam pageParam) {
        return iSysDictService.pageList(pageParam, 0l);
    }

    @ApiOperation(value = "字典查询子集分页接口", notes = "字典查询子集分页接口", produces = "application/json")
    @PostMapping("/subPage/{pid}")
    public R<IPage<SysDict>> subPage(@RequestBody PageParam pageParam, @PathVariable(value = "pid") Long pid) {
        return iSysDictService.pageList(pageParam, pid);
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
    @DeleteMapping("{id}")
    public R delDict(@PathVariable Long id) {
        return R.ok(iSysDictService.removeById(id));
    }

}
