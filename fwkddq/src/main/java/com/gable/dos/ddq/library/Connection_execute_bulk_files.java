package com.gable.dos.ddq.library;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;

public interface Connection_execute_bulk_files {

    boolean getBeanContext(String locationPath, String contextBean, String file_name);

    boolean connection(DriverManagerDataSource connection, String file_name) throws Exception;

    boolean createStatement(Connection connection, String file_name);
}
