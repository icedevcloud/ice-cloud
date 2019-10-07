package com.xiaobingby.upms.api.feign;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.upms.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FeignClient(value = "bing-upms")
@RestController
public interface IFlowFeign {

    /**
     * 通过角色ID查询所有用户
     *
     * @param roleId
     * @return
     */
    @GetMapping("/inside/flow/roleIdByUsers/{roleId}")
    R<List<SysUser>> roleIdByUsers(@PathVariable(name = "roleId") Long roleId);

}
