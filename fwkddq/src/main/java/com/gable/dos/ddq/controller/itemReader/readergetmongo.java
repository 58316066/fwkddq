package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.StampTime;
import lombok.Data;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Data
public class readergetmongo implements ItemReader<Object> {

    private MongoOperations mongoOperations;
    private String collection_name;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


        Query query = new Query();
        query.addCriteria(Criteria.where("StartTime").ne(null));
        Sort sort = new Sort(Sort.Direction.DESC, "StartTime"); //max Date {StartTime}
        query.with(sort);

        try {
            StampTime stampTimes = mongoOperations.findOne(query, StampTime.class, collection_name);
            System.out.println("StampTime = " + stampTimes);
            return stampTimes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
