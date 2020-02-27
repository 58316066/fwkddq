package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.model.StampTime;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
public class processEnd implements ItemProcessor<Object, StampTime> {
    private String collection_name;
    private MongoOperations mongoOperations;
    private TimeZone thaiTimeZone = TimeZone.getTimeZone("Asia/Bangkok");
    private Locale locale = new Locale("en", "EN");
    private SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
    Config_Datetime config_datetime = new Config_Datetime();

    @Override
    public StampTime process(Object date) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("StartTime").ne(null));
        Sort sort = new Sort(Sort.Direction.DESC, "StartTime"); //max Date {StartTime}
        query.with(sort);

        isoFormat.setTimeZone(thaiTimeZone);
        try {
            StampTime stampTimes = mongoOperations.findOne(query, StampTime.class, collection_name);

            if (stampTimes.getEndTime() == null) {

//                 isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                String strDate = isoFormat.format((Date) date);
                stampTimes.setEndTime(strDate);

                System.out.println("setEndTime :: " + stampTimes.getEndTime());
                mongoOperations.save(stampTimes, collection_name);
            }

            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
