package com.icedevcloud.upms.controller;


import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.security.dto.UserDetailsDto;
import com.icedevcloud.common.security.util.SecurityUtils;
import com.icedevcloud.upms.entity.SysPermission;
import com.icedevcloud.upms.vo.AntRouterTreeVo;
import com.icedevcloud.upms.vo.PermissionTreeVo;
import com.icedevcloud.upms.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ApiOperation(value = "嵌套子表格权限管理展接口", notes = "权限树展示接口", produces = "application/json")
    @GetMapping("/getPermissionTableTree")
    public R<List<PermissionTreeVo>> getPermissionTableTree() {
        List<PermissionTreeVo> permissionTableTree = iSysPermissionService.getPermissionTableTree();
        return R.ok(permissionTableTree);
    }

    @ApiOperation(value = "所有权限菜单接口", notes = "所有权限菜单接口", produces = "application/json")
    @GetMapping("/getPermissionMenuTree")
    public R<List<PermissionTreeVo>> getPermissionMenuTree() {
        ArrayList<PermissionTreeVo> permissionMenuTree = iSysPermissionService.getPermissionMenuTree();
        return R.ok(permissionMenuTree);
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
    @DeleteMapping("/{id}")
    public R<Boolean> delPermission(@PathVariable Long id) {
        return R.ok(iSysPermissionService.delPermission(id));
    }

    @ApiOperation(value = "用户前端路由接口", notes = "用户前端路由接口", produces = "application/json")
    @GetMapping("getRouterByUser")
    public R<List<AntRouterTreeVo>> getRouterByUser() {
        UserDetailsDto userDetails = SecurityUtils.getUserDetails();
        List<AntRouterTreeVo> routerByUser = iSysPermissionService.getRouterByUser(userDetails.getId());
        return R.ok(routerByUser);
    }

}
