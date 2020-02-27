package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.sql.ResultSet;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

public class read_actual_length implements ItemReader<String> {

    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private String SQL;
    private ResultSet rs;

    public static boolean returns_step5 = false;


    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!returns_step5) {
            returns_step5 = true;
            Connection_executes_result_set connection_executes = new Connection_executes_result_set();

            SQL = "select\n" +
                    " concat('max(length(',a.COLUMN_NAME,')) as Max_',a.COLUMN_NAME, ',' ) as P1 \n" +
                    "from information_schema.`COLUMNS` a \n" +
                    "where \n" +
                    " a.`TABLE_SCHEMA` = 'batch' and\n" +
                    " a.`TABLE_NAME` = '" + System.getProperty("tmp_tbl_name") + "'";

            logger.info("Create SQL concat(max_length) for [field/column]");
            logger.info(SQL);
            System.out.println("SQL MaxLength = " + SQL);

            rs = connection_executes.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

            logger.info("Create SQL get max_length for [field/column]");
            String queryMaxLength = "select \n";

            while (rs.next()) {
                System.out.println("rs = " + rs.getString("P1"));
                queryMaxLength = queryMaxLength.concat(rs.getString("P1") + "\n");
            }
            int cutIndex = queryMaxLength.lastIndexOf(",", queryMaxLength.length());
            String sub_queryMaxLength = queryMaxLength.substring(0, cutIndex);
            sub_queryMaxLength = sub_queryMaxLength.concat("\nfrom " + System.getProperty("tmp_tbl_name") + " ;");

            System.out.println("queryMaxLength = " + sub_queryMaxLength);
            logger.info(sub_queryMaxLength);

            return sub_queryMaxLength;
        }
        return null;
    }
}
