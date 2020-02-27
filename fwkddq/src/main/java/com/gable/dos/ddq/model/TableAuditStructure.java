package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class TableAuditStructure extends MainOutput{
    private String schema;
    private String table_name;
    private int fieldNo;
    private ArrayList<Object> fieldList;
}

