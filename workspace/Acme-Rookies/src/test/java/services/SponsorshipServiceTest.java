package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsorship;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest{

	@Autowired
	private SponsorshipService sponsorshipService;
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
	 * Coverage of the total project (%):
	 * 
	 * 
	 * Coverage of the total project (lines of codes):
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage sponsorships, which includes listing, displaying,
	 * creating, updating, and deleting it. UC 10.1 Acme-Rookies
	 */

	/*
	 * ####################### TEST CREATE SPONSORSHIP #######################
	 */

	@Test
	public void driverCreateSponsorship(){
		Object testingData[][] = {
				{"provider1","https://www.eff.org/files/banner_library/google-spy-eye.png",
					"target",null},
					//Positive test case
					
				{"rookie1","https://www.eff.org/files/banner_library/google-spy-eye.png",
					"target",IllegalArgumentException.class},
					//Negative test case, wrong actor
				{null,"https://www.eff.org/files/banner_library/google-spy-eye.png",
					"target",IllegalArgumentException.class},
					//Negative test case, null actor
				{"provider1","https://www.eff.org/files/banner_library/google-spy-eye.png",
					" ",IllegalArgumentException.class}
					//Negative test case, target cannot be blank
		};

		for(int i=0;i<testingData.length;i++){
			this.templateCreateSponsorship((String)testingData[i][0],
					(String)testingData[i][1],
					(String)testingData[i][2],
					(Class<?>)testingData[i][3]);
		}
	}

	protected void templateCreateSponsorship(String username,String banner,String target,Class<?>expected){
		Class<?>caught = null;

		try{

			this.authenticate(username);

			Sponsorship sp = this.sponsorshipService.create();

			sp.setBanner(banner);
			sp.setTarget(target);
			
			this.sponsorshipService.save(sp);
			this.sponsorshipService.flush();
			
			this.unauthenticate();
			

		}catch(Throwable oops){
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * ####################### TEST EDIT SPONSORSHIP #######################
	 */

}
