package com.gable.dos.ddq.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

@Slf4j
public class Connection_executes_result_set implements Connection_execute_resultset {

    private static Connection connections;

    private static boolean connectionStatus = false;
    private static DriverManagerDataSource dataSource = null;
    private static String Bean = "";

    @Override
    public ResultSet getBeanContext(String locationPath, String contextBean, String sql) {

        ApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext(locationPath);
            dataSource = (DriverManagerDataSource) context.getBean(contextBean);


            if (Bean != contextBean) {
                Bean = contextBean;
                connectionStatus = false;
            }

            return connection(dataSource, sql);

        } catch (Exception e) {
            log.error("get ContextBean Error :::=> " + e.getMessage());
            log.info(":::::::::::::: System Exit ::::::::::::::");
            SpringApplication.exit(context, () -> 0);
            System.exit(199);
        }
        return null;
    }

    @Override
    public ResultSet connection(DriverManagerDataSource connection, String sql) {
        if (!connectionStatus) {
            log.info("Connection Start");
            DriverManagerDataSource driver = connection;
            try {
                connections = DriverManager.getConnection(driver.getUrl(), driver.getUsername(), driver.getPassword());
                System.out.println(driver.getUrl() + driver.getUsername() + driver.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            logger.info("Get Connection");
            connectionStatus = true;
        }
        return createStatement(connections, sql);
    }


    @Override
    public ResultSet createStatement(Connection connection, String sql) {
        ResultSet resultSet = null;
        try {
            System.out.println("connection_Catalog :: " + connection.getCatalog());
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(sql);
            logger.info("executeQuery success!");
//            connections.close();

        } catch (Exception e) {
            try {
                Statement stmts = connection.createStatement();
                logger.info("execute success!");
                stmts.execute(sql);

            } catch (SQLException ex) {
                logger.warning("execute failed!");
                logger.warning(ex.getMessage());
                ex.printStackTrace();
            }
            try {
                connections.close();
            } catch (SQLException ex) {
//                ex.printStackTrace();
                log.error("Exception e : " + ex.getMessage());
            }

//            e.printStackTrace();
        }
//        connections.close();

        return resultSet;
    }
}
