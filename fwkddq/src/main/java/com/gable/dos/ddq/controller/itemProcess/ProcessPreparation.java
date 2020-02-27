package com.gable.dos.ddq.controller.itemProcess;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

@Slf4j
public class ProcessPreparation implements ItemProcessor<HashMap<String, ArrayList<String>>, HashMap<String, ArrayList<String>>> {
    public static ArrayList<String> list_header;
    private ArrayList<String> rs = new ArrayList<>();
    private ArrayList<String> header;
    private HashMap<String, ArrayList<String>> headers_refactor;

    @Override
    public HashMap<String, ArrayList<String>> process(HashMap<String, ArrayList<String>> headers) throws Exception {
        log.info("ItemProcessor");
        log.info("header.size() : " + headers.size());
        log.info("header.keySet();: " + headers.keySet());
        System.out.println("\n\n");
        headers_refactor = new HashMap<>();
        logger.info("Check column unique and replaceAll");
        logger.info("Change column toUpperCase");
        for (String key : headers.keySet()) {
            list_header = new ArrayList<>();

            header = new ArrayList<>();

            header = headers.get(key);
            Set<String> st = new HashSet<>();

            for (int i = 0; i < header.size(); i++) {
                // Check Regex
                String hUpperCase = header.get(i).toUpperCase();
                Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(hUpperCase);
                boolean b = m.find();
                if (b) {
                    System.out.println("CASE_INSENSITIVE = " + hUpperCase);
                    String replace = hUpperCase.replaceAll("[^A-Za-z0-9]", "_");
                    header.set(i, replace);
                    System.out.println("REPLACE CASE_INSENSITIVE TO = " + header.get(i));
                } else {
                    header.set(i, hUpperCase);
                    System.out.println("REPLACE CASE_INSENSITIVE TO = " + header.get(i));

                }
                // check whether the element is
                // repeated or not
                if (!st.contains(hUpperCase)) {
                    st.add(hUpperCase);
                } else {
                    // find the next greatest element
                    for (int j = header.indexOf(i) + 1; j < Integer.MAX_VALUE; j++) {
                        if (!st.contains("_" + j)) {
                            header.set(i, hUpperCase + "_" + j);
                            st.add("_" + j);
                            break;
                        }
                    }
                }
            }

            list_header = header;
            log.info("List Replace Success : " + header);
            log.info("list_header Replace Success : " + list_header);
            headers_refactor.put(key, list_header);
        }
        log.info("headers_refactor: " + headers_refactor);
        return headers_refactor;
    }
}