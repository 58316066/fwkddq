package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.controller.multiDatasource.ItemProcess;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


@Slf4j
public class ItemReaderMultiDataSource implements ItemReader {
    private Boolean check = true;


    @Override
    public JSONArray read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String resultSet1 = ItemProcess.ResourceSet_1;
        String resultSet2 = ItemProcess.ResourceSet_2;

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("resultSet1", "abc");
        jsonObject1.put("resultSet2", "bbb");
        jsonObject1.put("resultSet3", "ccc");
        jsonObject1.put("resultSet4", "ddd");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("resultSet1", "abc");
        jsonObject2.put("resultSet3", "ccc");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);

        log.info("JSONArray : " + jsonArray);

        if (check) {
            check = false;
            return jsonArray;
        }
        return null;
    }
}
