package com.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api" , produces = "application/json;charset=utf-8")
@Slf4j
public class DemoController {

    @GetMapping("/showHello")
    public String showHello(){
        System.out.println("master");
        System.out.println("test1");
        System.out.println("test2");
        return "hello";
    }

}
