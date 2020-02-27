package com.gable.dos.ddq.model;

import lombok.Data;

import java.util.Date;

@Data
public class ResultCompare extends MainOutput{
    private String schema;
    private String result1;
    private String result2;
    private String resultCompare;
    private String status;
}
