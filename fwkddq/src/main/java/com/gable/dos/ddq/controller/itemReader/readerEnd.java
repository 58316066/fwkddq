package com.gable.dos.ddq.controller.itemReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Date;

public class readerEnd implements ItemReader<Object> {
    private boolean checkTimeStamp = true;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (checkTimeStamp) {
            checkTimeStamp = false;
            return new Date();
        }

        return null;
    }
}
