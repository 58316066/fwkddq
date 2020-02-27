package com.gable.dos.ddq.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class RelationConfig {
    private String main_schema;
    private String main_table;
    private String main_field;
    private String ref_schema;
    private String ref_table;
    private String ref_field;
}
