package com.gable.dos.ddq.library;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;

public interface Connection_execute_files {

    void getBeanContext(String locationPath, String contextBean, String file_name);

    void connection(DriverManagerDataSource connection, String file_name) throws Exception;

    void createStatement(Connection connection, String file_name);
}
