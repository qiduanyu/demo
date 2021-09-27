package com.example.demo.quartz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Project {
    // 项目编码
    private String projectCode;
    // 项目名称
    private String projectName;

    private String parentCode;
    // 子项目
    private List<Project> children = new ArrayList<>();
}
