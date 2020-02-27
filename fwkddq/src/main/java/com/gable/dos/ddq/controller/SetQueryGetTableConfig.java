package com.gable.dos.ddq.controller;

import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.List;

import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Data
public class SetQueryGetTableConfig implements ItemReader, StepExecutionListener {
    private MongoOperations mongoOperations;
    private String collection_name;
    public static boolean check_setQuery = false;
    public static List<TableAudit> result_list;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!check_setQuery) {
            if (result_list.size() != 0) {
                return result_list.get(0);
            }
            return null;

        }
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        Query query = new Query();
        try {
            result_list = mongoOperations.find(query, TableAudit.class, collection_name);
            System.out.println("Result Class :: " + result_list.get(0));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

// FactoryBean<Query>
// <property name="template" ref="mongoTemplate"/>
//        <property name="query">
//            <bean class="com.gable.dos.ddq.controller.SetQueryGetTableConfig"/>
//        </property>
//
//        <!--        <property name="query" value="{ }"/>-->
//        <property name="sort">
//            <map>
//                <entry key="table_name"
//                       value="#{T(org.springframework.data.domain.Sort.Direction).ASC}"/>
//            </map>
//        </property>
//        <property name="collection" value="FWKDDQ_Config"/>
//        <property name="targetType" value="com.gable.dos.ddq.model.TableAudit"/>


//    @Override
//    public Query getObject() throws Exception, IOException, ExceptionInInitializerError {
//        Query query = new Query();
//
//        return query;
//    }
//
//    @Override
//    public Class<Query> getObjectType() {
//        return Query.class;
//    }

}

