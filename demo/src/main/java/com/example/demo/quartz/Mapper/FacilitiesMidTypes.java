package com.example.demo.quartz.Mapper;

import lombok.Data;

import java.util.List;

@Data
public class FacilitiesMidTypes {

    private String typeCode;
    private String typeVal;
    private String typeNum;
    private String index;
    private List<FacilitiesMidTypes> facilitiesMidTypes;
}
