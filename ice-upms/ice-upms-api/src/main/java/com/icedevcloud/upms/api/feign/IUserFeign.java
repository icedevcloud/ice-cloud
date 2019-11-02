package com.icedevcloud.upms.api.feign;

import com.icedevcloud.common.core.api.R;
import com.icedevcloud.upms.dto.SysUserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author XiaoBingBy
 * @date 2018-12-23 11:53
 * @since 1.0
 */
@FeignClient(value = "ice-upms")
public interface IUserFeign {

    /**
     * 用户名查询用户信息接口
     *
     * @param username
     * @return
     */
    @GetMapping("/user/loadUserByUsername/{username}")
    R<SysUserDetailsDto> loadUserByUsername(@PathVariable("username") String username);

}
