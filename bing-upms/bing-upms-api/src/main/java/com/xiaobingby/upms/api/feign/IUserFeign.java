package com.xiaobingby.upms.api.feign;

import com.xiaobingby.common.core.api.R;
import com.xiaobingby.upms.dto.SysUserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author XiaoBingBy
 * @date 2018-12-23 11:53
 * @since 1.0
 */
@FeignClient(value = "bing-upms")
public interface IUserFeign {

    /**
     * 用户名查询用户信息接口
     *
     * @param username
     * @return
     */
    @GetMapping("/upms/user/loadUserByUsername/{username}")
    R<SysUserDetailsDto> loadUserByUsername(@PathVariable("username") String username);

}
