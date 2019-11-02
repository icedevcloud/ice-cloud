package com.icedevcloud.upms.controller;


import com.icedevcloud.common.core.api.R;
import com.icedevcloud.upms.entity.SysDept;
import com.icedevcloud.upms.vo.DeptTreeVo;
import com.icedevcloud.upms.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_organization 组织机构表 前端控制器
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Api(value = "部门管理接口类", tags = "部门管理", description = "部门管理")
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "部门Tree接口", notes = "部门Tree接口", produces = "application/json")
    @GetMapping("/getDeptTree")
    public R<List<DeptTreeVo>> getDeptTree() {
        return R.ok(iSysDeptService.getDeptTree());
    }

    @ApiOperation(value = "添加部门接口", notes = "添加部门接口", produces = "application/json")
    @PostMapping
    public R<Boolean> addDept(@RequestBody SysDept sysDept) {
        return R.ok(iSysDeptService.addDept(sysDept));
    }

    @ApiOperation(value = "修改部门接口", notes = "修改部门接口", produces = "application/json")
    @PutMapping
    public R<Boolean> updateDept(@RequestBody SysDept sysDept) {
        return R.ok(iSysDeptService.updateDept(sysDept));
    }

    @ApiOperation(value = "删除部门接口", notes = "删除部门接口", produces = "application/json")
    @DeleteMapping("{id}")
    public R<Boolean> delDept(@PathVariable Long id) {
        return R.ok(iSysDeptService.removeDeptById(id));
    }

}
