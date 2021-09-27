package com.example.demo.rest;

import com.example.demo.quartz.*;
import com.example.demo.quartz.Equipment.EquipmentParam;
import com.example.demo.quartz.asset.AssetAll;
import com.example.demo.quartz.asset.assetValue.AssetValueParam;
import com.example.demo.service.DemoTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/scshj" ,produces = "application/json;charset=utf-8")
@Api(tags = "第三方数据请求的相关接口")
@Slf4j
public class DemoTestController {

    @Autowired
    private DemoTestService demoTestService;

    // 获取盐
    @ApiOperation(value = "获取盐")
    @GetMapping("/login/captcha")
    public ResponseResult captcha() {
        return ResponseResult.success("查询成功", demoTestService.captcha());
    }

    // 账号密码登陆
    @ApiOperation(value = "账号密码登陆")
    @PostMapping("/login/user")
    public ResponseResult user(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request) {
        LoginUser loginUser = demoTestService.userLogin(userLoginParam,request);
        if (null == loginUser){
            return ResponseResult.error(403,"账号或密码错误");
        }
        return ResponseResult.success("查询成功", loginUser);
    }

    // 短信验证
    @ApiOperation(value = "短信验证")
    @GetMapping("/login/verification/{phone}")
    public ResponseResult verification(@PathVariable String phone) {
        Verification verification = demoTestService.verification(phone);
        if ("1".equals(verification.getSuccess())){
            return ResponseResult.success("短信发送请求成功", verification);
        }
        return ResponseResult.error(502,"短信发送请求失败", verification);
    }

    //验证码登录
    @ApiOperation(value = "验证码登录")
    @PostMapping("/login/sms")
    public ResponseResult sms(@RequestBody SmsLoginParam smsLoginParam) {
        LoginUser loginUser = demoTestService.smsLogin(smsLoginParam);
        if (null == loginUser){
            return ResponseResult.error(403,"验证码错误");

        }
        return ResponseResult.success("查询成功", loginUser);
    }

    // 登出接口
    @ApiOperation(value = "登出接口")
    @PostMapping("/login/logout")
    public ResponseResult logout() {

        return ResponseResult.success("登出成功", "登出成功");
    }

    // 查询当前用户的项目
    @ApiOperation(value = "查询当前用户的项目")
    @GetMapping("/project/currentUser")
    public ResponseResult currentUserProject(HttpServletRequest request) {

        return ResponseResult.success("查询成功", demoTestService.currentUserProject(request));
    }

    // 设施查询接口 1（根据设施或设备查询中类和小类设施）
    @ApiOperation(value = "设施查询接口 1（根据设施或设备查询中类和小类设施）")
    @PostMapping("/facilities/all")
    public ResponseResult all(@RequestBody AllSearch allSearch) {

        return ResponseResult.success("查询成功", demoTestService.all(allSearch));
    }

    // 设施查询接口 2（根据中类设施编码查询小类设施）
    @ApiOperation(value = "设施查询接口 2（根据中类设施编码查询小类设施）")
    @PostMapping("/facilities/midToMin")
    public ResponseResult midToMin(@RequestBody AllSearchTwo allSearch) {

        return ResponseResult.success("查询成功", demoTestService.midToMin(allSearch));
    }

    // 根据设施编码查询设施属性信息接口
    @ApiOperation(value = "根据设施编码查询设施属性信息接口")
    @PostMapping("/facilities/details/attribute")
    public ResponseResult attribute(@RequestBody AllSearchAttr allSearchAttr) {

        return ResponseResult.success("查询成功", demoTestService.attribute(allSearchAttr));
    }

    // 2.13根根据项目编码查询接口-项目->子项目->中类设施->小类设施
    @ApiOperation(value = "2.13根据项目编码查询接口-项目->子项目->中类设施->小类设施")
    @GetMapping("/facilities/class/{projectCode}")
    public ResponseResult projectAll(@PathVariable String projectCode) {

        Object o = demoTestService.projectAll(projectCode);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    // 通过项目编码，中类设施编码，获取该设施下所含设备列表名录
    @ApiOperation(value = "2.10通过项目编码，中类设施编码，获取该设施下所含设备列表名录")
    @PostMapping("/equipment/list/mid")
    public ResponseResult equipmentList(@RequestBody EquipmentParam equipmentParam) {

        Object o = demoTestService.equipmentList(equipmentParam);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }
    // 通过项目编码，小类设施编码，获取设备列表名录
    @ApiOperation(value = "2.11通过项目编码，小类设施编码，获取设备列表名录")
    @PostMapping("/equipment/list/min")
    public ResponseResult equipmentMinList(@RequestBody EquipmentParam equipmentParam) {

        Object o = demoTestService.equipmentMinList(equipmentParam);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    // 通过项目编码，设备编码，获取设备动态属性信息、详情信息。
    @ApiOperation(value = "2.12通过项目编码，设备编码，获取设备动态属性信息、详情信息。")
    @PostMapping("/equipment/details/attribute")
    public ResponseResult equipmentAttribute(@RequestBody EquipmentParam equipmentParam) {

        Object o = demoTestService.equipmentAttribute(equipmentParam);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    @ApiOperation(value = "2.14根据项目编码、中类设施代码查询中类设施名称接口。")
    @PostMapping("/facilities/name")
    public ResponseResult facilitiesName(@RequestBody FacilitiesNameParam facilitiesNameParam) {

        Object o = demoTestService.facilitiesName(facilitiesNameParam);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    @ApiOperation(value = "2.15根据项目编码、设施代码删除设施属性接口")
    @PostMapping("/facilities/delete")
    public ResponseResult facilitiesDelete(@RequestBody AllSearchAttr allSearchAttr) {

        Object o = demoTestService.facilitiesDelete(allSearchAttr);
        System.out.println(o);
        return ResponseResult.success("删除成功", o);
    }

    @ApiOperation(value = "2.16根据项目编码查询项目指标统计信息")
    @PostMapping("/asset/all")
    public ResponseResult assetAll(@RequestBody AssetAll assetAll) {

        Object o = demoTestService.assetAll(assetAll);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    @ApiOperation(value = "2.17根据项目编码和项目指标类型查询具体设施指标数值")
    @PostMapping("/asset/value")
    public ResponseResult assetValue(@RequestBody AssetValueParam assetValueParam) {

        Object o = demoTestService.assetValue(assetValueParam);
        System.out.println(o);
        return ResponseResult.success("查询成功", o);
    }

    //
    @ApiOperation(value = "新增第三方数据测试使用")
    @PostMapping("/testData")
    public ResponseResult testData(){
        return ResponseResult.success("新增成功", demoTestService.testFacilitiesAttrDetailData());
    }

    @ApiOperation(value = "新增第三方数据测试使用-调蓄池新增")
    @PostMapping("/testAddTxcData")
    public ResponseResult testAddTxcData(@RequestBody FacilitiesMinEight facilitiesMin){
        return ResponseResult.success("新增成功", demoTestService.testAddTxcData(facilitiesMin));
    }

    @ApiOperation(value = "新增第三方数据测试使用-项目新增")
    @PostMapping("/testAddProject")
    public ResponseResult testAddProject(@RequestBody Project project){
        Object o = demoTestService.testAddProject(project);
        if (o instanceof String){
            return ResponseResult.error(500,o.toString());
        }
        return ResponseResult.success("新增成功", o);
    }
}


