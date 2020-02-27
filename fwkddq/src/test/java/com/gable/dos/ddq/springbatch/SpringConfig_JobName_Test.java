package com.gable.dos.ddq.springbatch;

import com.gable.dos.ddq.controller.SpringConfig;
import lombok.extern.slf4j.Slf4j;
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
public class SpringConfig_JobName_Test {

    @Autowired
    private Environment environment;
    private SpringConfig springConfig;
    private ApplicationArguments arguments;

    @Before
    public void JobNameTest() {
        System.out.println("\n----------------------------------------------------------------------------------------");
        System.out.println("SpringConfigTest.JobNameTest");
        System.out.println("----------------------------------------------------------------------------------------\n");

        springConfig = new SpringConfig();
    }

    @Test
    public void processTest_SpringConfig_JobName_all() {
        System.out.println("<======================= processTest_SpringConfig_JobName_all =======================>\n");

        String[] arg = {"--JobName=all"};
        arguments = new DefaultApplicationArguments(arg);
        springConfig = new SpringConfig();

        try {
            springConfig.setArgs(arguments);
            springConfig.argsComponent();

            assertEquals("all", springConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void processTest_SpringConfig_JobName_initialAllTables() { //job_list3
        System.out.println("<======================= processTest_SpringConfig_JobName_initialAllTables =======================>\n");

        String[] args = {"--JobName=job_list3"};
        arguments = new DefaultApplicationArguments(args);

        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            assertEquals("job_list3", springConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void processTest_SpringConfig_JobName_examResultJob_1() {
        System.out.println("<======================= processTest_SpringConfig_JobName_examResultJob_1 =======================>\n");

        String[] args = {"--JobName=examResultJob_1"};
        arguments = new DefaultApplicationArguments(args);

        springConfig = new SpringConfig();
        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            assertEquals("examResultJob_1", springConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void processTest_SpringConfig_JobName_examResultJob_2() {
        System.out.println("<======================= processTest_SpringConfig_JobName_examResultJob_2 =======================>\n");

        String[] args = {"--JobName=examResultJob_2"};
        arguments = new DefaultApplicationArguments(args);

        springConfig = new SpringConfig();
        springConfig.setArgs(arguments);
        springConfig.setEnv(environment);

        try {
            springConfig.argsComponent();
            assertEquals("examResultJob_2", springConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}