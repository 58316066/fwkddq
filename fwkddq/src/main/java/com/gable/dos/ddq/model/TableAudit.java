package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.Date;

@Data
public class TableAudit extends MainOutput{

    private String schema;

    private String table_name;

    private String primary_key;

//    private String table_type;

//    private int recordNumber;


}
