package com.gable.dos.ddq.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.gable.dos.ddq.FwkddQApplication;
import com.gable.dos.errcode.ErrorCode;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gable.dos.logshow.LogShow;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


//@ImportResource("classpath:spring-batch-context.xml")
//@ImportResource({ "spring-batch-context.xml" })


@Data
@Slf4j
@Configuration
@EnableBatchProcessing
// @EnableScheduling
@PropertySource("classpath:mongoconfig.properties")
public class SpringConfig {

    public static String dateargs = "";

    @Autowired
    Environment env;

    @Value("${initialize.Datasource}")
    private Properties properties;

    @Autowired
    private ApplicationArguments args;

    @Autowired
    public DataSource dataSource;

//    @Value("org/springframework/batch/core/schema-drop-sqlite.sql")
//    private Resource dropReopsitoryTables;

//    @Value("org/springframework/batch/core/schema-sqlite.sql")
//    private Resource dataReopsitorySchema;

    //    @Autowired
    private ApplicationContext context, contextForInit, contextDaily;

    @Autowired
    private JobLauncher jobLauncher;

//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;

//    @Autowired
//    private Job job;

//    @Autowired
//    private JobExecution execution;

    private String[] lstJobNames;

    private String[] lstInitJobName = {};

    private String[] lstDailyJobName = {};

    private String JobName = "";
    private String JobInit = "";

    private Boolean isTestConnection = false;

    public static Properties prop;

    public static Logger logger;
    public static FileHandler fh;

    public static List<String> field_name;
    public static List<String> row_num;
    public static int row_number;
    public static String dtp_file_name;

    @Bean
    public void argsComponent() {

        log.info("start initial argsComponent");
        log.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()) {
            log.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        if (args.containsOption("processDate")) {
            List<String> dates = args.getOptionValues("processDate");

            dateargs = dates.get(0);

//            log.info("dateArgs  = " + dateargs);

//            System.setProperty("dateSet.config", dates.get(0));
//            String dateSet = env.getProperty("dateSet.config");
//            log.info("dateSet.config = " + dateSet);


        } else {
            dateargs = env.getProperty("dateSet.config");
        }

        if (args.containsOption("JobName")) {
            //Get argument values
            List<String> values = args.getOptionValues("JobName");

            for (String dailyName : values) {
                if (dailyName.equals("daily")) {
                    //ทำทุก functions
                    getDailyJobName();
                    break;
                }
            }
//            this.setJobName("all");
            this.setJobName(values.get(0));
            log.info("JobName = " + this.getJobName());
        }

        if (args.containsOption("initialize")) {
            List<String> values = args.getOptionValues("initialize");

            for (String initName : values) {
                if (initName.equals("all")) {
                    //ทำทุก functions
                    Set_initialize_DB();
                    getInitJobName();
                    isTestConnection = true;
                    break;
                }
                if (initName.equals("createRepository")) {
                    Set_initialize_DB();
                }
                if (initName.equals("checkdb")) {
                    isTestConnection = true;
                }
                if (initName.equals("creatMongo")) {
                    getInitJobName();
                }
            }
            if (values.size() == 0) {
                log.info("initialize Parameter has : all | checkdb | createRepository | creatMongo");
            }
        }

        if (args.containsOption("ExcelJob")) {
            List<String> exvalue = args.getOptionValues("ExcelJob");

            for (String excel : exvalue) {
                if (excel.equals("all")) {
                    //ทำทุก functions
                    getReadExcel();
                    getWriteExcel();
                    break;
                }
                if (excel.equals("read")) {
                    getReadExcel();
                }
                if (excel.equals("write")) {
                    getWriteExcel();
                }
            }
        }

//        if (args.containsOption("datapump")) {
//            if (!args.containsOption("field_name") || !args.containsOption("row_num") || !args.containsOption("name")) {
//                ErrorCode.toValidate("E1000", "!!! Argument missing !!! ");
//            }
//            //Get argument values
//            field_name = args.getOptionValues("field_name");
//            row_num = args.getOptionValues("row_num");
//            row_number = Integer.parseInt(row_num.get(0));
//            dtp_file_name = args.getOptionValues("name").get(0);
//        }


        log.info("JobName = " + this.getJobName());


        log.info("stop initial argsComponent");
//        return args;
    }

