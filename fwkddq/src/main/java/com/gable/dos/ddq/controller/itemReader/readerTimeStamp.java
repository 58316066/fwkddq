package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.StampTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
public class readerTimeStamp implements ItemReader<Object> {

    private List<StampTime> stampTimeList = new ArrayList<>();

    private MongoOperations mongoOperations;
    private String collection_name;
    public static String start_time = "";
    public static String end_time = "";
    private boolean check = true;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        Query query = new Query();
        query.addCriteria(Criteria.where("StartTime").ne(null));
        Sort sort = new Sort(Sort.Direction.DESC, "StartTime"); //max Date {StartTime}
        query.with(sort);

        try {
            StampTime stampTimes = mongoOperations.findOne(query, StampTime.class, collection_name);
            System.out.println("stampTimes Full :: " + stampTimes);

            if (stampTimes.getEndTime() == null || stampTimes.getStartTime() == null) {

                return null;

            } else {
                System.out.println("getStartTime :: " + stampTimes.getStartTime());
                System.out.println("getEndTime :: " + stampTimes.getEndTime());

                start_time = stampTimes.getStartTime();
                end_time = stampTimes.getEndTime();

                if (check) {
                    check = false;
                    return stampTimes; //---------------
                }
                return null;
            }
        } catch (Exception e) {

            return null;
        }
    }
}
