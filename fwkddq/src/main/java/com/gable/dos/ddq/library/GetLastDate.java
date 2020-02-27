package com.gable.dos.ddq.library;

import com.gable.dos.ddq.model.TableAuditCountRecord;
import com.mongodb.client.MongoCollection;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Data
public class GetLastDate extends StepExecutionListenerSupport implements ItemReader<Object> {
    private MongoTemplate template;
    private String collection_name;
    private List<TableAuditCountRecord> item = new ArrayList<>();
    private TableAuditCountRecord tableAuditCountRecord = new TableAuditCountRecord();
    private Locale locale = new Locale("en", "UK");
    private LocalDateTime localDateTime;
    Date date1 = new Date();
    Date generateDate = new Date();
    public static boolean checkRestart = false;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date currentDate = config_datetime.dateConfig();


    //    Date currentDate = new Date();
    public static int countDay = 0;
    public static List<Date> dateList = new ArrayList<>();
    private MongoCollection<Document> collection = null;
    boolean check = true;


    @Override
    public FlowExecutionStatus read() throws Exception, UnexpectedInputException, org.springframework.batch.item.ParseException, NonTransientResourceException {

        getLastDate();
//        return new ExitStatus("FAILED");
//        return new FlowExecutionStatus("FAILED");
        return null;
    }


    public void getLastDate() {

        System.out.println("currentDate : " + currentDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        Date lastDateDB = null;
        Date lastDate = null;
//
//        Query.query(Criteria.where("processDate").is(TableAuditCountRecord.class));
//        TableAuditCountRecord auditCountRecord = template.findOne(Query.query(Criteria.where("_class").is(TableAuditCountRecord.class)),
//                TableAuditCountRecord.class, collection_name);

//        System.out.println(" Result " + auditCountRecord);


        item = template.findAll(TableAuditCountRecord.class, collection_name);

        log.info("item = " + item);

        try {
            if (!item.isEmpty()) {
                lastDateDB = df.parse(df.format(item.get(0).getProcessDate()));

                LocalDateTime ldt = LocalDateTime.ofInstant(lastDateDB.toInstant(), ZoneId.systemDefault());

                lastDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

                System.out.println("lastDate" + lastDate);

                localDateTime = lastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


                countDay = daysBetween(lastDate, currentDate);
                System.out.println("days : " + countDay);

                for (int i = 1; i < countDay; i++) {
                    localDateTime = localDateTime.plusDays(1);

                    generateDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                    dateList.add(generateDate);
//            assignment(item);
                    System.out.println("ans " + i + " : " + generateDate + "assignment Date Success");

                }
            } else {
                log.info("itemList get Data: Error");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


//        if (checkRestart) {
//            dateList.remove(0);
//        }
//
//        System.out.println("list"+dateList);
//        System.out.println("list.size"+dateList.size());


    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();

        if (dateList.isEmpty()) {
//            log.info("ExitStatus = FAILED");
            return new ExitStatus("FAILED");
        } else {
            log.info("ExitStatus = COMPLETED");
            return new ExitStatus("COMPLETED");
        }

    }


//    private void assignment(List<TableAuditCountRecord> item) {
//        TableAuditCountRecord countRecord = new TableAuditCountRecord();
//        countRecord.setMonth_id(item.get(0).getMonth_id());
//        countRecord.setSchema(item.get(0).getSchema());
//        countRecord.setTable_name(item.get(0).getTable_name());
//        countRecord.setTable_Type(item.get(0).getTable_Type());
//        countRecord.setCountRecord(0);
//        countRecord.setProcessDate(generateDate);
//        countRecord.setFieldNo(0);
//        countRecord.setJob_id("AuditStructure1");
//
//        Document document = new Document();
//        document.put("month_id", countRecord.getMonth_id());
//        document.put("schema", countRecord.getSchema());
//        document.put("table_name", countRecord.getTable_name());
//        document.put("table_Type", countRecord.getTable_Type());
//        document.put("countRecord", countRecord.getCountRecord());
//        document.put("fieldNo", countRecord.getFieldNo());
//        document.put("processDate", countRecord.getProcessDate());
//        document.put("job_id", countRecord.getJob_id());
//
//        collection = template.getCollection(collection_name);
//        collection.insertOne(document);
//
//    }


}
