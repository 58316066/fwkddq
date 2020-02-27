package com.gable.dos.ddq.controller.itemReader;

import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.mongodb.core.MongoTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MongoDropItemReader implements ItemReader<Object> {

    private MongoTemplate template;

    private String collection_name;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        try {
            // TODO Auto-generated method stub
            MongoCollection<Document> collection = template.getCollection(collection_name);
            // log.info("============= collection result ===============" + collection.find().first().toString());
            // log.info(collection.find().toString());
            collection.drop();

            template.createCollection(collection_name);
            log.info("-------------- Drop and Create : "+ collection.getNamespace() + " Success -------------" );
            // log.info("======= count of collecttion ======== " + collection.countDocuments());
        } catch (Exception e) {
            // TODO: handle exception
            log.info("===============Collection is null================");
        }



        // log.info("Drop COllection ===============" + collection);

        return null;
    }

}