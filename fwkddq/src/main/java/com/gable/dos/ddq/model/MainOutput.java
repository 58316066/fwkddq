package com.gable.dos.ddq.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MainOutput {
    private Date processDate;
    private Date date;
    private String date_string;
//    private long month_id;
    private String job_id;
    private String datasourceName;
    private String table_type;
    private int version_No;
}
