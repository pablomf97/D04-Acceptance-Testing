package services;


import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Company;




@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {




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
	 * Coverage of the total project (%):14,2%
	 * 
	 * 
	 * Coverage of the total project (lines of codes):5575
	 */


	@Autowired
	private ActorService actorService;

	@Autowired
	private FinderService finderService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private RookieService rookieService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private ProviderService providerService;
	@Autowired
	private CompanyService companyService;
	



	//	RF.11.1		The maximum of the number of positions per company.
	@Test 
	public void maxPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 4, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "rookie1", 3, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			maxPositionPerCompanyTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxPositionPerCompanyTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxPositionPerCompanyTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxPositionPerCompanyTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.positionService.maxPositionPerCompany();
		Assert.isTrue(res.intValue()==max);


	}
	//	RF.11.1		The minimum of the number of positions per company.
	@Test
	public void minPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 0, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "rookie1", 0, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minPositionPerCompanyTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minPositionPerCompanyTemplate(String username,Integer min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minPositionPerCompanyTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minPositionPerCompanyTest(Integer min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res =this.positionService.minPositionPerCompany();
		Assert.isTrue(res.intValue()==min);


	}

	//	RF.11.1		The average of the number of positions per company.

	@Test
	public void avgPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin",0.46667, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgPositionPerCompanyTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgPositionPerCompanyTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgPositionPerCompanyTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgPositionPerCompanyTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.avgPositionPerCompany();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.1		The standard deviation of the number of positions per company.

	@Test
	public void stddevPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 1.2037, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			stddevPositionPerCompanyTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void stddevPositionPerCompanyTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.stddevPositionPerCompanyTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void stddevPositionPerCompanyTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.sttdevPositionPerCompany();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.2		The standard deviation of the number of applications per rookie

	@Test
	public void sttdevApplicationsPerRookieDriver() {
		Object testingData[][] = { { "admin", 1.6, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			sttdevApplicationsPerRookieTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void sttdevApplicationsPerRookieTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.sttdevApplicationsPerRookieTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void sttdevApplicationsPerRookieTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.applicationService.sttdevApplicationsPerRookie();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.2		The average of the number of applications per rookie

	@Test
	public void avgdevApplicationsPerRookieDriver() {
		Object testingData[][] = { { "admin", 0.8, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgdevApplicationsPerRookieTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgdevApplicationsPerRookieTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgdevApplicationsPerRookieTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgdevApplicationsPerRookieTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.applicationService.avgApplicationsPerRookie();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.2		The maximum of the number of applications per rookie

	@Test 
	public void maxApplicationsPerRookieDriver() {
		Object testingData[][] = { { "admin", 4, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "rookie1", 3, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			maxApplicationsPerRookieTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxApplicationsPerRookieTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxApplicationsPerRookieTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxApplicationsPerRookieTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.applicationService.maxApplicationsPerRookie();
		Assert.isTrue(res.intValue()==max);


	}

	//	RF.11.2		The minimum of the number of applications per rookie

	@Test 
	public void minApplicationsPerRookieDriver() {
		Object testingData[][] = { { "admin", 0, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "rookie1", 3, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minApplicationsPerRookieTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minApplicationsPerRookieTemplate(String username,Integer min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minApplicationsPerRookieTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minApplicationsPerRookieTest(Integer min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.positionService.minPositionPerCompany();
		Assert.isTrue(res.intValue()==min);


	}

	//	RF.11.5		The minimum of the salaries offered

	@Test
	public void minSalarayPositionsDriver() {
		Object testingData[][] = { { "admin", 4.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minSalarayPositionsTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minSalarayPositionsTemplate(String username,Double min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minSalarayPositionsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minSalarayPositionsTest(Double min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.minSalarayPositions();
		Assert.isTrue(res.doubleValue()==min);

	}
	//	RF.11.5		The maximun of the salaries offered

	@Test
	public void maxSalarayPositionsDriver() {
		Object testingData[][] = { { "admin", 150.1, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			maxSalarayPositionsTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxSalarayPositionsTemplate(String username,Double min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxSalarayPositionsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxSalarayPositionsTest(Double min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.maxSalaryPositions();
		Assert.isTrue(res.doubleValue()==min);

	}
	//	RF.11.5		The average of the salaries offered

	@Test
	public void avgSalarayPositionsDriver() {
		Object testingData[][] = { { "admin",59.142857142857146, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgSalarayPositionsTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgSalarayPositionsTemplate(String username,Double min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgSalarayPositionsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgSalarayPositionsTest(Double min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.AVGSalaryPositions();
		Assert.isTrue(res.doubleValue()==min);

	}

	//	RF.11.5		The standard deviation of the salaries offered

	@Test
	public void STTDEVSalarayPositionsDriver() {
		Object testingData[][] = { { "admin", 59.24568355929538, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			STDDEVSalarayPositionsTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void STDDEVSalarayPositionsTemplate(String username,Double min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.STDDEVSalarayPositionsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void STDDEVSalarayPositionsTest(Double min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.STDDEVSalaryPositions();
		Assert.isTrue(res.doubleValue()==min);

	}

	//	RF.11.3		The company that have offered more positions

	@Test
	public void companyWithMorePositionsDriver() {
		Object testingData[][] = { { "admin","Gustavos S.A.", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "rookie1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			companyWithMorePositionsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void companyWithMorePositionsTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.companyWithMorePositionsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void companyWithMorePositionsTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		String res =this.positionService.companyWithMorePositions();
		Assert.isTrue(res.contentEquals(min));

	}

	//	RF.11.4		The rookie who have made more applications

	@Test
	public void rookieWithMoreApplicationsTest() {
		Object testingData[][] = { { "admin","Alberto", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "rookie1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			rookieWithMoreApplicationsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void rookieWithMoreApplicationsTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.rookieWithMoreApplicationsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void rookieWithMoreApplicationsTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		String res =this.rookieService.rookieWithMoreApplications();
		Assert.isTrue(res.contentEquals(min));

	}

	//	RF.11.6		The worst position in terms of salary


	@Test
	public void worstPositionSalaryDriver() {
		Object testingData[][] = { { "admin","Position 3 of company 1", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "rookie1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			worstPositionSalaryTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void worstPositionSalaryTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.worstPositionSalaryTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void worstPositionSalaryTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		String res =this.positionService.worstPositionSalary();
		Assert.isTrue(res.contentEquals(min));

	}	

	//	RF.11.6		The best position in terms of salary


	@Test
	public void bestPositionSalaryDriver() {
		Object testingData[][] = { { "admin","Position 3 of company 2", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "rookie1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			bestPositionSalaryTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void bestPositionSalaryTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.bestPositionSalaryTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void bestPositionSalaryTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		String res =this.positionService.bestPositionSalary();
		Assert.isTrue(res.contentEquals(min));

	}	

	//LEVEL B

	//	RF.18.2	Max results in the finders
	@Test 
	public void maxResultsFinderDriver() {
		Object testingData[][] = { { "admin", 0.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			maxResultsFinderTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxResultsFinderTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxResultsFinderTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxResultsFinderTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.StatsFinder()[0];
		Assert.isTrue(res.doubleValue()==max);


	}




	//	RF.18.2	Min results in the finders

	@Test 
	public void minResultsFinderDriver() {
		Object testingData[][] = { { "admin", 0.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minResultsFinderTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minResultsFinderTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minResultsFinderTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minResultsFinderTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.StatsFinder()[1];
		Assert.isTrue(res.doubleValue()==max);


	}



	//	RF.18.2	Average results in the finders

	@Test 
	public void avgResultsFinderDriver() {
		Object testingData[][] = { { "admin", 0.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgResultsFinderTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgResultsFinderTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgResultsFinderTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgResultsFinderTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.StatsFinder()[2];
		Assert.isTrue(res.doubleValue()==max);


	}

	//	RF.18.2	Standard deviation results in the finders

	@Test 
	public void stdevResultsFinderDriver() {
		Object testingData[][] = { { "admin", 0.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgResultsFinderTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void stdevResultsFinderTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.stdevResultsFinderTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void stdevResultsFinderTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.StatsFinder()[3];
		Assert.isTrue(res.doubleValue()==max);


	}

	//	RF.18.3	The ratio of empty versus non-empty finders


	@Test 
	public void ratioFindersDriver() {
		Object testingData[][] = { { "admin", 1.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			ratioFindersTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void ratioFindersTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.ratioFindersTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void ratioFindersTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.ratioFinders();
		Assert.isTrue(res.doubleValue()==max);


	}





	//	RF.18.1	Average of the number of curricula per rookie

	@Test 
	public void stdevCurriculaPerRookieDriver() {
		Object testingData[][] = { { "admin", 1.54919, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			stdevCurriculaPerRookieTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void stdevCurriculaPerRookieTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.stdevCurriculaPerRookieTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void stdevCurriculaPerRookieTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.stdevCurriculaPerRookie();
		Assert.isTrue(res.doubleValue()==max);


	}



	//	RF.18.1	Maximum of the number of curricula per rookie

	@Test 
	public void MaxCurriculaPerRookieDriver() {
		Object testingData[][] = { { "admin", 5, null },// Positive
				{ "admin", 54, IllegalArgumentException.class },//non expected

				{ "rookie1", 4, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			MaxCurriculaPerRookieTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void MaxCurriculaPerRookieTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.MaxCurriculaPerRookieTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void MaxCurriculaPerRookieTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.finderService.MaxCurriculaPerRookie();
		Assert.isTrue(res.doubleValue()==max);


	}



	//	RF.18.1	Minimum of the number of curricula per rookie

	@Test 
	public void minCurriculaPerRookieDriver() {
		Object testingData[][] = { { "admin", 1, null },// Positive
				{ "admin", 54, IllegalArgumentException.class },//non expected

				{ "rookie1", 4, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minCurriculaPerRookieTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minCurriculaPerRookieTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minCurriculaPerRookieTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minCurriculaPerRookieTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.finderService.MinCurriculaPerRookie();
		Assert.isTrue(res.intValue()==max);


	}


	//	RF.18.1	Deviation standard of the number of curricula per rookie

	@Test 
	public void avgCurriculaPerRookieDriver() {
		Object testingData[][] = { { "admin", 2.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgCurriculaPerRookieTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgCurriculaPerRookieTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgCurriculaPerRookieTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgCurriculaPerRookieTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.AvgCurriculaPerRookie();
		Assert.isTrue(res.doubleValue()==max);


	}

	//	 The average, the minimum, the maximum, and the standard deviation of the
	//	 audit score of the positions stored in the system.
	
	@Test 
	public void statsAuditsPerPositionDriver() {
		Object testingData[][] = { { "admin", 10.0,3.0,6.5,2.5, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "rookie1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			statsAuditsPerPositionTemplate((String) testingData[i][0],
					(Double) testingData[i][1],(Double) testingData[i][2],
					(Double) testingData[i][3],(Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void statsAuditsPerPositionTemplate(String username,Double max,
			Double min, Double avg, Double stddev, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.statsAuditsPerPositionTest(max,min,avg,stddev);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void statsAuditsPerPositionTest(Double max, Double min, Double avg, Double stddev) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double [] res=this.auditService.statsAuditPositions();
		Assert.isTrue(res[0].doubleValue()==max&&res[1].doubleValue()==min&&res[2].doubleValue()==avg&&res[3].doubleValue()==stddev);


	}
	

	//	 The average, the minimum, the maximum, and the standard deviation of the
	//	 audit score of the companies that are registered in the system.
	
	@Test 
	public void statsScoreCompaniesDriver() {
		Object testingData[][] = { { "admin", 1.0,0.2,0.9266666666666666,0.20805982045769647, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "rookie1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			statsScoreCompaniesTemplate((String) testingData[i][0],
					(Double) testingData[i][1],(Double) testingData[i][2],
					(Double) testingData[i][3],(Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void statsScoreCompaniesTemplate(String username,Double max,
			Double min, Double avg, Double stddev, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.statsScoreCompaniesTest(max,min,avg,stddev);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void statsScoreCompaniesTest(Double max, Double min, Double avg, Double stddev) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double [] res=this.companyService.statsScoreCompanies();
		Assert.isTrue(res[0].doubleValue()==max&&res[1].doubleValue()==min&&res[2].doubleValue()==avg&&res[3].doubleValue()==stddev);


	}
	


	//The companies with the highest audit score.
	
	@Test
	public void CompaniesHighestScoresDriver() {
		Object testingData[][] = { { "admin", "Disney", null},
				{ "admin", "PEPE", IllegalArgumentException.class },//non expected

				{ "rookie1", "Disney", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			CompaniesHighestScoresTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void CompaniesHighestScoresTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.CompaniesHighestScoresTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void CompaniesHighestScoresTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		List<Company> res =(List<Company>) this.companyService.CompaniesHighestScores();
		Assert.isTrue(res.get(0).getCommercialName().equals(min));

	}
	
	
	
	
	
	
	
	
	
	//	 The average salary offered by the positions that have the highest average
	//	 audit score.
	
	@Test
	public void avgSalaryPerPositionHighestScoreAuditsDriver() {
		Object testingData[][] = { 
				{ "admin",16.5 , null},//positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "rookie1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgSalaryPerPositionHighestScoreAuditsTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgSalaryPerPositionHighestScoreAuditsTemplate(String username,Double min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgSalaryPerPositionHighestScoreAuditsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgSalaryPerPositionHighestScoreAuditsTest(Double min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.avgSalaryPerPositionHighestScoreAudits();
		Assert.isTrue(res.doubleValue()==min);

	}
	


	//	The minimum, the maximum, the average, and the standard deviation of the
	//	number of items per provider.
	
	
	
	@Test 
	public void statsItemsPerProviderDriver() {
		Object testingData[][] = { { "admin", 20.,0.,4.16667,7.10438, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "rookie1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			statsItemsPerProviderTemplate((String) testingData[i][0],
					(Double) testingData[i][1],(Double) testingData[i][2],
					(Double) testingData[i][3],(Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void statsItemsPerProviderTemplate(String username,Double max,
			Double min, Double avg, Double stddev, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.statsItemsPerProviderTest(max,min,avg,stddev);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void statsItemsPerProviderTest(Double max, Double min, Double avg, Double stddev) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double [] res=this.providerService.statsItemsPerProvider();
		Assert.isTrue(res[0].doubleValue()==max&&res[1].doubleValue()==min&&res[2].doubleValue()==avg&&res[3].doubleValue()==stddev);


	}

	//  The top-5 providers in terms of total number of items provided.
	
	@Test
	public void top5ProvidersWithItemsDriver() {
		Object testingData[][] = { { "admin", "Alberto,Francisco,Pablo,Carlos,Pepe", null},
				{ "admin", "PEPE", IllegalArgumentException.class },//non expected

				{ "rookie1", "Disney", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			top5ProvidersWithItemsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void top5ProvidersWithItemsTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.top5ProvidersWithItemsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void top5ProvidersWithItemsTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		List<String> res =(List<String>) this.providerService.top5ProvidersWithItems();
		String comp=res.get(0)+","+res.get(1)+","+res.get(2)+","+res.get(3)+","+res.get(4);
		Assert.isTrue(comp.equalsIgnoreCase(min));

	}
	
	

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of sponsorships per provider.
	
	
	@Test 
	public void statsSponsorshipsPerProviderDriver() {
		Object testingData[][] = { { "admin", 0.0,0.0,0.0,0.0, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "rookie1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			statsSponsorshipsPerProviderTemplate((String) testingData[i][0],
					(Double) testingData[i][1],(Double) testingData[i][2],
					(Double) testingData[i][3],(Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void statsSponsorshipsPerProviderTemplate(String username,Double max,
			Double min, Double avg, Double stddev, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.statsSponsorshipsPerProviderTest(max,min,avg,stddev);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void statsSponsorshipsPerProviderTest(Double max, Double min, Double avg, Double stddev) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double [] res=this.providerService.statsSponsorshipsPerProvider();
		Assert.isTrue(res[0].doubleValue()==max&&res[1].doubleValue()==min&&res[2].doubleValue()==avg&&res[3].doubleValue()==stddev);


	}
	
	

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of sponsorships per position.
	
	@Test 
	public void statsSponsorshipsPerPositionDriver() {
		Object testingData[][] = { { "admin", 0.0,0.0,0.0,0.0, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "rookie1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			statsSponsorshipsPerPositionTemplate((String) testingData[i][0],
					(Double) testingData[i][1],(Double) testingData[i][2],
					(Double) testingData[i][3],(Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	protected void statsSponsorshipsPerPositionTemplate(String username,Double max,
			Double min, Double avg, Double stddev, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.statsSponsorshipsPerPositionTest(max,min,avg,stddev);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void statsSponsorshipsPerPositionTest(Double max, Double min, Double avg, Double stddev) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double [] res=this.positionService.statsSponsorshipsPerPosition();
		Assert.isTrue(res[0].doubleValue()==max&&res[1].doubleValue()==min&&res[2].doubleValue()==avg&&res[3].doubleValue()==stddev);


	}
	
	
	
	//	The providers who have a number of sponsorships that is at least 10% above
	//	the average number of sponsorships per provider.
	
	
	@Test
	public void Percentage10AVGSponsorshipPerProviderDriver() {
		Object testingData[][] = { 
				{ "admin","[]" , null},//positive
				{ "admin", "PEPE", IllegalArgumentException.class },//non expected

				{ "rookie1", "Francisco", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			Percentage10AVGSponsorshipPerProviderTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void Percentage10AVGSponsorshipPerProviderTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.Percentage10AVGSponsorshipPerProviderTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void Percentage10AVGSponsorshipPerProviderTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Collection<String> col=this.providerService.Percentage10AVGSponsorshipPerProvider();
		Assert.isTrue(col.toString().equalsIgnoreCase(min));

	}
	
	
}
