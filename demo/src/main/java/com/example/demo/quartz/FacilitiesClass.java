package com.example.demo.quartz;

import lombok.Data;

import java.util.List;

@Data
public class FacilitiesClass {

    private String type;
    private String Name;
    private Integer totalnum;//
    private String level;
    private List<FacilitiesClass> children;
}
