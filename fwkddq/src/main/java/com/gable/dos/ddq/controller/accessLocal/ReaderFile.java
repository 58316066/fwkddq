package com.gable.dos.ddq.controller.accessLocal;

import com.gable.dos.ddq.library.Config_Datetime;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ReaderFile implements ItemReader<Integer> {
    private ApplicationContext context = null;
    private MongoTemplate mongoTemplate = null;
    private MongoCollection<Document> collection = null;

    private List<Object> arrayList = new ArrayList();
    //    Model_Main modelMain = new Model_Main();
    HashMap<String, Object> hashMapObj1;
    HashMap<String, Object> hashMapObj2;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();

    private Date dateString = Config_Datetime.dateString;


    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("Retry 1");
        final File folder = new File("D:\\fwkddq");

        List<String> fileNo = new ArrayList<>();
        List<String> folderNo = new ArrayList<>();
        System.out.println("\n Directory Path : \"" + folder + "\"");
        System.out.println("...............................................");

        search(".*\\.*", folder, fileNo, folderNo);


//        log.info("arrayList : " + modelMain);
//        modelMain.setCreateDate(new Date());
//        modelMain.setJob_id("AccessFile21");
        ExecutionSQL(arrayList);

        System.out.println(":::::::::::::::::::::::::::::");
        System.out.println("Number of File = " + fileNo.size() + " Files");
        System.out.println("Number of Folder = " + folderNo.size() + " Folders");
        System.out.println(":::::::::::::::::::::::::::::");

        return null;
    }

    public void search(final String pattern, final File folderNo, List<String> fileNo, List<String> folderName) {

        for (final File f : folderNo.listFiles()) {

            if (f.isDirectory()) {

                ModelFile modelFile_Folder = new ModelFile();
                folderName.add(f.getAbsolutePath());

                System.out.println("Folder : " + f.getName());
                System.out.print("|_ ");

                modelFile_Folder.setFull_FileName(f.getAbsolutePath());
                modelFile_Folder.setFileName(f.getName());
                modelFile_Folder.setDateTime("");
                modelFile_Folder.setSize("");
                modelFile_Folder.setType("Folder");

                hashMapObj1 = new HashMap<>();
                hashMapObj1.put("Full_FileName", modelFile_Folder.getFull_FileName());
                hashMapObj1.put("Size", modelFile_Folder.getSize());
                hashMapObj1.put("FileName", modelFile_Folder.getFileName());
                hashMapObj1.put("DateTime", modelFile_Folder.getDateTime());
                hashMapObj1.put("Type", modelFile_Folder.getType());
                arrayList.add(hashMapObj1);

                search(pattern, f, fileNo, folderName);
            }
            if (f.isFile()) {
                if (f.getName().matches(pattern)) {

                    Path file = Paths.get(f.getAbsolutePath());
                    try {
                        ModelFile modelFile_File = new ModelFile();
                        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                        FileTime time = attr.creationTime();

                        String datePattern = "dd/MM/yyyy HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
                        String formatted = simpleDateFormat.format(new Date(time.toMillis()));

                        System.out.println("File : " + f.getName());
                        System.out.print("  |__ ");
                        System.out.println("Size : " + f.length() + " bytes ");
                        System.out.println("  |__ createTime : " + formatted);

                        double bytes = f.length();
                        double kilobytes = (bytes / 1024);
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        double kilobyte = Double.parseDouble(formatter.format(kilobytes));

                        modelFile_File.setSize(kilobyte + " KB");
                        modelFile_File.setFull_FileName(f.getAbsolutePath());
                        modelFile_File.setFileName(f.getName());
                        modelFile_File.setDateTime(formatted);
                        modelFile_File.setType("File");

                        hashMapObj2 = new HashMap<>();
                        hashMapObj2.put("Full_FileName", modelFile_File.getFull_FileName());
                        hashMapObj2.put("Size", modelFile_File.getSize());
                        hashMapObj2.put("FileName", modelFile_File.getFileName());
                        hashMapObj2.put("DateTime", modelFile_File.getDateTime());
                        hashMapObj2.put("Type", modelFile_File.getType());
                        arrayList.add(hashMapObj2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    fileNo.add(f.getAbsolutePath() + " {Size ==> " + f.length() + " bytes }");
                }
            }
        }

        System.out.println("");
    }

    private void ExecutionSQL(List<Object> item) {

        ConnectDB();

        Document document = new Document();
        document.put("processDate", date);
        document.put("date",dateString);
//        document.put("month_id",date.getMonth()+1);
        document.put("job_id", "AccessFile21");
        document.put("AccessLocal", item);

        collection.insertOne(document);
    }

    private void ConnectDB() {
        try {
            context = new ClassPathXmlApplicationContext("datasource/context-mongoDB.xml");
            mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
            collection = mongoTemplate.getCollection("FWKDDQ_Output");
//            collection = mongoTemplate.getCollection("Local_Files");
        } catch (Exception e) {
            log.error("get ContextBean Error :::=> " + e.getMessage());
            log.info(":::::::::::::: System Exit ::::::::::::::");
            SpringApplication.exit(context, () -> 0);
            System.exit(199);
        }
    }
}
