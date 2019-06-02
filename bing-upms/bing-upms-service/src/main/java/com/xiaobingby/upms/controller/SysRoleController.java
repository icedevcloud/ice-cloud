package com.xiaobingby.upms.controller;


import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.core.page.PageParams;
import com.xiaobingby.upms.entity.SysRole;
import com.xiaobingby.upms.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "角色查询分页接口", notes = "角色查询分页接口", produces = "application/json")
    @PostMapping("/page")
    @Override
    public R pageList(PageParams pageParams) {
        return super.pageList(pageParams);
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
        return R.ok( iSysRoleService.updateById(sysRole));
    }

    @ApiOperation(value = "删除角色接口", notes = "删除角色接口", produces = "application/json")
    @DeleteMapping("{ids}")
    public R<Boolean> delRole(@PathVariable Long[] ids) {
        return R.ok(iSysRoleService.delRole(ids));
    }

}
