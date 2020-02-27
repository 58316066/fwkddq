package com.gable.dos.ddq.controller;
// Java program for write JSON to a file
// Java program for write JSON to a file

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Slf4j
public class JSONWriteExample
{
    public static void main(String[] args) throws FileNotFoundException
    {
        // creating JSONObject
        String jsons = "{\"firstName\":\"Benz\",\"lastName\":\"Smith\",\"address\":{\"streetAddress\":\"21 2nd Street\",\"city\":\"New York\",\"state\":\"NY\",\"postalCode\":10021},\"age\":25,\"phoneNumbers\":[{\"type\":\"home\",\"number\":\"212 555-1234\"},{\"type\":\"fax\",\"number\":\"212 555-1234\"}]}";


        JSONObject jo = new JSONObject();
        try {
            jo = (JSONObject) new JSONParser().parse(jsons);

        }
        catch (Exception e){
            log.error(e.getMessage());
        }

//        JSONObject jo = new JSONObject();
//        jo.put("firstName", "Nantawat");
//        jo.put("lastName", "Mansak");
//        jo.put("age", 25);

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);
        m.put("streetAddress", "21 2nd Street");
        m.put("city", "New York");
        m.put("state", "NY");
        m.put("postalCode", 10021);

        // putting address to JSONObject
        jo.put("address", m);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(2);
        m.put("type", "home");
        m.put("number", "0979436573");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(2);
        m.put("type", "fax");
        m.put("number", "212 555-1234");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(2);
        m.put("type", "cellphone");
        m.put("number", "123445550");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("phoneNumbers", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("JSONExample.json");
        JSONArray all_json = new JSONArray();
        all_json.add(jo);
        jo.put("firstName", "Benz");
        all_json.add(jo);
        pw.write(all_json.toJSONString());

//        log.info("jo.toJSONString = "+ jo.get("firstName"));
        log.info("JSONString : "+ jo.toJSONString());

        pw.flush();
        pw.close();
    }
}
