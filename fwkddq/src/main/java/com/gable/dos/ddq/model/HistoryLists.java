package com.gable.dos.ddq.model;

import lombok.Data;

@Data
public class HistoryLists {
    private int id;
    private String job_Name;
    private String create_Time;
    private String start_Time;
    private String end_Time;
    private String status;
    private String exit_Code;
    private String exit_Message;
    private String last_Update;
}
