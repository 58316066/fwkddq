package com.gable.dos.ddq.model;

import java.util.Date;

import lombok.*;

@Data
public class Tables {

	private String schema;

	private String table_name;

    private String rowFormat;

    private Integer rows;

    private Date createTime;

    private Date updateTime;

    private Date checkTime;

    private Date timeStamp = new Date();

    private Integer fieldCount;
}
