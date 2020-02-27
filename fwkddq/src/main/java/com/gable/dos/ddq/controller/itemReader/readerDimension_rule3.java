package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.TableAudit;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.table_dimension;

public class readerDimension_rule3 implements ItemReader {
    public static List<TableAudit> list_dimension_rule3 = new ArrayList<>(table_dimension);
    public static boolean check_dimension_rule3 = false;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!check_dimension_rule3) {
            if (list_dimension_rule3.size() != 0) {
                return list_dimension_rule3.get(0);
            }
            return null;
        }
        return null;
    }

}
