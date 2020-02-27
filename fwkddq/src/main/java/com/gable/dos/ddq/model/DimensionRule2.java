package com.gable.dos.ddq.model;

import lombok.Data;

@Data
public class DimensionRule2 extends Dimension{
    private String main_schema;
    private String main_table;
    private String main_field;
    private String ref_schema;
    private String ref_table;
    private String ref_field;
}
