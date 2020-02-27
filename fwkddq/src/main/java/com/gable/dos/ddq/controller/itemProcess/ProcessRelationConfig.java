package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_execute_bulk_file;
import com.gable.dos.ddq.model.RelationConfig;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.readRelationConfig.check_rule2;
import static com.gable.dos.ddq.controller.itemReader.readRelationConfig.stringList;


@Slf4j
public class ProcessRelationConfig implements ItemProcessor<List<String>, RelationConfig> {

    @Override
    public RelationConfig process(List<String> item) throws Exception {
        if (stringList.size() != 0) {
            RelationConfig relationConfig = new RelationConfig();

            relationConfig.setMain_schema(item.get(0)); //Main_schema
            relationConfig.setMain_table(item.get(1)); //Main_table
            relationConfig.setMain_field(item.get(2)); //Main_field
            relationConfig.setRef_schema(item.get(3)); //Ref_schema
            relationConfig.setRef_table(item.get(4)); //Ref_table
            relationConfig.setRef_field(item.get(5)); //Ref_field
            stringList.remove(0);
            return relationConfig;
        }
        check_rule2 = true;
        return null;
    }

}
