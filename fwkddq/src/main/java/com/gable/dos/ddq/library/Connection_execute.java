package com.gable.dos.ddq.library;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;

public interface Connection_execute {

    boolean getBeanContext(String locationPath, String contextBean, String sql);

    boolean connection(DriverManagerDataSource connection, String sql) throws Exception;

    boolean createStatement(Connection connection, String sql);
}
