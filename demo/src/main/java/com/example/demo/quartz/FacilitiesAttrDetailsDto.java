package com.example.demo.quartz;

import lombok.Data;

import java.util.List;

@Data
public class FacilitiesAttrDetailsDto {

    private String key;

    private String keyDesc;

    private List<String> dataVal;

    private String dataValType;

    private String  dataType;

}
