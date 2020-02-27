package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import lombok.Data;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.sql.ResultSet;
import java.util.Date;

import static com.gable.dos.ddq.controller.itemReader.readerTimeStamp.end_time;
import static com.gable.dos.ddq.controller.itemReader.readerTimeStamp.start_time;

@Data
public class readerHistory_Repo implements ItemReader<Object> {
    private static String jobName = "";


    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";

    private String SQL;
    private ResultSet rs;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();

    private Date dateString = Config_Datetime.dateString;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        SQL = "SELECT \n" +
                "be.JOB_EXECUTION_ID as JOB_EXECUTION_ID , \n" +
                "bi.JOB_NAME as JOB_NAME ,\n" +
                "be.CREATE_TIME as CREATE_TIME,\n" +
                "be.START_TIME  as START_TIME, \n" +
                "be.END_TIME as END_TIME, \n" +
                "be.STATUS as STATUS ,\n" +
                "be.EXIT_CODE as EXIT_CODE ,\n" +
                "be.EXIT_MESSAGE as EXIT_MESSAGE,\n" +
                "be.LAST_UPDATED as LAST_UPDATED \n" +
                "FROM BATCH_JOB_EXECUTION as be INNER JOIN \n" +
                "BATCH_JOB_INSTANCE as bi ON be.JOB_INSTANCE_ID = bi.JOB_INSTANCE_ID \n" +
                "WHERE be.CREATE_TIME >= '" + start_time + "' and be.END_TIME <= '" + end_time + "' \n" +
                "ORDER BY be.JOB_EXECUTION_ID ";


        System.out.println("SQL == " + SQL);

//        try {
//            HistoryRepo historyRepo = new HistoryRepo();
//            connectionLibrary = new Connection_executes();
//            rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
//            ArrayList<HistoryLists> arrayList = new ArrayList<>();
//            System.out.println("rs" + rs);
//            while (rs.next()) {
//                HistoryLists historyLists = new HistoryLists();
//                System.out.println("JOB_EXECUTION_ID : " + rs.getInt("JOB_EXECUTION_ID"));
//                System.out.println("JOB_NAME : " + rs.getString("JOB_NAME"));
//                System.out.println("CREATE_TIME : " + rs.getDate("CREATE_TIME"));
//                System.out.println("START_TIME : " + rs.getDate("START_TIME"));
//                System.out.println("END_TIME : " + rs.getDate("END_TIME"));
//                System.out.println("STATUS : " + rs.getString("STATUS"));
//                System.out.println("EXIT_CODE : " + rs.getString("EXIT_CODE"));
//                System.out.println("EXIT_MESSAGE : " + rs.getString("EXIT_MESSAGE"));
//                System.out.println("LAST_UPDATED : " + rs.getDate("LAST_UPDATED"));
//                System.out.println("\n");
//
//
//                historyLists.setId(rs.getInt("JOB_EXECUTION_ID"));
//                historyLists.setJob_Name(rs.getString("JOB_NAME"));
//                historyLists.setCreate_Time(rs.getString("CREATE_TIME"));
//                historyLists.setStart_Time(rs.getString("START_TIME"));
//                historyLists.setEnd_Time(rs.getString("END_TIME"));
//                historyLists.setStatus(rs.getString("STATUS"));
//                historyLists.setExit_Code(rs.getString("EXIT_CODE"));
//                historyLists.setExit_Message(rs.getString("EXIT_MESSAGE"));
//                historyLists.setLast_Update(rs.getString("LAST_UPDATED"));
//                arrayList.add(historyLists);
//
////
//            }
//            historyRepo.setStart_Time(start_time);
//            historyRepo.setEnd_Time(end_time);
//            historyRepo.setHistoryLists(arrayList);
//            return historyRepo;
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }


        return null;
    }
}
