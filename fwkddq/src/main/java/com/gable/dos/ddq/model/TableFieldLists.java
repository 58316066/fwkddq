package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Data
public class TableFieldLists extends MainOutput{
    private Date createRecord;

    private Date createDate;

    private Date updateDate;

    private String table_name;

    private String desc;

    private ArrayList<Columns> fieldList;
}
