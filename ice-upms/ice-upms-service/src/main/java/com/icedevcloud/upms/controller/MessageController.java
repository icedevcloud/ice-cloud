package com.icedevcloud.upms.controller;

import com.icedevcloud.common.core.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "消息管理接口类", tags = "消息管理", description = "消息管理")
@RestController
@RequestMapping("/message")
public class MessageController {

    @ApiOperation(value = "获取消息数据接口", notes = "获取消息数据接口", produces = "application/json")
    @GetMapping("/count")
    public R<Integer>  messageCount() {
        return R.ok(1);
    }

}
