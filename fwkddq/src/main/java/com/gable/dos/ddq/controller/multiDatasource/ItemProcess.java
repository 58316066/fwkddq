package com.gable.dos.ddq.controller.multiDatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ItemProcess implements ItemProcessor<String,String> {
    public static String ResourceSet_1;
    public static String ResourceSet_2;

    @Override
    public String process(String result) throws Exception {
        if (ResourceSet_1 == null){
            ResourceSet_1 = result;
        }else {
            ResourceSet_2 = result;
        }

        log.info("ResourceSet_1 = " + ResourceSet_1);
        log.info("ResourceSet_2 = " + ResourceSet_2);
        return null;
    }
}
