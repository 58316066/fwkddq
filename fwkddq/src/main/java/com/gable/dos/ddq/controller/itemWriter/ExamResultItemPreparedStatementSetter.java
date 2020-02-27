package com.gable.dos.ddq.controller.itemWriter;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import com.gable.dos.ddq.model.ExamResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExamResultItemPreparedStatementSetter implements ItemPreparedStatementSetter<ExamResult> {

    public void setValues(ExamResult result, PreparedStatement ps) throws SQLException {


        log.info("Start setValues " + result.getStudentName());

        ps.setString(1, result.getStudentName());
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        ps.setDouble(3, result.getPercentage());

        log.info("Stop setValues");
    }

}