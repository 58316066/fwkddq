package com.gable.dos.ddq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
public class ExamResultAudit {

    private String schema;
    private String table;


    @XmlElement(name = "schema")
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @XmlElement(name = "table")
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


}