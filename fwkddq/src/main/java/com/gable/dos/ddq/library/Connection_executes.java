package com.gable.dos.ddq.library;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;

@Slf4j
public class Connection_executes implements Connection_execute {

    private static Connection connections;

    private static boolean connectionStatus = false;
    private static DriverManagerDataSource dataSource = null;
    private static String Bean = "";

    @Override
    public boolean getBeanContext(String locationPath, String contextBean, String sql) {

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
        return false;
    }

    @Override
    public boolean connection(DriverManagerDataSource connection, String sql) throws Exception {


        if (!connectionStatus) {
            log.info("Connection Start");
            DriverManagerDataSource driver = connection;
            connections = DriverManager.getConnection(driver.getUrl(), driver.getUsername(), driver.getPassword());
            connectionStatus = true;
        }
        return createStatement(connections, sql);
    }


    @Override
    public boolean createStatement(Connection connection, String sql) {
        try {
            Statement stmts = connection.createStatement();
            stmts.execute(sql);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            connections.close();
        } catch (SQLException ex) {
            log.error("Exception e : " + ex.getMessage());
        }
        return false;
    }
}
