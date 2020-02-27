package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Data
public class ReadTableDimension implements ItemReader, StepExecutionListener, JobExecutionListener {
    private MongoOperations mongoOperations;
    private String collection_name;
    public static boolean check_table_dimension = false;
    public static List<TableAudit> table_dimension;
    public static String DimensionJobName = "";
    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!check_table_dimension) {
            Query query = new Query();
            query.addCriteria(Criteria.where("table_type").is("DIMENSION"));
            Sort sort = new Sort(Sort.Direction.DESC, "table_name"); //max Date {StartTime}
            query.with(sort);
            try {
                table_dimension = mongoOperations.find(query, TableAudit.class, collection_name);
                System.out.println("Table Dimension :: " + table_dimension);
                check_table_dimension = true;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//            if (table_dimension.size() != 0) {
//                return table_dimension.get(0);
//            }
//            return null;

        }
        return null;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("table_type").is("dimension"));
//        Sort sort = new Sort(Sort.Direction.DESC, "table_name"); //max Date {StartTime}
//        query.with(sort);
//        try {
//            table_dimension = mongoOperations.find(query, TableAudit.class, collection_name);
//            System.out.println("Table Dimension :: " + table_dimension.get(0));
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        DimensionJobName = jobExecution.getJobInstance().getJobName();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
