package com.xiaobingby.upms.controller;


import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.core.page.PageParams;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.service.ISysPermissionService;
import com.xiaobingby.upms.vo.MenuTreeVo;
import com.xiaobingby.upms.vo.PermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * sys_permission 权限表 前端控制器
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Api(value = "权限管理接口类", tags = "权限管理", description = "权限管理")
@RestController
@RequestMapping("/permission")
public class SysPermissionController extends BaseController<ISysPermissionService, SysPermission, Long> {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "权限树展示接口", notes = "权限树展示接口", produces = "application/json")
    @GetMapping("/getPermissionTree")
    public R<List<PermissionTreeVo>> getPermissionTree() {
        List<PermissionTreeVo> permissionTree = iSysPermissionService.getPermissionTree();
        return new R<List<PermissionTreeVo>>().ok(permissionTree);
    }

    @ApiOperation(value = "添加权限接口", notes = "添加权限接口", produces = "application/json")
    @PostMapping
    public R<Boolean> addPermission(@RequestBody SysPermission sysPermission) {
        return R.ok(iSysPermissionService.save(sysPermission));
    }

    @ApiOperation(value = "修改权限接口", notes = "修改权限接口", produces = "application/json")
    @PutMapping
    public R<Boolean> updatePermission(@RequestBody SysPermission sysPermission) {
        return R.ok(iSysPermissionService.updateById(sysPermission));
    }

    @ApiOperation(value = "删除权限接口", notes = "删除权限接口", produces = "application/json")
    @DeleteMapping("{ids}")
    public R<Boolean> delPermission(@PathVariable Long[] ids) {
        return R.ok(iSysPermissionService.delPermission(ids));
    }

    @ApiOperation(value = "前端路由接口", notes = "前端路由接口", produces = "application/json")
    @GetMapping("getRouter")
    public R<List<MenuTreeVo>> getRouter() {
        List<MenuTreeVo> userMenuTree = iSysPermissionService.getUserMenuTree();
        return R.ok(userMenuTree);
    }

}
