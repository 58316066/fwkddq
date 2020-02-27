package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.Dimension;
import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.prop;
import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.DimensionJobName;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule1.check_dimension_rule1;
import static com.gable.dos.ddq.controller.itemReader.readerDimension_rule1.list_dimension_rule1;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Slf4j
@Data
public class processDimension_rule1 implements ItemProcessor<TableAudit, Dimension>, StepExecutionListener {
    private Connection_executes_result_set executesResultSet, executesResultSet2;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private MongoOperations mongoOperations;
    private String collection_name;
    private int lastVersion = 0;
    private String SQL;
    private ResultSet rs;

    private String StepName = "";

    private List<String> PK_Column_List;

    @Override
    public Dimension process(TableAudit item) throws Exception {

        if (list_dimension_rule1.size() != 0) {
            log.info("rule1 = " + item.getTable_name());
            String table_name = item.getTable_name();
            String schema_name = item.getSchema();
            String column_pk = item.getPrimary_key();
            list_dimension_rule1.remove(0);

            check_dimension_rule1 = false;
            return create_SQL_IsDuplicate_PK(column_pk, table_name, schema_name);
        }
        check_dimension_rule1 = true;
        return null;
    }

//    private void getPK(String table_name, String schema_name) throws IndexOutOfBoundsException, SQLException {
//        PK_Column_List = new ArrayList<>();
//
//        executesResultSet = new Connection_executes_result_set();
//
//        SQL = "SELECT COLUMN_NAME  as 'COLUMN_PK'\n" +
//                "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n" +
//                "WHERE OBJECTPROPERTY(OBJECT_ID(CONSTRAINT_SCHEMA + '.' + QUOTENAME(CONSTRAINT_NAME)), 'IsPrimaryKey') = 1\n" +
//                "AND TABLE_NAME = '" + table_name + "' AND TABLE_SCHEMA = '" + schema_name + "'";
//
//        rs = executesResultSet.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
//
//        while (rs.next()) {
//            log.info("PK in table [" + table_name + "] == > '" + rs.getString("COLUMN_PK") + "'");
//            PK_Column_List.add(rs.getString("COLUMN_PK"));
//        }
//
//        log.info("PK_Column_List = " + PK_Column_List);
//
//        create_SQL_IsDuplicate_PK(PK_Column_List, table_name, schema_name);
//    }

    private Dimension create_SQL_IsDuplicate_PK(String pk_column_list, String table_name, String schema_name) throws SQLException {
        String sql = "select\n" +
                "\tcase \n" +
                "\t     when sum(Flag)  = 0 then 'False'\n" +
                "\t\t\t when sum(Flag) is null then 'False' \n" +
                "\t\t\t else 'True' \n" +
                "\tEnd as IS_Duplicate\n" +
                "from\n" +
                "(\n" +
                "\tselect \n";
        sql = sql.concat(pk_column_list + ",");
        sql = sql.concat("\ncase when count(*) > 1 then 1 else 0 end Flag\n" +
                "\tfrom " + schema_name + "." + table_name + "\ngroup by ");
        sql = sql.concat(pk_column_list);
        sql = sql.concat("\n HAVING count(*) > 1\n" +
                ") t");

        String sqlPK_dup = "select \n";
        sqlPK_dup = sqlPK_dup.concat(pk_column_list + ",");
        sqlPK_dup = sqlPK_dup.concat("\ncase when count(*) > 1 then 1 else 0 end sumDuplicate\n" +
                "\tfrom " + schema_name + "." + table_name + "\ngroup by ");
        sqlPK_dup = sqlPK_dup.concat(pk_column_list);
        sqlPK_dup = sqlPK_dup.concat("\n HAVING count(*) > 1\n");


        System.out.println("SQL_IsDuplicate = " + sql);
        System.out.println("SQL_ShowPK_Duplicate = " + sqlPK_dup);

        return execute_IsDuplicate(sql, sqlPK_dup, table_name);

    }

    private Dimension execute_IsDuplicate(String sql_isDuplicate, String sqlPK_dup, String table_name) throws SQLException {
        ResultSet resultSet, resultSet2 = null;
        executesResultSet = new Connection_executes_result_set();
        executesResultSet2 = new Connection_executes_result_set();


        resultSet = executesResultSet.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, sql_isDuplicate);
        resultSet2 = executesResultSet2.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, sqlPK_dup);

        Dimension dimension = new Dimension();

        String srtDetail_duplicate = "";
        int sumDuplicate = 0;
        int column = resultSet2.getMetaData().getColumnCount() - 1;
        while (resultSet2.next()) {
            for (int i = 1; i <= column; i++) {
                System.out.println("Detail PK Duplicate = " + resultSet2.getString(i));
                srtDetail_duplicate = srtDetail_duplicate.concat(resultSet2.getString(i) + ", ");
            }
            sumDuplicate = resultSet2.getInt("sumDuplicate");
            System.out.println("Sum PK Duplicate = " + sumDuplicate);
        }
        System.out.println("srtDetail_duplicate = "+srtDetail_duplicate);
        dimension.setDetail_duplicate(srtDetail_duplicate);
        dimension.setSum_Duplicate(sumDuplicate);

        boolean status = false;
        while (resultSet.next()) {
            String result = resultSet.getString("IS_Duplicate");
            log.info("IS_Duplicate tbl [" + table_name + "] = " + resultSet.getString("IS_Duplicate"));

            status = Boolean.parseBoolean(result);
        }


        dimension.setRule_name(StepName);
        dimension.setProcessDate(new Date());
        dimension.setTable_name(table_name);
        dimension.setDate_string(date_string);
        dimension.setSql_detail_duplicate(sqlPK_dup);
        dimension.setSql(sql_isDuplicate);
        dimension.setStatus(status);
        dimension.setJob_id(DimensionJobName);
//        dimension.setMonth_id(new Date().getMonth() + 1);
        dimension.setVersion_No(lastVersion + 1);

        log.info(dimension.getRule_name());
        return dimension;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        StepName = stepExecution.getStepName();

        GetLastVersion getLastVersion = new GetLastVersion();
        lastVersion = getLastVersion.getLast_version(mongoOperations, collection_name, stepExecution.getJobExecution().getJobInstance().getJobName());
        System.out.println("lastVersion : " + lastVersion);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
