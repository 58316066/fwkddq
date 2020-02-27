package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Schema {

    private String name;

    private String desc;

    private HashMap<String, String> tableList;
}
