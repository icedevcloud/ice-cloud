package com.icedevcloud.upms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icedevcloud.common.core.api.R;
import com.icedevcloud.common.core.page.PageParam;
import com.icedevcloud.common.security.dto.UserDetailsDto;
import com.icedevcloud.common.security.util.SecurityUtils;
import com.icedevcloud.upms.dto.SysUserDetailsDto;
import com.icedevcloud.upms.dto.UserDto;
import com.icedevcloud.upms.entity.SysUser;
import com.icedevcloud.upms.service.ISysUserService;
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
@RequestMapping("/user")
public class SysUserController extends BaseController<ISysUserService, SysUser, Long> {

    @Autowired
    private ISysUserService iSysUserService;

    @ApiOperation(value = "用户查询分页接口", notes = "用户查询分页接口", produces = "application/json")
    @PostMapping("/page")
    @Override
    public R<IPage<SysUser>> pageList(@RequestBody PageParam pageParam) {
        return super.pageList(pageParam);
    }

    @ApiOperation(value = "添加用户接口", notes = "添加用户接口", produces = "application/json")
    @PostMapping
    public R<Boolean> addUser(@RequestBody UserDto userDto) {
        return R.ok(iSysUserService.addUser(userDto));
    }

    @ApiOperation(value = "修改用户角色关联信息", notes = "修改用户角色关联信息", produces = "application/json")
    @PutMapping
    public R<Boolean> updateUserAndUserRole(@RequestBody UserDto userDto) {
        return R.ok(iSysUserService.updateUserAndUserRole(userDto));
    }

    @ApiOperation(value = "修改个人用户信息", notes = "修改个人用户信息", produces = "application/json")
    @PutMapping("updateUser")
    public R<Boolean> updateUser(@RequestBody SysUser sysUser) {
        UserDetailsDto userDetails = SecurityUtils.getUserDetails();
        sysUser.setId(userDetails.getId());
        return R.ok(iSysUserService.updateUse(sysUser));
    }

    @ApiOperation(value = "删除用户接口", notes = "删除用户接口", produces = "application/json")
    @DeleteMapping("{id}")
    public R<Boolean> delUser(@PathVariable Long id) {
        return R.ok(iSysUserService.removeUserById(id));
    }

    @ApiOperation(value = "用户名查询用户信息接口", notes = "auth 服务授权使用接口", produces = "application/json")
    @GetMapping("/loadUserByUsername/{username}")
    public R<SysUserDetailsDto> loadUserByUsername(@PathVariable(name = "username") String username) {
        SysUserDetailsDto sysUserDetailsDto = iSysUserService.loadUserByUsername(username);
        return R.ok(sysUserDetailsDto);
    }

    @ApiOperation(value = "获取当前登录用户信息接口", notes = "获取当前登录用户信息接口", produces = "application/json")
    @GetMapping("/getUserInfo")
    public R<SysUser> getUserInfo() {
        UserDetailsDto userDetails = SecurityUtils.getUserDetails();
        SysUser sysUser = iSysUserService.getById(userDetails.getId());
        sysUser.setPassword(null);
        sysUser.setAvatar("https://file.iviewui.com/dist/a0e88e83800f138b94d2414621bd9704.png");
        return R.ok(sysUser);
    }

    @ApiOperation(value = "用过用户ID查询用户角色信息接口", notes = "用过用户ID查询用户角色信息接口", produces = "application/json")
    @GetMapping("/userRolesInfo/{id}")
    public R<UserDto> userRolesInfo(@PathVariable Long id) {
        UserDto userRolesInfo = iSysUserService.findUserRolesInfo(id);
        return R.ok(userRolesInfo);
    }

}
