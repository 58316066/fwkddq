package com.gable.dos.ddq.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;


@Data
public class HistoryRepo {
    private String start_Time;
    private String end_Time;
    private ArrayList<HistoryLists> historyLists;

}

