package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.model.StampTime;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
public class processStarted implements ItemProcessor<Object, StampTime> {

    private MongoOperations mongoOperations;
    private String collection_name;
    private Locale locale = new Locale("en", "EN");
    private TimeZone thaiTimeZone = TimeZone.getTimeZone("Asia/Bangkok");
    private SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);

    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private String SQL;
    private ResultSet rs;


    private Config_Datetime config_datetime = new Config_Datetime();

    @Override
    public StampTime process(Object date) throws Exception {

//        Date date = config_datetime.plustDate();
        Query query = new Query();
        query.addCriteria(Criteria.where("StartTime").ne(null));
        Sort sort = new Sort(Sort.Direction.DESC, "StartTime"); //max Date {StartTime}
        query.with(sort);


        isoFormat.setTimeZone(thaiTimeZone);

        try {
            StampTime stampTimes = mongoOperations.findOne(query, StampTime.class, collection_name);
            System.out.println("TimeStamp :: " + stampTimes);
            if (stampTimes.getEndTime() == null) {
                return null;
            } else {

//                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                isoFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//                Date date = isoFormat.parse("2010-05-23T09:01:02");


//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                String strDate = isoFormat.format((Date) date);


//                Date date = isoFormat.parse(strDate);


                StampTime stampTime = new StampTime();
                stampTime.setStartTime(strDate);
                System.out.println("setStartTime :: " + stampTime.getStartTime());


                return stampTime;
            }
        } catch (Exception e) {
            String strDate = isoFormat.format((Date) date);
            StampTime stampTime = new StampTime();
            stampTime.setStartTime(strDate);
            System.out.println("setStartTime :: " + stampTime.getStartTime());

            return stampTime;
        }


    }
}
