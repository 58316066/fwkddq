package com.gable.dos.ddq;

//import com.gable.dos.compare.FileDiff;
import com.gable.dos.compare.FileDiff;
import com.gable.dos.compare.process.FileCommandsVisitor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.gable.dos.errcode.ErrorCode;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
//@EnableScheduling
//@Configuration
//@EnableBatchProcessing
@SpringBootApplication
public class FwkddQApplication {

    public static void main(String[] args) {
//		ApplicationContext context;
//		JobLauncher jobLauncher;
//		Job job;
//		JobExecution execution;

//        FileDiff fileDiff = new FileDiff();
//
//        JSONObject jsonObject1 = new JSONObject();
////        HashMap<String,Object> jsonObject1 = new HashMap<>();
//
//        jsonObject1.put("name","nantawatbbb");
//        jsonObject1.put("age","23");
//        jsonObject1.put("career","developer");
//
//        JSONObject jsonObject2 = new JSONObject();
//
//        jsonObject2.put("name","nantawataaaaa");
//        jsonObject2.put("age","27");
//        jsonObject2.put("career","developer");
//
//
////        log.info("jsonObject1 :"+jsonObject1);
////        log.info("jsonObject2 :"+jsonObject2);
//
//        try {
//            fileDiff.html(jsonObject1,jsonObject2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        log.info("start...");
        log.info("Argument of command line args: " + args.length);
        log.info("Command line Arguments list : ");

        for (int i = 0; i < args.length; i++) {
            log.info("    arg[" + i + "] = " + args[i]);
        }


        //Terminate script when found not option less then 2
        if (args.length < 2) {
            ErrorCode.toValidate("E1099", "!!! Argument missing !!! ");
        }

//        System.out.println(java.util.TimeZone.getDefault().getID());
        SpringApplication.run(FwkddQApplication.class, args);

//        FileDiff fileDiff = new FileDiff();
//        try {
//            fileDiff.html();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        // context = new ClassPathXmlApplicationContext("spring-batch-context.xml");

        // jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        // //call job examResultJob
        // job = (Job) context.getBean("examResultJob");
        // try {

        // 	execution = jobLauncher.run(job, new JobParameters());
        // 	log.info("Job Exit Status : "+ execution.getStatus());

        // } catch (JobExecutionException e) {
        // 	log.info("Job ExamResult failed");
        // 	e.printStackTrace();
        // }

        // //call job examResultJob_B
        // job = (Job) context.getBean("examResultJob_B");
        // try {

        // 	execution = jobLauncher.run(job, new JobParameters());
        // 	log.info("Job Exit Status : "+ execution.getStatus());

        // } catch (JobExecutionException e) {
        // 	log.info("Job ExamResult failed");
        // 	e.printStackTrace();
        // }

//		test(20);
        log.info("stop...");

    }


//	//method for test
//	private static void test(int iLoop) {
//		
//		for(int i=1;i<=iLoop;i++)
//		{
//			log.info("print value {}",i);
//		}
//		
//	}

}
