package com.gable.dos.ddq.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class StampTime {
    private ObjectId _id;
    private String StartTime;
    private String EndTime;

}
