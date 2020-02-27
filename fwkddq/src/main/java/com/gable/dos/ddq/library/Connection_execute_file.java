package com.gable.dos.ddq.library;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

@Slf4j
public class Connection_execute_file implements Connection_execute_files {

    private static Connection connections;

    private static boolean connectionStatus = false;
    private static DriverManagerDataSource dataSource = null;
    private static String Bean = "";

    @Override
    public void getBeanContext(String locationPath, String contextBean, String file_name) {

        ApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext(locationPath);
            dataSource = (DriverManagerDataSource) context.getBean(contextBean);


            if (Bean != contextBean) {
                Bean = contextBean;
                connectionStatus = false;
            }

            connection(dataSource, file_name);

        } catch (Exception e) {
            log.error("get ContextBean Error :::=> " + e.getMessage());
            log.info(":::::::::::::: System Exit ::::::::::::::");
            SpringApplication.exit(context, () -> 0);
            System.exit(199);
        }
    }

    @Override
    public void connection(DriverManagerDataSource connection, String file_name) throws Exception {


        if (!connectionStatus) {
            log.info("Connection Start");
            DriverManagerDataSource driver = connection;
            connections = DriverManager.getConnection(driver.getUrl(), driver.getUsername(), driver.getPassword());
            logger.info("Get Connection");
            connectionStatus = true;
        }
        createStatement(connections, file_name);
    }


    @Override
    public void createStatement(Connection connection, String file_name) {
        try {
            System.out.println("connection_Catalog :: " + connection.getCatalog());

            ScriptRunner sr = new ScriptRunner(connection);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader(file_name));
            //Running the script
            sr.runScript(reader);
            logger.info("ScriptRunner runScript [" + file_name + "] success!");

        } catch (Exception e) {
            logger.warning("ScriptRunner runScript [" + file_name + "] failed!");
            try {
                connections.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("Exception e : " + e.getMessage());
//            e.printStackTrace();
        }
//        connections.close();

    }
}
