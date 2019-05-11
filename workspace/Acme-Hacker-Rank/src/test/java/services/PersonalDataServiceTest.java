
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;
import domain.PersonalData;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private PersonalDataService	personalDataService;


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
		final Object testingData[][] = {

			/* Test 1.1 ----------------------------------------------- */
			{
				"rookie1", "https://github.com/fsfd", "https://www.linkedin.com/in/fswe12fd", "Carlos Plaza", "statement1", "618922568", null
			//Positive

			},

			/* Test 1.2 ----------------------------------------------- */
			{
				"rookie1", null, "https://www.linkedin.com/in/fswe12fd", "Carlos Plaza", "statement1", "618922568", IllegalArgumentException.class
			//Negative: attribute githubProfile is null

			},

			/* Test 1.3 ----------------------------------------------- */
			{
				"rookie1", "url", "url", "Carlos Plaza", "statement1", "618922568", ConstraintViolationException.class
			//Negative: format error url

			},

			/* Test 2.1 ----------------------------------------------- */
			{
				null, "https://github.com/fsfd", "https://www.linkedin.com/in/fswe12fd", "Carlos Plaza", "statement1", "618922568", IllegalArgumentException.class
			//Negative: actor null

			},

			/* Test 2.2 ----------------------------------------------- */
			{
				"company1", "https://github.com/fsfd", "https://www.linkedin.com/in/fswe12fd", "Carlos Plaza", "statement1", "618922568", ClassCastException.class
			//Negative: actor non authorized

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePersonalData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void templateCreatePersonalData(final String username, final String githubProfile, final String linkedIn, final String fullName, final String statement, final String phoneNumber, final Class<?> expected) {
		Rookie principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);

			principal = (Rookie) this.actorService.findByPrincipal();

			final Collection<Curricula> principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());

			final PersonalData pd = this.personalDataService.create();

			pd.setGithubProfile(githubProfile);
			pd.setLinkedIn(linkedIn);
			pd.setFullName(fullName);
			pd.setStatement(statement);
			pd.setPhoneNumber(phoneNumber);

			this.personalDataService.save(pd, principalCurriculas.iterator().next().getId());

			this.personalDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * ####################### TEST EDIT PersonalData#######################
	 */

	@Test
	public void driverEditPersonalData() {
		final Object testingData[][] = {

			/* Test 1.1 ----------------------------------------------- */
			{
				"rookie2", "https://github.com/fsfd", "https://www.linkedin.com/in/fswe12fd", "Jesús Plaza", "statement1", "618922568", null
			//Positive
			},

			/* Test 1.3 ----------------------------------------------- */
			{
				"rookie2", "url", "https://www.linkedin.com/in/fswe12fd", "Jesús Plaza", "statement1", "618922568", ConstraintViolationException.class
			//Negative: formatt error url

			}, {
				"company1", "https://github.com/fsfd", "https://www.linkedin.com/in/fswe12fd", "Jesús Plaza", "statement1", "618922568", ClassCastException.class
			// Negative: actor non authorized

			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPersonalData((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void templateEditPersonalData(final String username, final String githubProfile, final String linkedIn, final String fullName, final String statement, final String phoneNumber, final Class<?> expected) {
		Rookie principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);
			principal = (Rookie) this.actorService.findByPrincipal();

			final Collection<Curricula> principalCurriculas = this.curriculaService.getCurriculasByRookie(principal.getId());
			final PersonalData pd = principalCurriculas.iterator().next().getPersonalData();

			pd.setGithubProfile(githubProfile);
			pd.setLinkedIn(linkedIn);
			pd.setFullName(fullName);
			pd.setStatement(statement);
			pd.setPhoneNumber(phoneNumber);

			this.personalDataService.save(pd, principalCurriculas.iterator().next().getId());

			this.personalDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
