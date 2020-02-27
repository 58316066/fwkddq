package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.model.TableAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.table_dimension;

@Slf4j
public class ProcessTableType_Dimension implements ItemProcessor<Object, Object> {
    @Override
    public Object process(Object item) throws Exception {


        if (table_dimension.size() != 0) {
            TableAudit tableAudit = (TableAudit) item;
            log.info(String.valueOf(tableAudit));
            table_dimension.remove(0);
        }
        return null;

    }
}
