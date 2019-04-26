package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest  extends AbstractTest{

	
	@Autowired
	private ActorService actorService;
	
	@Autowired 
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalDataService personalDataService;
	
	//Caso de uso 17.1
	

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
	 * Coverage of the total project (%): 5.7%
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 1383
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage PersonalData, which includes listing, displaying,
	 * creating, updating, and deleting its records.
	 */

	/*
	 * ####################### TEST CREATE PersonalData#######################
	 */

	@Test
	public void driverCreatePersonalData() {
		Object testingData[][] = {

				

				/* Test 1.1 ----------------------------------------------- */
				{ "hacker1", "https://github.com/fsfd", "https://linkedln.com/fswe12fd","Carlos Plaza","statement1","618922568", null //Positive

				},

				/* Test 1.2 ----------------------------------------------- */
				{ "hacker1", null, "https://linkedln.com/fswe12fd","Carlos Plaza","statement1","618922568", IllegalArgumentException.class //Negative: attribute githubProfile is null

				},

				/* Test 1.3 ----------------------------------------------- */
				{ "hacker1","url", "url","Carlos Plaza","statement1","618922568", ConstraintViolationException.class//Negative: format error url

				},



				/* Test 2.1 ----------------------------------------------- */
				{ null,"https://github.com/fsfd", "https://linkedln.com/fswe12fd","Carlos Plaza","statement1","618922568", IllegalArgumentException.class //Negative: actor null

				},

				/* Test 2.2 ----------------------------------------------- */
				{ "company1","https://github.com/fsfd", "https://linkedln.com/fswe12fd","Carlos Plaza","statement1","618922568", ClassCastException.class //Negative: actor non authorized

				},



		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePersonalData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					 (String) testingData[i][5],
					(Class<?>) testingData[i][6]);
	}

	protected void templateCreatePersonalData(String username,
			String githubProfile, String linkedIn, String fullName, String statement, String phoneNumber, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);

			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

			PersonalData pd= this.personalDataService
					.create();

			pd.setGithubProfile(githubProfile);
			pd.setLinkedIn(linkedIn);
			pd.setFullName(fullName);
			pd.setStatement(statement);
			pd.setPhoneNumber(phoneNumber);

			this.personalDataService.save(pd,principalCurriculas.iterator().next().getId());

			this.personalDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}



	
	
	/*
	 * ####################### TEST EDIT  PersonalData#######################
	 */

	@Test
	public void driverEditPersonalData() {
		Object testingData[][] = {



				/* Test 1.1 ----------------------------------------------- */
				{ "hacker2", "https://github.com/fsfd", "https://linkedln.com/fswe12fd","Jesús Plaza","statement1","618922568", null //Positive
				},

				/* Test 1.3 ----------------------------------------------- */
				{ "hacker2", "url", "https://linkedln.com/fswe12fd","Jesús Plaza","statement1","618922568",
					ConstraintViolationException.class //Negative: formatt error url

				},
				{ "company1", "https://github.com/fsfd", "https://linkedln.com/fswe12fd","Jesús Plaza","statement1","618922568",
					ClassCastException.class // Negative: actor non authorized

				}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPersonalData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					 (String) testingData[i][5],
					(Class<?>) testingData[i][6]);
	}

	protected void templateEditPersonalData(String username,
			String githubProfile, String linkedIn, String fullName, String statement, String phoneNumber, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);
			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());
			PersonalData pd = principalCurriculas.iterator().next().getPersonalData();
			
			pd.setGithubProfile(githubProfile);
			pd.setLinkedIn(linkedIn);
			pd.setFullName(fullName);
			pd.setStatement(statement);
			pd.setPhoneNumber(phoneNumber);

			this.personalDataService.save(pd,principalCurriculas.iterator().next().getId());

			this.personalDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	

}
