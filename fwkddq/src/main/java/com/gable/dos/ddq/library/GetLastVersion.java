package com.gable.dos.ddq.library;

import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Data
public class GetLastVersion {

    int lastversion = 0;

    public int getLast_version(MongoOperations mongoOperations, String collection_name, String jobName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("version_No").gte(0).andOperator(Criteria.where("date_string").is(date_string)
                .andOperator(Criteria.where("job_id").is(jobName))));
        Sort sort = new Sort(Sort.Direction.DESC, "version_No"); //max Date {StartTime}
        query.with(sort).limit(1);
        try {
            List<TableAudit> result = mongoOperations.find(query, TableAudit.class, collection_name);
            System.out.println("Result Class :: " + result.get(0).getVersion_No());
            return result.get(0).getVersion_No();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int getLast_version_perStep(MongoOperations mongoOperations, String collection_name, String jobName, String rule_name) {

        Query query = new Query();
        query.addCriteria(Criteria.where("version_No").gte(0).andOperator(Criteria.where("date_string").is(date_string)
                .andOperator(Criteria.where("job_id").is(jobName).andOperator(Criteria.where("rule_name").is(rule_name)))));
        Sort sort = new Sort(Sort.Direction.DESC, "version_No");
        query.with(sort).limit(1);
        try {
            List<TableAudit> result = mongoOperations.find(query, TableAudit.class, collection_name);
            System.out.println("Result Class :: " + result.get(0).getVersion_No());
            return result.get(0).getVersion_No();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
