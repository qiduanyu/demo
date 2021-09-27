package com.example.demo.quartz;

import lombok.Data;

@Data
public class SmsLoginParam {
    private String phone;
    private String verification;
}
