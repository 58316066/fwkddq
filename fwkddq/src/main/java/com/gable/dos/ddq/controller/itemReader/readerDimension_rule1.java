package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.table_dimension;

@Data
public class readerDimension_rule1 implements ItemReader {
    public static List<TableAudit> list_dimension_rule1  = new ArrayList<>(table_dimension);
    public static boolean check_dimension_rule1 = false;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!check_dimension_rule1) {
//            list_dimension_rule1 = new ArrayList<>(table_dimension);
            if (list_dimension_rule1.size() != 0) {
                return list_dimension_rule1.get(0);
            }
            return null;
        }
        return null;
    }
}
