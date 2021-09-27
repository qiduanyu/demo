package com.example.demo.quartz;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FacilitiesAttr {

    private String attributeId;

    private String value;

    private List<Map<String,Object>> Details;

}
