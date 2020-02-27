package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.DimensionRule2;
import com.gable.dos.ddq.model.RelationConfig;
import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.DimensionJobName;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule2.check_dimension_rule2;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule2.list_dimension_rule2;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Slf4j
@Data
public class processDimension_rule2 implements ItemProcessor<TableAudit, Object>, StepExecutionListener {
    private MongoOperations mongoOperations;
    private String collection_name;
    private String collection_output;
    public static List<RelationConfig> relationConfigs;
    private Connection_executes_result_set executesResultSet;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private String SQL;
    private ResultSet rs;
    private int lastVersion = 0;
    private String StepName = "";

    private List dimensionRule2List;

    @Override
    public Object process(TableAudit item) throws Exception {
        dimensionRule2List = new ArrayList<>();
        while (!check_dimension_rule2) {
            if (list_dimension_rule2.size() != 0) {
                log.info("rule2 Table_name = " + item.getTable_name());

                SearchTables(list_dimension_rule2.get(0));

                list_dimension_rule2.remove(0);
                check_dimension_rule2 = false;


            } else {
                check_dimension_rule2 = true;
            }
        }


        return dimensionRule2List;
    }

    private void SearchTables(TableAudit tableAudit) {

        Query query = new Query();
        query.addCriteria(Criteria.where("main_table").is(tableAudit.getTable_name()));
        try {
            relationConfigs = mongoOperations.find(query, RelationConfig.class, collection_name);
            System.out.println("Table RelationConfigs :: " + relationConfigs);
            System.out.println("Table RelationConfigs.Size :: " + relationConfigs.size());

            if (relationConfigs.size() != 0) {

                for (RelationConfig relation : relationConfigs) {
                    log.info("RelationConfig :::> " + relation);
                    checkFK(relation);
                }

            }
//            list_dimension_rule2.remove(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void checkFK(RelationConfig relation) throws SQLException {

        SQL = "select\n" +
                "case \n" +
                "\t\t when count(*) >= 1 then 'True'\n" +
                "\t\t when count(*) is null then 'False' \n" +
                "\t\t else 'False' \n" +
                "\tEnd as FK_IsNull\n" +
                "\tFROM \n" +
                "(\n" +
                "\tselect \n" +
                "\ttbl_main." + relation.getMain_field() + " as key_main\n" +
                "\tfrom " + relation.getMain_schema() + "." + relation.getMain_table() + " as tbl_main\n" +
                "\twhere not exists(\n" +
                "\t\t\tselect 1 from " + relation.getRef_schema() + "." + relation.getRef_table() + " where " + relation.getRef_field() + " = tbl_main." + relation.getMain_field() + "\n" +
                "\t)\n" +
                ") b\n";

        log.info("SQL checkFK = " + SQL);

        executesResultSet = new Connection_executes_result_set();

        rs = executesResultSet.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);


        boolean status = false;
        while (rs.next()) {
            String s = rs.getString("FK_IsNull");
            status = Boolean.parseBoolean(s);
            log.info("RS rule 2 [" + relation.getMain_table() + "." + relation.getMain_field() + "] & [" + relation.getRef_table() + "." + relation.getRef_field() + "] = " + status);
        }

        DimensionRule2 dimensionRule2 = new DimensionRule2();
        dimensionRule2.setMain_schema(relation.getMain_schema());
        dimensionRule2.setMain_table(relation.getMain_table());
        dimensionRule2.setMain_field(relation.getMain_field());
        dimensionRule2.setRef_schema(relation.getRef_schema());
        dimensionRule2.setRef_table(relation.getRef_table());
        dimensionRule2.setRef_field(relation.getRef_field());
        dimensionRule2.setTable_name(relation.getMain_table());
        dimensionRule2.setKey_PK(relation.getMain_field());
        dimensionRule2.setRule_name(StepName);
        dimensionRule2.setSql(SQL);
        dimensionRule2.setStatus(status);
        dimensionRule2.setJob_id(DimensionJobName);
//        dimensionRule2.setMonth_id(new Date().getMonth() + 1);
        dimensionRule2.setProcessDate(new Date());
        dimensionRule2.setDate_string(date_string);
        dimensionRule2.setVersion_No(lastVersion + 1);

        dimensionRule2List.add(dimensionRule2);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepName = stepExecution.getStepName();

        GetLastVersion getLastVersion = new GetLastVersion();
        lastVersion = getLastVersion.getLast_version_perStep(mongoOperations, collection_output, stepExecution.getJobExecution().getJobInstance().getJobName(), StepName);
        System.out.println("lastVersion : " + lastVersion);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
