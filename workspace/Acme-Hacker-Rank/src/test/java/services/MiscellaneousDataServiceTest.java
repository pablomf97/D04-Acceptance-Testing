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

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest{



	@Autowired
	private ActorService actorService;

	@Autowired 
	private CurriculaService curriculaService;

	@Autowired
	private MiscellaneousDataService miscellaneousDataService;

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
	 * Coverage of the total project (%): 6.0%
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 1451
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage MiscellaneousData, which includes listing, displaying,
	 * creating, updating, and deleting its records.
	 */

	/*
	 * ####################### TEST CREATE MISCELLANEOUSDATA #######################
	 */

	@Test
	public void driverCreateMiscellaneousData() {
		Object testingData[][] = {

				

				/* Test 1.1 ----------------------------------------------- */
				{ "hacker1", "text", "https://www.imgur.com/djkskdb", null //Positive

				},

				/* Test 1.2 ----------------------------------------------- */
				{ "hacker1", null, "https://www.imgur.com/djkskdb", IllegalArgumentException.class //Negative: attribute text is null

				},

				/* Test 1.3 ----------------------------------------------- */
				{ "hacker1", "text", "url", ConstraintViolationException.class//Negative: format error url

				},



				/* Test 2.1 ----------------------------------------------- */
				{ null, "title", "description", IllegalArgumentException.class //Negative: actor null

				},

				/* Test 2.2 ----------------------------------------------- */
				{ "company1", "title", "description", ClassCastException.class //Negative: actor non authorized

				},



		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateMiscellaneousData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateCreateMiscellaneousData(String username,
			String text, String attachement, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);

			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

			MiscellaneousData miscellaneousData = this.miscellaneousDataService
					.create();

			miscellaneousData.setText(text);
			miscellaneousData.setAttachements(attachement);

			this.miscellaneousDataService.save(miscellaneousData,principalCurriculas.iterator().next().getId());

			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}



	
	
	/*
	 * ####################### TEST EDIT  MISCELLANEOUSDATA #######################
	 */

	@Test
	public void driverEditMiscellaneousData() {
		Object testingData[][] = {



				/* Test 1.1 ----------------------------------------------- */
				{ "hacker3", "text", "https://gadsy.com/auifd"
					, null //Positive

				},

				/* Test 1.3 ----------------------------------------------- */
				{ "hacker3", "miscellaneousRecord1", "url",
					ConstraintViolationException.class //Negative: formatt error url

				},
				{ "company1", "miscellaneousRecord1", "https://gadsy.com/auifd",
					ClassCastException.class // Negative: actor non authorized

				}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditMiscellaneousData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateEditMiscellaneousData(String username,
			 String text, String attachements, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);
			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());
			MiscellaneousData miscellaneousRecord = principalCurriculas.iterator().next().getMiscellaneousData().iterator().next();

			miscellaneousRecord.setText(text);
			miscellaneousRecord.setAttachements(attachements);

			this.miscellaneousDataService.save(miscellaneousRecord,principalCurriculas.iterator().next().getId());

			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	/*
	 * ####################### TEST DELETE MISCELLANEOUSDATA #######################
	 */

	@Test
	public void driverDeleteMiscellaneousData() {
		Object testingData[][] = {

				


			
				{ "company1", "miscellaneousData5", ClassCastException.class//Negative: Actor non authorized
			
				},

				/* Test 2.3 ----------------------------------------------- */
				{ null, "miscellaneousData5", IllegalArgumentException.class //Negative: Actor null
			
					
				}, /* Test 1.1 ----------------------------------------------- */
				{ "hacker5", "miscellaneousData5", null //Positive
			
				} };

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMiscellaneousData((String) testingData[i][0],
					(int) super.getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
	}

	protected void templateDeleteMiscellaneousData(String username,
			int miscId, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			MiscellaneousData miscellaneousRecord = this.miscellaneousDataService
					.findOne(miscId);

			this.miscellaneousDataService.delete(miscellaneousRecord);

			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}



}
