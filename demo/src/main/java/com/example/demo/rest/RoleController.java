package com.example.demo.rest;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.user.User;
import com.example.demo.service.RoleService;
import com.example.demo.utils.ApiResponseUtils;
import com.example.demo.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 75412
 *
 * 权限相关接口
 */

@Api(tags = "权限相关接口")
@RestController
@RequestMapping(path = "/api/role",produces = "application/json;charset=utf-8")
@Slf4j
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询权限")
    @PostMapping("")
    public ApiResponseEntity<Boolean> add(
            @ApiParam(value = "用户实体类") @RequestBody User user
    ){
//        userService.add(user);
        return ApiResponseUtils.success();
    }

    @ApiOperation(value = "生成token")
    @GetMapping("/token")
    public ApiResponseEntity<String> token(
            @ApiParam(value = "用户账号") @RequestParam String username,
            @ApiParam(value = "用户密码") @RequestParam String password
    ){
        return ApiResponseUtils.success(TokenUtils.token(username,password));
    }

    @ApiOperation(value = "验证token")
    @GetMapping("/verifyToken")
    public ApiResponseEntity<Boolean> verifyToken(
            @ApiParam(value = "用户token") @RequestParam String token
    ){
        return ApiResponseUtils.success(TokenUtils.verify(token));
    }

}
