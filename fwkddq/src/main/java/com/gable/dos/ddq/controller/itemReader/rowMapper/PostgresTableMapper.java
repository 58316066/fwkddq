package com.gable.dos.ddq.controller.itemReader.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gable.dos.ddq.model.TableAuditCountRecord;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

@Data
@Slf4j
public class PostgresTableMapper implements RowMapper<TableAuditCountRecord> {

    private String dataSource;

    @Override
    public TableAuditCountRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

        TableAuditCountRecord table = new TableAuditCountRecord();

        table.setSchema(rs.getString("TABLE_SCHEMA"));
        table.setTable_name(rs.getString("TABLE_NAME"));
        table.setTable_type(rs.getString("table_type"));
        table.setDatasourceName(dataSource);

//        log.info("this PostgresTableMapper : " + table);

        return table;
    }

}
