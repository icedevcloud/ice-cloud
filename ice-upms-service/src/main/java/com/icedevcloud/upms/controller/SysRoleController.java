package com.icedevcloud.upms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.upms.dto.RolePermissionDto;
import com.icedevcloud.upms.entity.SysRole;
import com.icedevcloud.upms.vo.AntRolePermissionTreeVo;
import com.icedevcloud.upms.service.ISysPermissionService;
import com.icedevcloud.upms.service.ISysRolePermissionService;
import com.icedevcloud.upms.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sys_role 角色表 前端控制器
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Api(value = "角色管理接口类", tags = "角色管理", description = "角色管理")
@RestController
@RequestMapping("/upms/role")
public class SysRoleController extends BaseController<ISysRoleService, SysRole, Long> {

    @Autowired
    private ISysRoleService iSysRoleService;

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;

    @ApiOperation(value = "角色查询分页接口", notes = "角色查询分页接口", produces = "application/json")
    @PostMapping("/page")
    @Override
    public R<List<SysRole>> pageList(@RequestBody PageParam pageParam) {
        return super.pageList(pageParam);
    }

    @ApiOperation(value = "添加角色接口", notes = "添加角色接口", produces = "application/json")
    @PostMapping
    public R<Boolean> addRole(@RequestBody SysRole sysRole) {
        boolean save = iSysRoleService.save(sysRole);
        return R.ok(save);
    }

    @ApiOperation(value = "修改角色接口", notes = "修改角色接口", produces = "application/json")
    @PutMapping
    public R<Boolean> updateRole(@RequestBody SysRole sysRole) {
        return R.ok(iSysRoleService.updateById(sysRole));
    }

    @ApiOperation(value = "删除角色接口", notes = "删除角色接口", produces = "application/json")
    @DeleteMapping("{id}")
    public R<Boolean> delRole(@PathVariable Long id) {
        return R.ok(iSysRoleService.delRole(id));
    }

    @ApiOperation(value = "角色名查询角色接口", notes = "角色名查询角色接口", produces = "application/json")
    @GetMapping("/queryRole")
    public R<List<SysRole>> queryRole(String roleName) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(roleName)) {
            queryWrapper.like("name", roleName);
        }
        queryWrapper.orderByDesc("gmt_create");
        List<SysRole> list = iSysRoleService.list(queryWrapper);
        return R.ok(list);
    }

    @ApiOperation(value = "角色权限树查询接口", notes = "角色权限树查询接口", produces = "application/json")
    @GetMapping("/permissionTree/{id}")
    public R<AntRolePermissionTreeVo> getRolePermissionTree(@PathVariable Long id) {
        AntRolePermissionTreeVo rolePermissionTree = iSysPermissionService.getRolePermissionTree(id);
        return R.ok(rolePermissionTree);
    }

    @ApiOperation(value = "分配角色权限接口", notes = "分配角色权限接口", produces = "application/json")
    @PutMapping("/updateRolePermission")
    public R updateRolePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        boolean b = iSysRolePermissionService.updateRolePermissionByRoleId(rolePermissionDto);
        return R.ok(b);
    }

}
