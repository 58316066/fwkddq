
package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.model.Columns;
import com.gable.dos.ddq.model.TableAudit;
import com.gable.dos.ddq.model.TableAuditCountRecord;
import com.gable.dos.ddq.model.Tables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.util.ArrayList;

@Slf4j
@Data
@EnableScheduling
public class ItemSaveTableLists implements ItemProcessor<Object, Object> {

    public static ArrayList<Tables> tables_lists = new ArrayList<>();
    public static ArrayList<TableAuditCountRecord> TablePostgres_list = new ArrayList<>();

    public static ArrayList<TableAudit> tablesAudit_lists = new ArrayList<>();

//
//    public static ArrayList<String> result_returns = new ArrayList<>();


    @Override
    public Object process(Object result) throws Exception {

        if (result.getClass() == Tables.class) {

            tables_lists.add((Tables) result);

        } else if (result.getClass() == TableAudit.class) {

            tablesAudit_lists.add((TableAudit) result);

        }

        return null;
    }
}
