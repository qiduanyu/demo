package com.example.demo.quartz;

import lombok.Data;

@Data
public class UserLoginParam {
    private String username;
    private String password;
    private String captchaKey;
    private String captcha;
    private String token;
}
