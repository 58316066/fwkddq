package com.gable.dos.ddq.controller;

import java.util.List;

import com.gable.dos.ddq.controller.itemProcess.ItemLogsProgress;
import com.gable.dos.ddq.controller.itemProcess.ItemSaveTableLists;
import org.joda.time.DateTime;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExamResultJobListener implements JobExecutionListener {

    private DateTime startTime, stopTime;


    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getJobInstance().getJobName();
        startTime = new DateTime();
        log.info("ExamResult Job starts at :" + startTime);
//		System.out.println("ExamResult Job starts at :"+startTime);
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        stopTime = new DateTime();

        log.info("ExamResult Job stops at :" + stopTime);
        log.info("Total time take in millis :" + getTimeInMillis(startTime, stopTime));

//		System.out.println("ExamResult Job stops at :"+stopTime);
//		System.out.println("Total time take in millis :"+getTimeInMillis(startTime , stopTime));

        if (jobExecution.getJobInstance().getJobName().equals("initialAllTables")){
            ItemLogsProgress.result_return2.clear();
            ItemSaveTableLists.tablesAudit_lists.clear();
        }

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            log.info("ExamResult job completed successfully");
//			System.out.println("ExamResult job completed successfully");
            //Here you can perform some other business logic like cleanup
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {

            log.info("ExamResult job failed with following exceptions ");
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
