package com.example.demo.quartz.Equipment;

import lombok.Data;

import java.util.List;

@Data
public class EquipMin {

    private String equipMinName;

    private String equipAmount;

    private List<Equip> equipList;
}
