package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.model.StampTime;
import org.springframework.batch.item.ItemProcessor;

public class Itemgetmongo implements ItemProcessor<StampTime, Object> {
    @Override
    public Object process(StampTime item) throws Exception {


        System.out.println("process " + item);

        return item;
    }
}
