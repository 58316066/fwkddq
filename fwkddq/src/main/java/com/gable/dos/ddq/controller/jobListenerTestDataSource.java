package com.gable.dos.ddq.controller;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@Slf4j
public class jobListenerTestDataSource implements JobExecutionListener {
    private DateTime startTime, stopTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = new DateTime();
        log.info("jobTestDataSource starts at :" + startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        stopTime = new DateTime();

        log.info("jobTestDataSource stops at :" + stopTime);
        log.info("Total time take in millis :" + getTimeInMillis(startTime, stopTime));

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            jobExecution.stop();

            log.info("jobTestDataSource job completed Wait....." + jobExecution.getJobInstance().getJobName());
            log.info("jobTestDataSource job Process Again");
            log.info("jobTestDataSource job completed successfully");

        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {

            log.info("jobTestDataSource job failed with following exceptions ");
            List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();

            for (Throwable th : exceptionList) {

                log.error("exception :" + th.getLocalizedMessage());
            }
        }
    }

    private long getTimeInMillis(DateTime start, DateTime stop) {
        return stop.getMillis() - start.getMillis();
    }
}
