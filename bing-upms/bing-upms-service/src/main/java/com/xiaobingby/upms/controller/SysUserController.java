package com.xiaobingby.upms.controller;


import com.xiaobingby.common.core.api.R;
import com.xiaobingby.common.core.page.PageParams;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import com.xiaobingby.upms.dto.UserDto;
import com.xiaobingby.upms.entity.SysUser;
import com.xiaobingby.upms.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * sys_user 用户表 前端控制器
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Api(value = "用户管理接口类", tags = "用户管理", description = "用户管理")
@RestController
@RequestMapping("/upms/user")
public class SysUserController extends BaseController<ISysUserService, SysUser, Long> {

    @Autowired
    private ISysUserService iSysUserService;

    @ApiOperation(value = "用户查询分页接口", notes = "用户查询分页接口", produces = "application/json")
    @PostMapping("/page")
    @Override
    public R pageList(PageParams pageParams) {
        return super.pageList(pageParams);
    }

    @ApiOperation(value = "添加用户接口", notes = "添加用户接口", produces = "application/json")
    @PostMapping
    public R<Boolean> addUser(@RequestBody UserDto userDto) {
        return R.ok(iSysUserService.addUser(userDto));
    }

    @ApiOperation(value = "修改用户接口", notes = "修改用户接口", produces = "application/json")
    @PutMapping
    public R<Boolean> updateUser(@RequestBody UserDto userDto) {
        return R.ok(iSysUserService.updateUser(userDto));
    }

    @ApiOperation(value = "删除用户接口", notes = "删除用户接口", produces = "application/json")
    @DeleteMapping("{ids}")
    public R<Boolean> delUser(@PathVariable Long[] ids) {
        return R.ok(iSysUserService.removeUserByIds(ids));
    }

    @ApiOperation(value = "用户名查询用户信息接口", notes = "auth 服务授权使用接口", produces = "application/json")
    @GetMapping("/loadUserByUsername/{username}")
    public R<SysUserDetailsDto> loadUserByUsername(@PathVariable(name = "username") String username) {
        SysUserDetailsDto sysUserDetailsDto = iSysUserService.loadUserByUsername(username);
        return R.ok(sysUserDetailsDto);
    }

}
