package com.gable.dos.ddq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gable.dos.ddq.controller.SpringConfig;

//@RunWith(SpringRunner.class)
//@SpringBootTest

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
//                                    "/jobs/skipSampleJob.xml" })
//@Ignore
public class FwkddQApplicationTests {

    @Autowired
    private Environment environment;
    
	@Before
	public void initTest() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.initTest");
		System.out.println("----------------------------------------------------------------------------------------");
	}

	@Test
	@Ignore
	public void processTest_SpringConfig_ExcelJob_Read() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest_SpringConfig_ExcelJob_Read");
		System.out.println("----------------------------------------------------------------------------------------");
		
		try {
			String[] args = {"--ExcelJob=read"};
					
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

			SpringConfig springConfig = new SpringConfig();
			springConfig.setArgs(applicationArguments);			
			springConfig.argsComponent();
			
			assertTrue(true);
						
		} catch (Exception e) {
            e.printStackTrace();
    		fail("Exception=" + e);
		}
	}

	@Test
	@Ignore
	public void processTest_SpringConfig_ExcelJob_Write() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest_SpringConfig_ExcelJob_Write");
		System.out.println("----------------------------------------------------------------------------------------");
		boolean isSue = false;

		try {
			String[] args = {"--ExcelJob=write"};

			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

			SpringConfig springConfig = new SpringConfig();
			springConfig.setArgs(applicationArguments);			
			
			assertTrue(isSue);
						
		} catch (Exception e) {
            e.printStackTrace();
    		fail("Exception=" + e);
		}
	}

	@Test
	public void processTest_SpringConfig_JobName() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest_SpringConfig_JobName");
		System.out.println("----------------------------------------------------------------------------------------");
		
		try {
			String[] args = {"--JobName=all"};
					
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

			SpringConfig springConfig = new SpringConfig();
			springConfig.setArgs(applicationArguments);			
			springConfig.argsComponent();
			assertEquals("all", springConfig.getJobName());
			
		} catch (Exception e) {
			// TODO: handle exception
//			log.info("===============Collection is null================");
		}
	}

	@Test
	@Ignore
	public void processTest_SpringConfig_initialize_checkdb() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest_SpringConfig_initialize_checkdb");
		System.out.println("----------------------------------------------------------------------------------------");
		
		try {
			
			String[] args = {"--initialize=checkdb"};
					
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

			SpringConfig springConfig = new SpringConfig();
			springConfig.setArgs(applicationArguments);			
			springConfig.argsComponent();
			
			assertTrue(springConfig.getIsTestConnection());
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			log.info("===============Collection is null================");
		}
	}
	@Test
	public void processTest_SpringConfig_initialize_all() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest_SpringConfig_initialize_all");
		System.out.println("----------------------------------------------------------------------------------------");
		
		try {				
			String[] args = {"--initialize=all"};
					
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			
			SpringConfig springConfig = new SpringConfig();
			springConfig.setArgs(applicationArguments);
			springConfig.setEnv(environment);			
			springConfig.argsComponent();
			log.info("Try getIsTestConnection : "+springConfig.getIsTestConnection());
			assertTrue(springConfig.getIsTestConnection());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
//			log.info("===============Collection is null================");
		}
	}

	@Test
	@Ignore
	public void processTest2() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.processTest2");
		System.out.println("----------------------------------------------------------------------------------------");
		
		String strVal1 = "String1";
		String strVal2 = "String1";
		int val2 = 1;		
		int val1 = 1;
		Integer val3 = new Integer(val2);
		val3 = null;

		assertEquals("String1", strVal2);
		assertTrue(val1 == val2);		
		assertNotNull(val3);
	}

	@After
	public void finishTest() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("FwkddQApplicationTests.finishTest");
		System.out.println("----------------------------------------------------------------------------------------");
	}

}
