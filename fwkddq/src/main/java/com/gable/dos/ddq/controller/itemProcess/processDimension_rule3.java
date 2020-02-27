package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.Dimension;
import com.gable.dos.ddq.model.DimensionRule2;
import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;
import org.springframework.data.mongodb.core.MongoOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.DimensionJobName;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule1.check_dimension_rule1;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule1.list_dimension_rule1;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule3.list_dimension_rule3;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule3.check_dimension_rule3;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Data
@Slf4j
public class processDimension_rule3 implements ItemProcessor<TableAudit, Object>, StepExecutionListener {
    private Connection_executes_result_set executesResultSet;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private MongoOperations mongoOperations;
    private String collection_name;
    private String collection_output;
    private int lastVersion = 0;
    private String SQL;
    private ResultSet resultSet;

    private String StepName = "";

    private List<String> PK_Column_List;
    private List dimensionRule3List;

    @Override
    public Object process(TableAudit item) throws Exception {
        dimensionRule3List = new ArrayList<>();
//        while (!check_dimension_rule3) {
            if (list_dimension_rule3.size() != 0) {
                log.info("rule3 = " + item.getTable_name());
                String table_name = item.getTable_name();
                String schema_name = item.getSchema();
                String column_pk = item.getPrimary_key();

                parsePK(column_pk, table_name, schema_name);

                check_dimension_rule3 = false;
                list_dimension_rule3.remove(0);
            } else {

                check_dimension_rule3 = true;
            }
//        }

        return dimensionRule3List;
    }

    private void parsePK(String column_pk, String table_name, String schema_name) throws SQLException {

        String[] tokens = column_pk.split(",");

        for (String t : tokens) {
            log.info("t = " + t);
            create_SQL_IsNull_PK(t, table_name, schema_name);
        }
    }


    private void create_SQL_IsNull_PK(String fieldPK, String table_name, String schema_name) throws SQLException {

//        String fieldPK = parsePK(column_pk);
        String SQL = "select \n" +
                "\tcase \n" +
                "\t\twhen sum(difference) != sum(countAll) then 'True' else 'False' end as result \n" +
                "\t\tfrom (\n" +
                "\t\t\t\t\tselect \n" +
                "\t\t\t\t\t\tcount(*) as countAll,\n" +
                "\t\t\t\t\t\tcount(*) - \n" +
                "\t\t\t\t\t\t\t(select count(*) as " + fieldPK + " from " + schema_name + "." + table_name + " where " + fieldPK + " = '' or " + fieldPK + " is null) as difference\n" +
                "\t\t\t\t\tfrom " + schema_name + "." + table_name + "\n" +
                ")t";

        log.info("SQL create_SQL_IsNull_PK = " + SQL);

        executesResultSet = new Connection_executes_result_set();

        resultSet = executesResultSet.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);


        boolean status = false;
        while (resultSet.next()) {
            String result = resultSet.getString("result");
            log.info("FK_IsNull tbl [" + table_name + "] & field [" + fieldPK + "] = " + result);

            status = Boolean.parseBoolean(result);
        }

        Dimension dimension = new Dimension();

        dimension.setRule_name(StepName);
        dimension.setProcessDate(new Date());
        dimension.setTable_name(table_name);
        dimension.setDate_string(date_string);
        dimension.setSql(SQL);
        dimension.setKey_PK(fieldPK);
        dimension.setStatus(status);
        dimension.setJob_id(DimensionJobName);
//        dimension.setMonth_id(new Date().getMonth() + 1);
        dimension.setVersion_No(lastVersion + 1);
        dimensionRule3List.add(dimension);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepName = stepExecution.getStepName();

        GetLastVersion getLastVersion = new GetLastVersion();
        lastVersion = getLastVersion.getLast_version_perStep(mongoOperations, collection_output, stepExecution.getJobExecution().getJobInstance().getJobName(),StepName);
        System.out.println("lastVersion : " + lastVersion);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
