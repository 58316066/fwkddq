package com.gable.dos.ddq.controller.itemProcess;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gable.dos.ddq.model.ExamResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {

    @Override
    public ExamResult process(ExamResult result) throws Exception {

//		System.out.println("Processing result :"+result);

        /*
         * Only return results which are more than 75%
         *
         */

        if (result.getPercentage() < 75) {

            log.info("Processing result :" + result + " filter out..");
            return null;

        } else
            log.info("Processing result :" + result);

        return result;
    }

    //For test setup schedule
    public void demoSchduleMethod() {
        log.info("Method executed at every 5 seconds. Current time is :: " + new Date());
    }

}
