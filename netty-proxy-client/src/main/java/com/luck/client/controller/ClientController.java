package com.luck.client.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "客户端client")
@EasyController("/client")
public class ClientController {

    @GetMapping("/version")
    public Result version() {
        return ResultFactory.successOf("客户端版本");
    }
}
