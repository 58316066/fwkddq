package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.Date;

@Data
public class TableRecNo {

    private String schema;

    private String table_name;

    private int tableRecNo;

}
