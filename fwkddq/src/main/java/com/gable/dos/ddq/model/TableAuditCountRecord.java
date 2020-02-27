package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.Date;

@Data
public class TableAuditCountRecord extends MainOutput{
    private String schema;

    private String table_name;

//    private String table_type;

    private int countRecord;

    private int fieldNo;

}
