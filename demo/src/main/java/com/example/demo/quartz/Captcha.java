package com.example.demo.quartz;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Captcha {
    private String captchaKey;
    private Integer salt;
    private String imgStr;
    private String piccode;
}
