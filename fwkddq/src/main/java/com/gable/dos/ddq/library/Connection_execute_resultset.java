package com.gable.dos.ddq.library;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Connection_execute_resultset {

    ResultSet getBeanContext(String locationPath, String contextBean, String sql);

    ResultSet connection(DriverManagerDataSource connection, String sql) throws Exception;

    ResultSet createStatement(Connection connection, String sql);
}
