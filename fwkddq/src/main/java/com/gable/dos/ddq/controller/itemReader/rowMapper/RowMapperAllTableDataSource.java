package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.model.TableAudit;
import com.gable.dos.ddq.model.TableAuditCountRecord;
import javafx.beans.property.Property;
import lombok.Data;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


@Data
public class RowMapperAllTableDataSource implements RowMapper<TableAudit> {
    @Autowired
    JobExplorerFactoryBean jobExplorerFactoryBean;

    @Autowired
    JobExplorer jobExplorer;

    private String datasourceName;

    @Override
    public TableAudit mapRow(ResultSet rs, int rowNum) throws SQLException {
//        System.out.println("JobName " + jobExplorer.getJobNames());

        TableAudit table = new TableAudit();

        table.setSchema(rs.getString("TABLE_SCHEMA"));
        table.setTable_name(rs.getString("TABLE_NAME"));
        table.setTable_type("N/A");
        table.setDatasourceName(datasourceName);

        return table;
    }

}
