package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Curricula;

import domain.Hacker;
import domain.PositionData;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionDataServiceTest  extends AbstractTest{

	
	@Autowired
	private ActorService actorService;
	
	@Autowired 
	private CurriculaService curriculaService;
	
	@Autowired
	private PositionDataService positionDataService;
	
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
	 * Coverage of the total project (%): 6.7%
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 1633
	 */

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * Manage PositionData, which includes listing, displaying,
	 * creating, updating, and deleting its records.
	 */

	/*
	 * ####################### TEST CREATE POSITIONDATA #######################
	 */

	@Test
	public void driverCreatePositionData() {
		Object testingData[][] = {

				

				
				{ "hacker1", "title1", "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), null //Positive

				},
				{ "hacker1", null, "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), IllegalArgumentException.class //Negative: degree dont be null

				},
				{ null, "title1", "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), IllegalArgumentException.class //Negative: actor is null

				},
				{ "company1", "title1", "description1",new GregorianCalendar(2041, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), ClassCastException.class //Negative: actor non authorized

				}



		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatePositionData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Date) testingData[i][3],(Date) testingData[i][4],
					(Class<?>) testingData[i][5]);
	}

	protected void templateCreatePositionData(String username,
			String title, String description,  Date startDate, Date endDate, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);

			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());

			PositionData ed = this.positionDataService
					.create();

			ed.setTitle(title);
			ed.setDescription(description);
			ed.setStartDate(startDate);
			ed.setEndDate(endDate);
			

			this.positionDataService.save(ed,principalCurriculas.iterator().next().getId());

			this.positionDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}



	
	
	/*
	 * ####################### TEST EDIT  POSITIONDATA #######################
	 */

	@Test
	public void driverEditPositionData() {
		Object testingData[][] = {



				/* Test 1.1 ----------------------------------------------- */
				{ "hacker1", "title1", "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), null //Positive

				},

				/* Test 1.3 ----------------------------------------------- */
				{ "hacker1", "title1", "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), null //Positive

				},
				{ "company1", "title1", "description1",new GregorianCalendar(2010, Calendar.JANUARY, 1)
				.getTime(),new GregorianCalendar(2011, Calendar.JANUARY, 1)
				.getTime(), ClassCastException.class //Negative:actor non authorized

				}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditPositionData((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Date) testingData[i][3],(Date) testingData[i][4],
					(Class<?>) testingData[i][5]);
	}

	protected void templateEditPositionData(String username,
			 String title, String description, Date startDate, Date endDate, Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);
			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());
			PositionData ed = principalCurriculas.iterator().next().getPositionData().iterator().next();

			ed.setTitle(title);
			ed.setDescription(description);
			ed.setStartDate(startDate);
			ed.setEndDate(endDate);

			this.positionDataService.save(ed,principalCurriculas.iterator().next().getId());

			this.positionDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	/*
	 * ####################### TEST DELETE POSITIONDATA #######################
	 */

	@Test
	public void driverDeletePositionData() {
		Object testingData[][] = {

				


			
				{ "company1", "positionData1", ClassCastException.class//Negative: Actor non authorized
			
				},

				/* Test 2.3 ----------------------------------------------- */
				{ null, "positionData1", IllegalArgumentException.class //Negative: Actor null
			
					
				}, /* Test 1.1 ----------------------------------------------- */
				{ "hacker1", "positionData1", null //Positive
			
				} };

		for (int i = 0; i < testingData.length; i++)
			this.templateDeletePositionData((String) testingData[i][0],
					(int) super.getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
	}

	protected void templateDeletePositionData(String username,
			int edId, Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			PositionData ed = this.positionDataService
					.findOne(edId);

			this.positionDataService.delete(ed);

			this.positionDataService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
}
