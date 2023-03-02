package com.lazy.netty.proxy.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "服务端")
@EasyController("/server")
public class ServerController {

    @GetMapping("/version")
    public Result version() {
        return ResultFactory.successOf("服务端端版本");
    }
}
