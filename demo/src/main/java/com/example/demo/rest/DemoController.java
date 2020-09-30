package com.example.demo.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Demo接口")
@RestController
@RequestMapping(path = "/api" , produces = "application/json;charset=utf-8")
@Slf4j
public class DemoController {

    @GetMapping("/showHello")
    @ApiOperation(value = "showHello",notes = "欢迎信息")
    public String showHello(){
        System.out.println("master");
        System.out.println("test1");
        System.out.println("test2");
        return "hello";
    }

}
