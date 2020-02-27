package com.gable.dos.ddq.model;

import lombok.Data;

@Data
public class SizeofTables extends MainOutput {
    private String table_name;
    private String schema;
    private int countRecord;
    private Double TotalSpaceMB;
    private Double UsedSpaceMB;
    private Double UnusedSpaceMB;
}
