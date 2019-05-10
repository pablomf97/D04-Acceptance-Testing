package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest{

	

	@Autowired
	private AuditService auditService;

	@Autowired
	private PositionService positionService;

	

	/*
	 * Total coverage of all tests
	 * 
	 * 
	 * Coverage of the total project (%): 
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 
	 * 
	 * ################################################################
	 * 
	 * Total coverage by exclusively executing this test class
	 * 
	 * 
	 * Coverage of the total project (%):2.5
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 1000
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage audit, which includes listing, displaying,
	 * creating, updating, and deleting it. UC 3.1,3.2 Acme-Rookies
	 */

	/*
	 * ####################### TEST CREATE AUDIT #######################
	 */

	@Test
	public void driverCreateAudit(){
		Object testingData[][] = {
				{"auditor1",new Date(System.currentTimeMillis()-1),"Test text",7,"position1c1",null},
				//Positive test case 

				{"company1",new Date(System.currentTimeMillis()-1),"Test text",7,"position1c1",IllegalArgumentException.class},

				//Negative test case, trying to create and audit as a company (wrong actor).

				{null,new Date(System.currentTimeMillis()-1),"Test text",7,"position1c1",IllegalArgumentException.class},

				//Negative test case, null actor.
		};

		for(int i = 0;i < testingData.length; i++){
			this.templateCreateAudit((String)testingData[i][0],
					(Date) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3],
					(String) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void templateCreateAudit(String username,
			Date moment,String text,Integer score,String position,Class<?>expected){

		Class<?> caught = null;

		try{
			this.authenticate(username);

			Position pos = this.positionService.findOne(this.getEntityId(position));

			Audit audit = this.auditService.create(pos);

			audit.setMoment(moment);
			audit.setScore(score);
			audit.setText(text);

			this.auditService.save(audit);
			this.auditService.flush();

			this.unauthenticate();

		}catch(Throwable oops){
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	/*
	 * ####################### TEST EDIT  AUDIT #######################
	 */

	@Test
	public void driverEditAudit(){
		Object testingData[][] = {

				{"auditor1","audit1",new Date(System.currentTimeMillis()-1),"Test text",7,null},

				//Positive test case

				{"auditor1","audit4",new Date(System.currentTimeMillis()-1),"Test text",7,IllegalArgumentException.class
				},

				//Negative test case, audit4 doesn't correspond to auditor 1

				{"auditor1","audit3",new Date(System.currentTimeMillis()-1),"Test text",7,IllegalArgumentException.class},
				
				//Negative test case, audit3 is saved in final mode
				
				{"company1","audit3",new Date(System.currentTimeMillis()-1),"Test text",7,IllegalArgumentException.class}
				
				//Negative test case, wrong actor
		};

		for(int i = 0;i < testingData.length; i++){
			this.templateEditAudit((String)testingData[i][0],
					(String)testingData[i][1],
					(Date)testingData[i][2],
					(String)testingData[i][3],
					(Integer)testingData[i][4],
					(Class<?>)testingData[i][5]);
		}
	}

	protected void templateEditAudit(String username, String audit,
			Date moment,String text, Integer score, Class<?> expected){

		Class<?>caught = null;

		try{
			this.authenticate(username);

			Audit aud = this.auditService.findOne(this.getEntityId(audit));

			aud.setMoment(moment);
			aud.setText(text);
			aud.setScore(score);

			this.auditService.save(aud);
			this.auditService.flush();

			this.unauthenticate();

		}catch(Throwable oops){
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	/*
	 * ####################### TEST DELETE  AUDIT #######################
	 */
	
	@Test
	public void driverDeleteAudit(){
		Object testingData[][] = {
				{"auditor1","audit2",null},
				//Positive test case
				
				{"auditor1","audit3",IllegalArgumentException.class},
				//Negative test case, audit3 is final mode
				
				{"auditor1","audit4",IllegalArgumentException.class},
				//Negative test case, audit4 doesn't correspond to auditor1
				
				{"company1","audit5",IllegalArgumentException.class},
				//Negative test case, wrong actor
				
				{null,"audit1",IllegalArgumentException.class}
				//Negative test case, null actor
				
		};
		
		for(int i = 0;i < testingData.length;i++){
			
		}
		
	}
	
	protected void templateDeleteAudit(String username,String audit,Class<?>expected){
		
		Class<?>caught = null;
		
		try{
			this.authenticate(username);
			
			Audit aux = this.auditService.findOne(this.getEntityId(audit));
			
			this.auditService.delete(aux);
			
			this.auditService.flush();
			
			this.unauthenticate();
			
		}catch(Throwable oops){
			caught = oops.getClass();
		}
		
		super.checkExceptions(expected, caught);
	}


}
