package com.example.demo.quartz;

import com.example.demo.quartz.Mapper.FacilitiesMidTypes;
import lombok.Data;

import java.util.List;

@Data
public class ProjectAll {

    private String Code;
    private String Name;
    private String Num;
    private String index;
    private List<ProjectAll> projectAll;

}
