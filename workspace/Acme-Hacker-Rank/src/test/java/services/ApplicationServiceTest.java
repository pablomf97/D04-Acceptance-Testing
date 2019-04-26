package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Application;
import domain.Position;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest{
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private CurriculaService curriculaService;
	
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
	 * (RF9.3) Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them.
	 * 
	 * (RF10.1) Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them.
	 */
	
	/*
	 * ####################### TEST CREATE LEGAL RECORDS #######################
	 */

	@Test
	public void driverCreateApplication() {
		Object testingData[][] = {

		/* 1. Attribute 'hacker' Testing */

//		/* Test 1.1 ----------------------------------------------- */
//		{ "hacker1", "position1c1",  null
//		/*
//		 * 
//		 * There is no error expected here, a hacker creates an application with 
//		 * valid data
//		 */
//		},
//
//		/* Test 1.2 ----------------------------------------------- */
//		{ "company1", "position1c1", ConstraintViolationException.class
//		/*
//		 * 
//		 * Expected a Constraint Violation Exception because
//		 * a company is trying to create an application
//		 */
//		},
//		
//		/* Test 1.3 ----------------------------------------------- */
//		{ "", "position1c1", IllegalArgumentException.class
//		/*
//		 * 
//		 * Expected a Illegal Argument Exception because
//		 * the atribute hacker is empty
//		 */
//		},
//
//		/* 2. Attribute 'position' Testing */
//		/* Test 2.1 ----------------------------------------------- */
//		{ "hacker1", "position2c1",  null
//		/*
//		 * 
//		 * There is no error expected here, a hacker creates an application with 
//		 * valid data
//		 */
//		},
//
//		/* Test 2.2 ----------------------------------------------- */
//		{ "hacker1", "position3c1", ConstraintViolationException.class
//		/*
//		 * 
//		 * Expected a Illegal Argument Exception because
//		 * the position is set to draft
//		 */
//		},
//		/* Test 2.3 ----------------------------------------------- */
//		{ "hacker1", "", ConstraintViolationException.class
//		/*
//		 * 
//		 * Expected a Illegal Argument Exception because
//		 * the position is not specified
//		 */
//		},
		

		};

		for (int i = 0; i < testingData.length; i++) {
			templateCreateApplication((String) testingData[i][0],
					(int) super.getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);}
		}
		

	protected void templateCreateApplication(String username, int positionId,
		Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			Position aux;
			Application application = this.applicationService
					.create();
			
			aux = this.positionService.findOne(positionId);

			application.setPosition(aux);
			
			this.applicationService.save(application);

			this.applicationService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverSubmitApp() {
		final Object testingData[][] = {
			{
				"application2h1", "hacker1", "Explication test", "https://www.humblebundle.com/", "curricula6", null
			},// Positivo:submit
				// normal
			{
				"application2h1", "hacker1", "Explication test", "not a link", "curricula6", IllegalArgumentException.class
			},// Negativo:submit
				// no es un link
			{
				"application1h1", "hacker1", "Explication test", "https://www.humblebundle.com/", "curricula6", IllegalArgumentException.class
			},// Negativo:submit
				// no una application en status pending
			{
				"application2h1", "company1", "Explication test", "https://www.humblebundle.com/", "curricula6", IllegalArgumentException.class
			},// Negativo:submit
				// no es un hacker
			{
				"application2h1", "hacker1", "Explication test", "https://www.humblebundle.com/", "curricula2", IllegalArgumentException.class
			},// Negativo:submit
				// la curricula no es del hacker 1
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSubmitApp((int) super.getEntityId((String) testingData[i][0]), (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3],(int) super.getEntityId((String) testingData[i][4]),
					(Class<?>) testingData[i][5]);
	}

	private void templateSubmitApp(final int applicationId, final String username, 
			final String explanation, final String linkCode, int curriculaId,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			Application aux = this.applicationService.findOne(applicationId);

			aux.setExplanation(explanation);
			aux.setLinkCode(linkCode);
			aux.setCopyCurricula(this.curriculaService.findOne(curriculaId));
			
			this.applicationService.save(aux);
			this.applicationService.flush();
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverAcceptRejectApp() {
		final Object testingData[][] = {
			{
				"application4h1", "company1", "ACCEPTED",  null
			},// Positivo:accept/reject
				// normal
			{
				"application4h1", "hacker2", "REJECTED",  IllegalArgumentException.class
			},// Negativo:accept/reject
				// no es una company
			{
				"application4h1", "company2", "ACCEPTED",  IllegalArgumentException.class
			},// Negativo:accept/reject
				// no es la company autora de la oferta
			{
				"application4h1", "company1", "PENDING",  IllegalArgumentException.class
			},// Negativo:accept/reject
				// no el status que se intenta setear no es ni accepted ni rejected
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAcceptRejectApp((int) super.getEntityId((String) testingData[i][0]), (String) testingData[i][1],
					(String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void templateAcceptRejectApp(final int applicationId, final String username, 
			final String status, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			Application aux = this.applicationService.findOne(applicationId);

			aux.setStatus(status);
			
			this.applicationService.save(aux);
			this.applicationService.flush();
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
