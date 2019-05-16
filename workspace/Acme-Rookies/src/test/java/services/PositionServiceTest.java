
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import utilities.AbstractTest;
import domain.Actor;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;


	//Sentence coverage 55.1%
	@Test
	public void driver() {
		Collection<Problem> problems = new ArrayList<>();
		final Collection<Problem> problems2 = new ArrayList<>();

		problems2.add(this.problemService.findOne(this.getEntityId("problem2c1")));
		problems2.add(this.problemService.findOne(this.getEntityId("problem1c3")));

		final Object testingData[][] = {
			{
				"Test 1", "Test 1", "Test", "Test", 100.00, "prue-7890", "test", "company1", true, false, null
			},// Positivo:crear
				// normal
				//
			{
				"", "Test 1", "Test", "Test", 100.00, "prue-7892", "test", "company1", true, false, NullPointerException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notBlank
			{
				null, "Test 1", "Test", "Test", 100.00, "prue-7893", "test", "company1", true, false, NullPointerException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notNull
			{
				"Test 1", null, "Test", "Test", null, "prue-7895", null, "company1", true, false, NullPointerException.class
			},// Negativo->RN:nombre,
				// datos nulos
			{
				"Test 1", "Test 1", "Test", "Test", 100.00, "prue-7896", "test", "company100", true, false, IllegalArgumentException.class
			},// Negativo->Intento de
				// registro logueado
				// como un actor no
				// existente en la
				// DB
			{

				"Test 1", "Test 1", "Test", "Test", -100.00, "prue-7897", "test", "company1", true, false, NullPointerException.class
			},// Negativo->salario negativo
			{

				"Test 1", "Test 1", "Test", "Test", 100.00, "prue-7898", "test", "company1", false, false, IllegalArgumentException.class
			},// Negativo->final con menos de 2 problemas

			{

				"Test 1", "Test 1", "Test", "Test", 100.00, "prue-7899", "test", "company2", false, false, IllegalArgumentException.class
			},// Negativo->guardar con problemas que no le pertenecen
		};

		for (int i = 0; i < testingData.length; i++) {
			final Date d = new Date();
			if (i == 8)
				problems = problems2;
			this.template((String) testingData[i][0], (String) testingData[i][1], d, (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				problems, (Boolean) testingData[i][8], (Boolean) testingData[i][9], (Class<?>) testingData[i][10]);
		}
	}
	private void template(final String title, final String description, final Date date, final String profileRequired, final String technologiesRequired, final Double salary, final String ticker, final String skillsRequired, final String company,
		final Collection<Problem> problems, final Boolean isDraft, final Boolean isCancelled, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);

			this.saveProblem(title, description, date, profileRequired, technologiesRequired, salary, ticker, skillsRequired, company, problems, isDraft, isCancelled);
			this.unauthenticate();
		} catch (final Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	private void saveProblem(final String title, final String description, final Date deadline, final String profileRequired, final String technologiesRequired, final Double salary, final String ticker, final String skillsRequired, final String company,
		final Collection<Problem> problems, final Boolean isDraft, final Boolean isCancelled) {
		Actor principal;
		principal = this.actorService.findByPrincipal();
		final Position result = this.positionService.create(principal);
		result.setDeadline(deadline);
		result.setDescription(description);
		result.setIsDraft(isDraft);
		result.setIsCancelled(isCancelled);
		result.setProfileRequired(profileRequired);
		result.setProblems(problems);
		result.setSalary(salary);
		result.setTicker(ticker);
		result.setSkillsRequired(skillsRequired);
		result.setTechnologiesRequired(technologiesRequired);
		result.setTitle(title);

		final BindingResult binding = null;
		this.validator.validate(result, binding);
		this.positionService.save(result);
		this.positionService.flush();

	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				"position3c1", "company1", null
			},// Positivo:borrar
				// normal
			{
				"position1c2", "company1", IllegalArgumentException.class
			},// Negativo:borrar
				// no pertenece
			{
				"position1c1", "company2", IllegalArgumentException.class
			},// Negativo:borrar
				// usado

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void template2(final String id, final String company, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId(id);

			this.deletePosition(idEntity);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void deletePosition(final Integer idEntity) {
		final Position p = this.positionService.findOne(idEntity);

		this.positionService.delete(p);
		this.positionService.flush();

	}

	@Test
	public void driver3() {
		final Object testingData[][] = {

			{
				"position3c1", "company1", "title test", null
			},// Positivo:editar
				// normal
			{
				"position3c1", "company2", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no pertenece
			{
				"position1c1", "company1", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no borrador
		};

		for (int i = 0; i < testingData.length; i++)
			this.template3((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void template3(final String id, final String company, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId(id);

			this.editProblem(idEntity, title);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editProblem(final Integer idEntity, final String title) {
		final Position p = this.positionService.findOne(idEntity);
		p.setTitle(title);

		this.positionService.save(p);
		this.positionService.flush();

	}

	@Test
	public void driver4() {
		final Object testingData[][] = {

			{
				"company1", null
			},// Positivo:editar
				// normal
			{
				"company2", null
			},// Positivo:editar
				// no pertenece
		};

		for (int i = 0; i < testingData.length; i++)
			this.template4((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void template4(final String company, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId("company1");

			this.listProblem(idEntity);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void listProblem(final Integer idEntity) {

		final Collection<Position> p = this.positionService.findByOwner(this.actorService.findOne(idEntity));
		this.positionService.flush();

	}

	@Test
	public void driver5() {
		final Object testingData[][] = {

			{
				"position3c1", "company1", null
			},// Positivo:editar
				// normal
			{
				"position3c1", "company2", null
			},// Positivo:editar
				// no pertenece
		};

		for (int i = 0; i < testingData.length; i++)
			this.template5((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void template5(final String position, final String company, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);
			final Integer idEntity = this.getEntityId(position);

			this.displayProblem(idEntity);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void displayProblem(final Integer idEntity) {

		final Position p = this.positionService.findOne(idEntity);
		this.positionService.flush();

	}

}
