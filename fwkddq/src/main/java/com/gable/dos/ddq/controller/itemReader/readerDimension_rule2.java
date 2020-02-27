package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.TableAudit;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.table_dimension;

public class readerDimension_rule2 implements ItemReader {
    public static List<TableAudit> list_dimension_rule2  = new ArrayList<>(table_dimension);
    public static boolean check_dimension_rule2 = false;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!check_dimension_rule2) {
//            list_dimension_rule2
            if (list_dimension_rule2.size() != 0) {
                return list_dimension_rule2.get(0);
            }
            return null;
        }
        return null;
    }
}
