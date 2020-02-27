package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.GetLastDate;
import com.gable.dos.ddq.model.TableAuditCountRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;
import java.util.List;


@Slf4j
public class ProcessAssignmentData extends StepExecutionListenerSupport implements ItemProcessor<Object, Object> {
    private List<Date> dateList = GetLastDate.dateList;
    private Date date = new Date();

    @Override
    public Object process(Object item) throws Exception {

//        GetLastDate.checkRestart = true;

        log.info("process");

        if (item.getClass() == TableAuditCountRecord.class) {
            return assignmentData((TableAuditCountRecord) item);
        }

        return null;
    }


    private Object assignmentData(TableAuditCountRecord item) {

        TableAuditCountRecord countRecord = new TableAuditCountRecord();
//        countRecord.setMonth_id(new Date().getMonth()+6);
        countRecord.setSchema(item.getSchema());
        countRecord.setTable_name(item.getTable_name());
        countRecord.setTable_type(item.getTable_type());
        countRecord.setCountRecord(0);


        date = dateList.get(0);
//        System.out.println(date);
        System.out.println("====================="+ date);
        countRecord.setProcessDate(date);
        countRecord.setFieldNo(0);
        countRecord.setJob_id("AuditStructure1");

        return countRecord;
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
