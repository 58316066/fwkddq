package com.gable.dos.ddq.controller.itemReader.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gable.dos.ddq.model.Columns;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.RowMapper;

@Slf4j
public class ColumnMapper implements RowMapper<Columns> {

    @Override
    public Columns mapRow(ResultSet rs, int rowNum) throws SQLException {

        Columns columns = new Columns();

        columns.setName(rs.getString("COLUMN_NAME"));
        columns.setType(rs.getString("COLUMN_TYPE"));
        columns.setIsNullAble(rs.getString("IS_NULLABLE"));
        columns.setKey(rs.getString("COLUMN_KEY"));
        columns.setDefult(rs.getString("COLUMN_DEFAULT"));
        columns.setExtra(rs.getString("EXTRA"));
        columns.setSchema(rs.getString("TABLE_SCHEMA"));
        columns.setTable_name(rs.getString("TABLE_NAME"));

        return columns;
    }
}
