package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.model.TableAudit;
import org.springframework.batch.item.ItemProcessor;

public class processParallelStep1 implements ItemProcessor<Object,Object> {
    @Override
    public Object process(Object item) throws Exception {
        TableAudit tableAudit = new TableAudit();
        System.out.println("processParallelStep1");

        tableAudit.setTable_name("processParallelStep1");
        tableAudit.setSchema("processParallelStep1");
//        tableAudit.setRecordNumber(90);
        return tableAudit;
    }
}
