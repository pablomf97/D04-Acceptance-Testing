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
public class CurriculaServiceTest extends AbstractTest{
	
	@Autowired
	private ActorService actorService;
	
	@Autowired 
	private CurriculaService curriculaService;
	
	
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
	 * Coverage of the total project (%):3.9%
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 953
	 */

	
	//TEST CREATE CURRICULA //
	
	@Test
	public void driverCreateCV() {
		Object testingData[][] = {

				

				/* Test 1.1 ----------------------------------------------- */
				{ "hacker1", null //Positive
					
				},
				{ "company1", ClassCastException.class //Negative:Actor non authorized
					
				},

			



		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatecv((String) testingData[i][0],
					
					(Class<?>) testingData[i][1]);
	}

	protected void templateCreatecv(String username,
			Class<?> expected) {
	
		Class<?> caught = null;

		try {
			this.authenticate(username);

			

			Curricula cv = this.curriculaService.create();

			

			this.curriculaService.save(cv);

		

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	
	///TEST DELETE CURRICULA///
	@Test
	public void driverDeleteCV() {
		Object testingData[][] = {

				

				/* Test 1.1 ----------------------------------------------- */
				{ "hacker1", null //Positive
					
				},
				{ "company1", ClassCastException.class //Negative:Actor non authorized
					
				},

			



		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreatecv((String) testingData[i][0],
					
					(Class<?>) testingData[i][1]);
	}

	protected void templateDeletecv(String username,
			Class<?> expected) {
		Hacker principal;
		Class<?> caught = null;

		try {
			this.authenticate(username);

			principal = (Hacker) this.actorService.findByPrincipal();

			Collection<Curricula>principalCurriculas = this.curriculaService.getCurriculasByHacker(principal.getId());
			
			this.curriculaService.delete(principalCurriculas.iterator().next());
			
			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}


}
