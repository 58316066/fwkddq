package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.model.HistoryLists;
import com.gable.dos.ddq.model.HistoryRepo;
import com.gable.dos.ddq.model.StampTime;
import com.gable.dos.ddq.controller.SendMail;
import org.springframework.batch.item.ItemProcessor;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.gable.dos.ddq.controller.itemReader.readerTimeStamp.end_time;
import static com.gable.dos.ddq.controller.itemReader.readerTimeStamp.start_time;

public class ProcessTimeStamp implements ItemProcessor<StampTime, Object> {

    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private String SQL;
    private ResultSet rs;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SendMail sendMail = new SendMail();
    @Override
    public Object process(StampTime stampTime) throws Exception {
//        System.out.println("(Last Process Timestamp) : " + stampTime);

        // config sql_Query (1)
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
                "WHERE be.CREATE_TIME >= '" + stampTime.getStartTime() + "' and be.END_TIME <= '" + stampTime.getEndTime() + "' \n" +
                "ORDER BY be.JOB_EXECUTION_ID ";

        System.out.println("SQL == " + SQL);

        try {
            HistoryRepo historyRepo = new HistoryRepo();
            connectionLibrary = new Connection_executes_result_set();
            rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
            ArrayList<HistoryLists> arrayList = new ArrayList<>();
            System.out.println("rs" + rs);
            while (rs.next()) {
                HistoryLists historyLists = new HistoryLists();
                System.out.println("JOB_EXECUTION_ID : " + rs.getInt("JOB_EXECUTION_ID"));
                System.out.println("JOB_NAME : " + rs.getString("JOB_NAME"));
                System.out.println("CREATE_TIME : " + rs.getDate("CREATE_TIME"));
                System.out.println("START_TIME : " + rs.getDate("START_TIME"));
                System.out.println("END_TIME : " + rs.getDate("END_TIME"));
                System.out.println("STATUS : " + rs.getString("STATUS"));
                System.out.println("EXIT_CODE : " + rs.getString("EXIT_CODE"));
                System.out.println("EXIT_MESSAGE : " + rs.getString("EXIT_MESSAGE"));
                System.out.println("LAST_UPDATED : " + rs.getDate("LAST_UPDATED"));
                System.out.println("\n");


                historyLists.setId(rs.getInt("JOB_EXECUTION_ID"));
                historyLists.setJob_Name(rs.getString("JOB_NAME"));
                historyLists.setCreate_Time(rs.getString("CREATE_TIME"));
                historyLists.setStart_Time(rs.getString("START_TIME"));
                historyLists.setEnd_Time(rs.getString("END_TIME"));
                historyLists.setStatus(rs.getString("STATUS"));
                historyLists.setExit_Code(rs.getString("EXIT_CODE"));
                historyLists.setExit_Message(rs.getString("EXIT_MESSAGE"));
                historyLists.setLast_Update(rs.getString("LAST_UPDATED"));
                arrayList.add(historyLists);

            }
            historyRepo.setStart_Time(start_time);
            historyRepo.setEnd_Time(end_time);
            historyRepo.setHistoryLists(arrayList);

            sendMail.SendMail(historyRepo);

            return historyRepo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
