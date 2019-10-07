package com.xiaobingby.upms.controller;


import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.security.dto.UserDetailsDto;
import com.xiaobingby.common.security.util.SecurityUtils;
import com.xiaobingby.upms.entity.SysPermission;
import com.xiaobingby.upms.service.ISysPermissionService;
import com.xiaobingby.upms.vo.AntRouterTreeVo;
import com.xiaobingby.upms.vo.PermissionTableTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R<List<PermissionTableTreeVo>> getPermissionTableTree() {
        List<PermissionTableTreeVo> permissionTableTreeVos = iSysPermissionService.getPermissionTableTree();
        return new R<List<PermissionTableTreeVo>>().ok(permissionTableTreeVos);
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

    @ApiOperation(value = "用户前端路由接口", notes = "用户前端路由接口", produces = "application/json")
    @GetMapping("getRouterByUser")
    public R<List<AntRouterTreeVo>> getRouterByUser() {
        UserDetailsDto userDetails = SecurityUtils.getUserDetails();
        List<AntRouterTreeVo> routerByUser = iSysPermissionService.getRouterByUser(userDetails.getId());
        return R.ok(routerByUser);
    }

}
