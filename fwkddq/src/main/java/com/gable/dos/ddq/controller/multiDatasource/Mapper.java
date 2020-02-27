package com.gable.dos.ddq.controller.multiDatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class Mapper implements RowMapper {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("multi DataSource {Reader} this Result set :==> "+ rs.getString("resultCount"));
        return rs.getString("resultCount");
    }
}
