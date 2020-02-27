package com.gable.dos.ddq.controller.itemReader.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gable.dos.ddq.model.Tables;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;


@Slf4j
public class TableMapper implements RowMapper<Tables> {

    @Override
    public Tables mapRow(ResultSet rs, int rowNum) throws SQLException {

        log.info("(Reader) Start mapRow this."+this.getClass()+ " : rs = " +rs.getString("TABLE_NAME") );
        Tables table = new Tables();

        table.setSchema(rs.getString("TABLE_SCHEMA"));
        table.setTable_name(rs.getString("TABLE_NAME"));
        table.setRowFormat(rs.getString("ROW_FORMAT"));
        table.setRows(rs.getInt("TABLE_ROWS"));
        table.setCreateTime(rs.getDate("CREATE_TIME"));
        table.setUpdateTime(rs.getDate("UPDATE_TIME"));
        table.setCheckTime(rs.getDate("CHECK_TIME"));

          log.info("ResultSet rs : "+ table);


        return table;
    }

}
