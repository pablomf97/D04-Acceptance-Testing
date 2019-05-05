package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import utilities.AbstractTest;
import domain.Actor;




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
	 * Coverage of the total project (%):15,8%
	 * 
	 * 
	 * Coverage of the total project (lines of codes):3835
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
	private HackerService hackerService;
	
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

				{ "hacker1", 3, IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 0, IllegalArgumentException.class } //non authorized actor

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
		Object testingData[][] = { { "admin",2.33333, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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
		Object testingData[][] = { { "admin", 1.69967, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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

	//	RF.11.2		The standard deviation of the number of applications per hacker

	@Test
	public void sttdevApplicationsPerHackerDriver() {
		Object testingData[][] = { { "admin", 1.6, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			sttdevApplicationsPerHackerTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void sttdevApplicationsPerHackerTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.sttdevApplicationsPerHackerTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void sttdevApplicationsPerHackerTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.applicationService.sttdevApplicationsPerHacker();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.2		The average of the number of applications per hacker

	@Test
	public void avgdevApplicationsPerHackerDriver() {
		Object testingData[][] = { { "admin", 0.8, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgdevApplicationsPerHackerTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgdevApplicationsPerHackerTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgdevApplicationsPerHackerTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgdevApplicationsPerHackerTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.applicationService.avgApplicationsPerHacker();
		Assert.isTrue(res.doubleValue()==val);


	}

	//	RF.11.2		The maximum of the number of applications per hacker

	@Test 
	public void maxApplicationsPerHackerDriver() {
		Object testingData[][] = { { "admin", 4, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "hacker1", 3, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			maxApplicationsPerHackerTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxApplicationsPerHackerTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxApplicationsPerHackerTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxApplicationsPerHackerTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.applicationService.maxApplicationsPerHacker();
		Assert.isTrue(res.intValue()==max);


	}

	//	RF.11.2		The minimum of the number of applications per hacker

	@Test 
	public void minApplicationsPerHackerDriver() {
		Object testingData[][] = { { "admin", 0, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected

				{ "hacker1", 3, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minApplicationsPerHackerTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minApplicationsPerHackerTemplate(String username,Integer min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minApplicationsPerHackerTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minApplicationsPerHackerTest(Integer min) {
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

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

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

	//	RF.11.4		The hacker who have made more applications

	@Test
	public void hackerWithMoreApplicationsTest() {
		Object testingData[][] = { { "admin","Alberto", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "hacker1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			hackerWithMoreApplicationsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void hackerWithMoreApplicationsTemplate(String username,String min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.hackerWithMoreApplicationsTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void hackerWithMoreApplicationsTest(String min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		String res =this.hackerService.hackerWithMoreApplications();
		Assert.isTrue(res.contentEquals(min));

	}

	//	RF.11.6		The worst position in terms of salary


	@Test
	public void worstPositionSalaryDriver() {
		Object testingData[][] = { { "admin","Position 3 of company 1", null },// Positive
				{ "admin", "Aliexpress", IllegalArgumentException.class },//non expected

				{ "hacker1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", "El corte ingles", IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

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

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

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





	//	RF.18.1	Average of the number of curricula per hacker

	@Test 
	public void stdevCurriculaPerHackerDriver() {
		Object testingData[][] = { { "admin", 1.54919, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			stdevCurriculaPerHackerTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void stdevCurriculaPerHackerTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.stdevCurriculaPerHackerTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void stdevCurriculaPerHackerTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.stdevCurriculaPerHacker();
		Assert.isTrue(res.doubleValue()==max);


	}



	//	RF.18.1	Maximum of the number of curricula per hacker

	@Test 
	public void MaxCurriculaPerHackerDriver() {
		Object testingData[][] = { { "admin", 5, null },// Positive
				{ "admin", 54, IllegalArgumentException.class },//non expected

				{ "hacker1", 4, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			MaxCurriculaPerHackerTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void MaxCurriculaPerHackerTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.MaxCurriculaPerHackerTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void MaxCurriculaPerHackerTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.finderService.MaxCurriculaPerHacker();
		Assert.isTrue(res.doubleValue()==max);


	}



	//	RF.18.1	Minimum of the number of curricula per hacker

	@Test 
	public void minCurriculaPerHackerDriver() {
		Object testingData[][] = { { "admin", 1, null },// Positive
				{ "admin", 54, IllegalArgumentException.class },//non expected

				{ "hacker1", 4, IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			minCurriculaPerHackerTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minCurriculaPerHackerTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minCurriculaPerHackerTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minCurriculaPerHackerTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.finderService.MinCurriculaPerHacker();
		Assert.isTrue(res.intValue()==max);


	}


	//	RF.18.1	Deviation standard of the number of curricula per hacker

	@Test 
	public void avgCurriculaPerHackerDriver() {
		Object testingData[][] = { { "admin", 2.0, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected

				{ "hacker1", 3., IllegalArgumentException.class } //non authorized actor

		};

		for (int i = 0; i < testingData.length; i++) {
			avgCurriculaPerHackerTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgCurriculaPerHackerTemplate(String username,Double max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgCurriculaPerHackerTest(max);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgCurriculaPerHackerTest(Double max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res=this.finderService.AvgCurriculaPerHacker();
		Assert.isTrue(res.doubleValue()==max);


	}

	//	 The average, the minimum, the maximum, and the standard deviation of the
	//	 audit score of the positions stored in the system.
	
	@Test 
	public void statsAuditsPerPositionDriver() {
		Object testingData[][] = { { "admin", 10.0,3.0,6.5,2.5, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "hacker1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

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
		Object testingData[][] = { { "admin", 1.0,0.2,0.6333333333333333,0.3299831645537222, null },// Positive
				{ "admin", 1.8,1.9,1.9,1.8, IllegalArgumentException.class },//non expected

				{ "hacker1",1.8,1.9,1.9,1.8, IllegalArgumentException.class } //non authorized actor

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
	
	
	//	
	//	 The companies with the highest audit score.
	//	 The average salary offered by the positions that have the highest average
	//	 audit score.


	//	The minimum, the maximum, the average, and the standard deviation of the
	//	number of items per provider.

	//  The top-5 providers in terms of total number of items provided.


	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of sponsorships per provider.

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of sponsorships per position.

	//	The providers who have a number of sponsorships that is at least 10% above
	//	the average number of sponsorships per provider.

}
