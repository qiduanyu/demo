package com.example.demo.rest;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.user.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 75412
 *
 * Demo增删改查示例接口
 */

@Api(tags = "Demo增删改查示例接口")
@RestController
@RequestMapping(path = "/api/user",produces = "application/json;charset=utf-8")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增用户")
    @PostMapping("")
    public ApiResponseEntity<Boolean> add(
            @ApiParam(value = "用户实体类") @RequestBody User user
            ){
        userService.add(user);
        return new ApiResponseEntity<>();
    }

}
