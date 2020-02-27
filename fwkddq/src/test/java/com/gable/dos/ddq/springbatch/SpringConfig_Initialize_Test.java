package com.gable.dos.ddq.springbatch;

import com.gable.dos.ddq.controller.SpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringConfig_Initialize_Test {

    @Autowired
    private Environment environment;
    private SpringConfig springConfig;
    private ApplicationArguments arguments;

    @Before
    public void initializeTest() {
        System.out.println("\n----------------------------------------------------------------------------------------");
        System.out.println("SpringConfig.initializeTest");
        System.out.println("----------------------------------------------------------------------------------------\n");

        springConfig = new SpringConfig();
    }

    @Test
    public void processTest_SpringConfig_initialize_all() {
        System.out.println("<======================= processTest_SpringConfig_initialize_all =======================>\n");

        String[] args = {"--initialize=all"};

        arguments = new DefaultApplicationArguments(args);

        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            assertTrue(springConfig.getIsTestConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processTest_SpringConfig_initialize_createRepository() {
        System.out.println("<================ processTest_SpringConfig_initialize_createRepository ================>\n");

        String[] args = {"--initialize=createRepository"};
        arguments = new DefaultApplicationArguments(args);

        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            log.info("environment.getProperty : "+ environment.getProperty("initialize.Datasource"));
//            environment.getActiveProfiles()
            assertEquals("true",environment.getProperty("initialize.Datasource"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processTest_SpringConfig_initialize_checkDB() {
        System.out.println("<======================= processTest_SpringConfig_initialize_checkDB =======================>\n");

        String[] args = {"--initialize=checkdb"};
        arguments = new DefaultApplicationArguments(args);

        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            assertTrue(springConfig.getIsTestConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processTest_SpringConfig_initialize_creatMongo() {
        System.out.println("<======================= processTest_SpringConfig_initialize_checkDB =======================>\n");

        String[] args = {"--initialize=creatMongo"};
        arguments = new DefaultApplicationArguments(args);

        springConfig.setEnv(environment);
        springConfig.setArgs(arguments);

        try {
            springConfig.argsComponent();
            log.info("ListInitJobName.length : " + springConfig.getLstInitJobName().length);

            assertNotEquals(0, springConfig.getLstInitJobName().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Success SpringConfig.initializeTest");
        System.out.println("----------------------------------------------------------------------------------------\n");
    }
}

