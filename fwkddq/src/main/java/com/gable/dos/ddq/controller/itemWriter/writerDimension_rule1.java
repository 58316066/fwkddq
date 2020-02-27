package com.gable.dos.ddq.controller.itemWriter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class writerDimension_rule1 implements ItemWriter {
    @Override
    public void write(List items) throws Exception {

      log.info(items.toString());

    }
}
