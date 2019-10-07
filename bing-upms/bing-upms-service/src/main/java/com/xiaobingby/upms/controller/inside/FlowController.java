package com.xiaobingby.upms.controller.inside;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.upms.entity.SysUser;
import com.xiaobingby.upms.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "flow接口类", tags = "flow接口类", description = "flow接口类")
@RequestMapping("/inside/flow")
@RestController
public class FlowController {

    @Autowired
    private ISysRoleService iSysRoleService;

    @ApiOperation(value = "通过角色ID查询所有用户", notes = "通过角色ID查询所有用户", produces = "application/json")
    @GetMapping("roleIdByUsers/{roleId}")
    public R<List<SysUser>> roleIdByUsers(@PathVariable(name = "roleId") Long roleId) {
        List<SysUser> sysUsers = iSysRoleService.roleIdByUsers(roleId);
        return R.ok(sysUsers);
    }

}
