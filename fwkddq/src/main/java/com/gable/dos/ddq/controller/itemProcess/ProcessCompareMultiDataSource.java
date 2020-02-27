package com.gable.dos.ddq.controller.itemProcess;

//import static com.gable.dos.compare.FileDiff.checkStatusCompare;

//
//@Slf4j
//public class ProcessCompareMultiDataSource implements ItemProcessor<JSONArray, Object> {

//    /*new class Config_Datetime.java*/
//    private Config_Datetime config_datetime = new Config_Datetime();
//
//    /*get Date Config*/
//    private Date date = config_datetime.dateConfig();
//
//    private Date dateString = Config_Datetime.dateString;
//
//
//    @Override
//    public Object process(JSONArray item) throws Exception {
//
//        JSONObject jsonObject1 = (JSONObject) item.get(0);
//        JSONObject jsonObject2 = (JSONObject) item.get(1);
//
//        log.info("******* JsonArray *******");
//        log.info("JSONObject {1} : " + jsonObject1);
//        log.info("JSONObject {2} : " + jsonObject2);
//
//
//        // new class FileDiff.java
//        FileDiff fileDiff = new FileDiff();
//        fileDiff.html(jsonObject1,jsonObject2);
//
//        log.info("******* checkStatusCompare *******"+ checkStatusCompare);
//        String dataHTML = FileCommandsVisitor.output;
//
//        ResultCompare resultCompare = new ResultCompare();
//
//        resultCompare.setResultCompare(dataHTML);
//        resultCompare.setProcessDate(date);
//        resultCompare.setDate(dateString);
//        resultCompare.setMonth_id(date.getMonth()+1);
//        resultCompare.setResult1(jsonObject1.toJSONString());
//        resultCompare.setResult2(jsonObject2.toJSONString());
//        resultCompare.setSchema("batch.dataDemo, public.a_table");
//        resultCompare.setStatus(String.valueOf(checkStatusCompare));
//        resultCompare.setJob_id("MultiDataSource1");
//
//        return resultCompare;
//    }
//}
