package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.model.HistoryRepo;
import org.springframework.batch.item.ItemProcessor;

import java.sql.ResultSet;

public class processHistory_Repo implements ItemProcessor<HistoryRepo, Object> {
    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private String SQL;
    private ResultSet rs;

    @Override
    public Object process(HistoryRepo historyRepo) throws Exception {
//        HistoryRepo historyRepo = new HistoryRepo();
//        System.out.println("(Last Process Timestamp) : " +  stampTime);
//
//        // config sql_Query (1)
//        SQL = "SELECT \n" +
//                "be.JOB_EXECUTION_ID as JOB_EXECUTION_ID , \n" +
//                "bi.JOB_NAME as JOB_NAME ,\n" +
//                "be.CREATE_TIME as CREATE_TIME,\n" +
//                "be.START_TIME  as START_TIME, \n" +
//                "be.END_TIME as END_TIME, \n" +
//                "be.STATUS as STATUS ,\n" +
//                "be.EXIT_CODE as EXIT_CODE ,\n" +
//                "be.EXIT_MESSAGE as EXIT_MESSAGE,\n" +
//                "be.LAST_UPDATED as LAST_UPDATED \n" +
//                "FROM BATCH_JOB_EXECUTION as be INNER JOIN \n" +
//                "BATCH_JOB_INSTANCE as bi ON be.JOB_INSTANCE_ID = bi.JOB_INSTANCE_ID \n" +
//                "WHERE be.CREATE_TIME >= '" + stampTime.getStartTime() + "' and be.END_TIME <= '" + stampTime.getEndTime() + "' \n" +
//                "ORDER BY be.JOB_EXECUTION_ID ";
//
//        System.out.println("SQL == " + SQL);
//
//        try {
//
//            connectionLibrary = new Connection_executes();
//            rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
//
//            System.out.println("rs" + rs);
//            while (rs.next()) {
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
//                historyRepo.setId(rs.getInt("JOB_EXECUTION_ID"));
//                historyRepo.setJob_Name(rs.getString("JOB_NAME"));
//                historyRepo.setCreate_Time(rs.getString("CREATE_TIME"));
//                historyRepo.setStart_Time(rs.getString("START_TIME"));
//                historyRepo.setEnd_Time(rs.getString("END_TIME"));
//                historyRepo.setStatus(rs.getString("STATUS"));
//                historyRepo.setExit_Code(rs.getString("EXIT_CODE"));
//                historyRepo.setExit_Message(rs.getString("EXIT_MESSAGE"));
//                historyRepo.setLast_Update(rs.getString("LAST_UPDATED"));
//                return historyRepo;
//            }
//
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        System.out.println(historyRepo);

        return historyRepo;
    }
}
