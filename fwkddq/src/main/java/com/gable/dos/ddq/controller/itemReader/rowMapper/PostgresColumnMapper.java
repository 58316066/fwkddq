package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.model.Columns;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class PostgresColumnMapper implements RowMapper<Columns> {

    @Override
    public Columns mapRow(ResultSet rs, int rowNum) throws SQLException {

        Columns columns = new Columns();

        columns.setName(rs.getString("COLUMN_NAME"));
        columns.setType(rs.getString("udt_name"));
        columns.setIsNullAble(rs.getString("IS_NULLABLE"));

        return columns;
    }
}