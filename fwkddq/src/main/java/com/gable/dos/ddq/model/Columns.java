package com.gable.dos.ddq.model;

import lombok.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;
import java.util.TimeZone;


@Data
public class Columns {

	private String name;

	private String type;
	
	private String isNullAble;

	private String key;

	private String defult;

	private String extra;

	private Date timeStamp = new Date();

	private String schema;

	private String table_name;
}
