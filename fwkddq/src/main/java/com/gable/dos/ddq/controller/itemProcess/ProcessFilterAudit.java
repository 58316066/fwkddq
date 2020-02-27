package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.model.ExamResultAudit;
import com.gable.dos.ddq.model.TableAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.Date;

@Slf4j
public class ProcessFilterAudit implements ItemProcessor<ExamResultAudit, TableAudit> {

    private ApplicationContext context = new ClassPathXmlApplicationContext("datasource/context-datasource.xml");

    private int recordNumber = 0;

    private DriverManagerDataSource contextBean;

    @Override
    public TableAudit process(ExamResultAudit item) throws Exception {

        return queryRecordNumber(item);
    }


    private TableAudit queryRecordNumber(ExamResultAudit item) {
        try {
            contextBean = (DriverManagerDataSource) context.getBean("dataSource");
        } catch (Exception e) {
            log.error("get ContextBean Error :::=> " + e.getMessage());
            log.info(":::::::::::::: System Exit ::::::::::::::");
            SpringApplication.exit(context, () -> 0);
            System.exit(199);
        }

        try {
            String url = contextBean.getUrl();
            String username = contextBean.getUsername();
            String password = contextBean.getPassword();

            Connection connect = DriverManager.getConnection(url, username, password);
            Statement stmt = connect.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT count(*) FROM  " + item.getSchema() + "." + item.getTable());

            while (rs.next()) {
                recordNumber = rs.getInt(1);
                TableAudit tableAudit = new TableAudit();
                tableAudit.setSchema(item.getSchema());
                tableAudit.setTable_name(item.getTable());
                tableAudit.setProcessDate(new Date());
//                tableAudit.setRecordNumber(recordNumber);
                return tableAudit;
            }
        } catch (Exception e) {
            System.err.println("Query || Got an exception! ");
            System.err.println(e.getMessage());
        }

        return null;
    }
}
