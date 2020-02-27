package com.gable.dos.ddq.controller;

import com.gable.dos.ddq.library.GetLastDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Date;
import java.util.List;

@Slf4j
public class Delete_list extends StepExecutionListenerSupport implements ItemReader<Object> {
    List<Date> dateList;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (GetLastDate.dateList.size() != 0) {

            GetLastDate.dateList.remove(0);

            if (GetLastDate.dateList.size() != 0) {
                dateList = GetLastDate.dateList;
                log.info("dateList.Size == " + dateList.size());
                log.info("dateList.Is == " + dateList.get(0));
            } else {
                return null;
            }
        } else {
            return null;
        }

        return null;
    }


    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();

        if (dateList.size() != 0) {
            log.info("ExitStatus = COMPLETED");
            return new ExitStatus("COMPLETED");
        }
        log.info("ExitStatus = FAILED");
        return new ExitStatus("FAILED");
    }
}