    @Bean
    public void get_Properties() {
        prop = new Properties();
        try (InputStream input = FwkddQApplication.class.getClassLoader().getResourceAsStream("mongoconfig.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            fh = new FileHandler(prop.getProperty("part.logfile") + "logger.log");
            logger = Logger.getLogger("logger.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void getReadExcel() {
        try {
            ExcelReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private void getWriteExcel() {
        try {
            ExcelWriter.write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public void getInitJobName() {
        try {
            contextForInit = new ClassPathXmlApplicationContext("jobs/batch-init.xml");
            lstInitJobName = contextForInit.getBeanNamesForType(Job.class);
            log.info("get contextForInit success" + lstInitJobName.length);

        } catch (Exception e) {
            log.error("error : " + e.getMessage());
        }

        log.info("lstInitJobName : " + lstInitJobName);
    }

    public void getDailyJobName() {
        try {
            contextDaily = new ClassPathXmlApplicationContext("jobs/batch-job-daily.xml");
            lstDailyJobName = contextDaily.getBeanNamesForType(Job.class);
            log.info("get contextDaily success" + lstDailyJobName.length);
        } catch (Exception e) {
            log.error("error : " + e.getMessage());
        }
        log.info("lstDailyJobName : " + lstDailyJobName);

    }

    public void Set_initialize_DB() {
        System.setProperty("initialize.Datasource", "true");
        String initDB = env.getProperty("initialize.Datasource");
        log.info("initialize.Datasource = " + initDB);
    }

    @Bean
    public ApplicationContext ContextConfig() {
        log.info("start initial ContextConfig");

        try {
            context = new ClassPathXmlApplicationContext("spring-batch/spring-batch-context.xml");
            log.info("get context success");
            if (isTestConnection) {
                test_connection();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("stop done ContextConfig");

        return context;
    }

//	  Should be setup @load ContextConfig
//    @Bean
//    public JobRepository getJobRepository() throws Exception {
//    	
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        
//        log.info("getJobRepository...");
//        
//        factory.setDataSource((DataSource) context.getBean("dataSourceRepository"));
//        factory.setTransactionManager((PlatformTransactionManager) context.getBean("transactionManager"));
//        factory.setDatabaseType(dbType);
//        // JobRepositoryFactoryBean's methods Throws Generic Exception,
//        // it would have been better to have a specific one
//        factory.afterPropertiesSet();
//        return (JobRepository) factory.getObject();
//    }    

    public void test_connection() {
        log.info("start get_bean_name");

        Boolean bCheck = false;
        Connection connect = null;

        String[] beanNames = context.getBeanNamesForType(Object.class);

        for (String beanName : beanNames) {
            log.info("All bean name = " + beanName);

            final Class clazz = context.getType(beanName);

            log.info("class name = " + clazz.getTypeName());

            if ((beanName.toLowerCase().contains("datasource")) && !beanName.contains("#0")) {
                log.info("bean datasource name = " + beanName);

                DriverManagerDataSource contextBean = (DriverManagerDataSource) context.getBean(beanName);
                try {
                    String url = contextBean.getUrl();
                    String username = contextBean.getUsername();
                    String password = contextBean.getPassword();

                    log.info("get url = " + url);
                    log.info("get username = " + username);
                    log.info("get password = " + password);

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect = DriverManager.getConnection(url, username, password);
                    if (connect != null) {
                        bCheck = true;
                        log.info("Database Connected. { " + beanName + " }");
                    } else {
                        log.info("Database Connect Failed.........");
                    }

                } catch (Exception e) {
                    log.info("Database Connect Failed.");
                    log.info("Exception Error : " + e);
                }
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!bCheck) {
                    log.info("Exit ::::::::::::::");
                    SpringApplication.exit(context, () -> 0);
                    System.exit(199);
                }

            } else if (beanName.contains("mongoTemplate")) {  //mongo-client//mongoTemplate

                log.info("context mongo= " + context.getBean(beanName).getClass());
                log.info("bean name mongo = " + beanName);
                log.info("getType mongo = " + clazz.getTypeName());

                try {
                    MongoTemplate mongoTemplate = (MongoTemplate) context.getBean(beanName);

                    Set<String> coname = mongoTemplate.getCollectionNames();

                    log.info("Collection name = " + coname);

                    log.info("getMongoClientOptions: " + mongoTemplate);
                } catch (Exception e) {
                    log.info("getMongoClientOptions: Error");
                }
            }
        }
        log.info("stop run");
    }


    @Bean
    public JobLauncher run_jobLauncher() {

        log.info("start JobLauncher");

        jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        log.info("stop JobLauncher");
        return jobLauncher;
    }


    public Job run_job(String JobName) {

        // restartJob(JobName);

        Job job = null;

        log.info("start Job");

        try {
            job = (Job) context.getBean(JobName);

            log.info("stop Job");

        } catch (Exception e) {
            log.error("Exception :" + e.getMessage());
        }

        return job;

    }

    public void list_all_jobNames() {
        log.info("start list_all_jobNames");

        lstJobNames = context.getBeanNamesForType(Job.class);

        log.info("Number of JobNames = " + lstJobNames.length + " job(s)");
        log.info(LogShow.getStrLine());

        //loop split string array
        for (String jobName : lstJobNames) {
            log.info(" JobNames : " + jobName);
        }
        log.info(LogShow.getStrLine());
        log.info("done list_all_jobNames");
        log.info(LogShow.getStrLine());

    }

    public void restartJob(String jobName) {
        JobExplorer jobExplorer = (JobExplorer) context.getBean("jobExplorer");

        List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, 0, 1);

        for (JobInstance jobInstance : jobInstances) {
            List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
            for (JobExecution jobExecution : jobExecutions) {

                log.info("Job name " + jobInstance.getJobName());

                log.info("JobExecution ID :" + jobExecution.getId());
                log.info("JobInstance " + jobInstance.getInstanceId());

                jobExecution.stop();

                log.info(jobInstance.getJobName() + " stop : " + jobExecution.isStopping());
                log.info(jobInstance.getJobName() + " batch status : " + jobExecution.getStatus());

                log.info(jobInstance.getJobName() + " running : " + jobExecution.isRunning());
            }
        }
    }

    //  @Scheduled(fixedRate=10000)
    @Bean
    public JobExecution run_jobExecution() {
        JobExecution execution = null;

        for (String jobInitName : lstInitJobName) {
            try {
                Job job = (Job) contextForInit.getBean(jobInitName);
                execution = jobLauncher.run(job, new JobParameters());
                log.info("jobInitName Status : " + jobInitName + execution.getStatus());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        for (String jobDailyName : lstDailyJobName) {
            try {
                Job job = (Job) contextDaily.getBean(jobDailyName);
                execution = jobLauncher.run(job, new JobParameters());
                log.info("jobDailyName Status : " + jobDailyName + execution.getStatus());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        log.info("start JobExecution");

        //Check condition of jobName
        if (this.JobName.isEmpty()) {
            log.info("not run jobName ie Empty : [" + this.getJobName() + "]");
        } else {

            log.info(" run jobName :" + this.getJobName());
            try {
                //Execute all jobs
                if (this.getJobName().equalsIgnoreCase("all")) {
                    //get all job into this.lstJobNames
                    list_all_jobNames();

                    for (String jobName : lstJobNames) {
                        log.info("Execution JobNames : " + jobName);

                        execution = jobLauncher.run(run_job(jobName), new JobParameters());
                    }
                } else if (this.getJobName().equalsIgnoreCase("datapump")) {

                    if (!args.containsOption("field_name") || !args.containsOption("row_num") || !args.containsOption("file_name")) {
                        ErrorCode.toValidate("E1000", "!!! Argument missing !!! ");
                    }
                    //Get argument values
                    field_name = args.getOptionValues("field_name");
                    row_num = args.getOptionValues("row_num");
                    row_number = Integer.parseInt(row_num.get(0));
                    dtp_file_name = args.getOptionValues("file_name").get(0);

                    Job job = run_job(this.getJobName());

                    if (job != null) {
                        execution = jobLauncher.run(job, new JobParameters());
                    }
                } else //Execute on jobs Name from argument
                {
                    Job job = run_job(this.getJobName());

                    if (job != null) {
                        execution = jobLauncher.run(job, new JobParameters());
                    }
                }
            } catch (JobExecutionAlreadyRunningException e) {
                // TODO Auto-generated catch block
                log.error("JobExecutionAlreadyRunningException : " + e.getMessage());
                e.printStackTrace();
            } catch (JobRestartException e) {
                // TODO Auto-generated catch block
                log.error("JobRestartException : " + e.getMessage());
                e.printStackTrace();
            } catch (JobInstanceAlreadyCompleteException e) {
                // TODO Auto-generated catch block
                log.error("JobInstanceAlreadyCompleteException : " + e.getMessage());
                e.printStackTrace();
            } catch (JobParametersInvalidException e) {
                // TODO Auto-generated catch block
                log.error("JobParametersInvalidException : " + e.getMessage());
                e.printStackTrace();
            }
        }
        log.info("stop JobExecution");
        return execution;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        
//        log.info("dataSource...");
//        
//        dataSource.setDriverClassName("org.sqlite.JDBC");
//        dataSource.setUrl("jdbc:sqlite:repository.sqlite");
//        return dataSource;
//    }

//    @Bean
//    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) throws MalformedURLException {
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//
//        log.info("dataSourceInitializer...");
//        
//        databasePopulator.addScript(dropReopsitoryTables);
//        databasePopulator.addScript(dataReopsitorySchema);
//        databasePopulator.setIgnoreFailedDrops(true);
//
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator);
//
//        return initializer;
//    }

//    @Bean
//    public JobRepository getJobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        
//        log.info("getJobRepository...");
//        
//        factory.setDataSource(dataSource());
//        factory.setTransactionManager(getTransactionManager());
//        // JobRepositoryFactoryBean's methods Throws Generic Exception,
//        // it would have been better to have a specific one
//        factory.afterPropertiesSet();
//        return (JobRepository) factory.getObject();
//    }
//
//    private PlatformTransactionManager getTransactionManager() {
//        return new ResourcelessTransactionManager();
//    }

//    public JobLauncher getJobLauncher() throws Exception {
//        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//        // SimpleJobLauncher's methods Throws Generic Exception,
//        // it would have been better to have a specific one
//        
//        log.info("getJobLauncher...");
//        
//        jobLauncher.setJobRepository(getJobRepository());
//        jobLauncher.afterPropertiesSet();
//        return jobLauncher;
//    }

}