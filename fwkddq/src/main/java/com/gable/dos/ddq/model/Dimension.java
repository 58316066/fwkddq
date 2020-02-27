package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.Date;

@Data
public class Dimension extends MainOutput{
    private String rule_name;
    private String table_name;
    private String sql;
    private String sql_detail_duplicate;
    private boolean status;
    private String key_PK;
    private String detail_duplicate;
    private int sum_Duplicate;
}
