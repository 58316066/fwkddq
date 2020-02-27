package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.compare.process.FileCommandsVisitor;
import com.gable.dos.ddq.model.ReadJson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.mongodb.core.MongoTemplate;


@Slf4j
@Data
public class ReadJSON implements ItemReader{
    private MongoTemplate template;

    private String collection_name;

    private Boolean check = true;

    @Override
    public ReadJson read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        ReadJson readJson = new ReadJson();

        String dataHTML = FileCommandsVisitor.output;

        if (check){
            readJson.setData(dataHTML);
            check = false;
            return readJson;
        }

//        log.info("dataHTML" + dataHTML);

        return  null;


    }

//    @Override
//    public void write(List list) throws Exception {
//
//    }
}
