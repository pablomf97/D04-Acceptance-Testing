
package services;

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
import domain.Company;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;

	@Autowired
	private ProblemService	problemService;

	private String			title;
	private String			statement;
	private String			optionalHint;
	private String			attachments;
	private Company			company;
	private Boolean			isDraft;


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				"Test 1", "Test 1", "Test 1", "https://www.almaobservatory.org/wp-content/uploads/2018/06/20180620-Dong-et-al-header-680x311.jpg", "company1", true, null
			},// Positivo:crear
				// normal
			{
				"Test 1", "Test 1", null, "https://www.almaobservatory.org/wp-content/uploads/2018/06/20180620-Dong-et-al-header-680x311.jpg", "company1", true, null
			},// Positivo:campos no
				// obligatorios nulos
			{
				"", "Test 1", "Test 1", "https://www.almaobservatory.org/wp-content/uploads/2018/06/20180620-Dong-et-al-header-680x311.jpg", "company1", true, NullPointerException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notBlank
			{
				null, "Test 1", "Test 1", "https://www.almaobservatory.org/wp-content/uploads/2018/06/20180620-Dong-et-al-header-680x311.jpg", "company1", true, NullPointerException.class
			},// Negativo->RN:Title
				// no puedes ser
				// notNull
			{
				"Test 1", "Test 1", "Test 1", "asdf", "company1", true, NullPointerException.class
			},// Negativo->RN:Url
				// no
				// tiene
				// el
				// formato
				// adecuado
			{
				null, null, "Test 1", null, "company1", null, NullPointerException.class
			},// Negativo->RN:nombre,
				// datos no nulos
			{
				"Test 1", "Test 1", "Test 1", "https://www.almaobservatory.org/wp-content/uploads/2018/06/20180620-Dong-et-al-header-680x311.jpg", "company100", true, IllegalArgumentException.class
			},// Negativo->Intento de
				// registro logueado
				// como un actor no
				// existente en la
				// DB

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	private void template(final String title, final String statement, final String optionalHint, final String attachments, final String company, final Boolean isDraft, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.authenticate(company);

			this.saveProblem(title, statement, optionalHint, attachments, isDraft);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void saveProblem(final String title, final String statement, final String optionalHint, final String attachments, final Boolean isDraft) {
		Actor principal;
		principal = this.actorService.findByPrincipal();
		final Problem result = this.problemService.create(principal);
		final BindingResult binding = null;

		result.setAttachments(attachments);
		result.setIsDraft(isDraft);
		result.setOptionalHint(optionalHint);
		result.setStatement(statement);
		result.setTitle(title);

		this.validator.validate(result, binding);
		this.problemService.checkSplitPictures(result.getAttachments(), binding);
		this.problemService.save(result);
		this.problemService.flush();

	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			{
				"problem1c3", "company3", null
			},// Positivo:borrar
				// normal
			{
				"problem2c1", "company2", IllegalArgumentException.class
			},// Negativo:borrar
				// no pertenece
			{
				"problem2c1", "company1", IllegalArgumentException.class
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

			this.deleteProblem(idEntity);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void deleteProblem(final Integer idEntity) {
		final Problem p = this.problemService.findOne(idEntity);

		this.problemService.delete(p);
		this.problemService.flush();

	}

	@Test
	public void driver3() {
		final Object testingData[][] = {

			{
				"problem1c3", "company3", "title test", null
			},// Positivo:editar
				// normal
			{
				"problem2c1", "company2", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no pertenece
			{
				"problem2c1", "company1", "title test", IllegalArgumentException.class
			},// Negativo:editar
				// no borrador
		};

		for (int i = 0; i < testingData.length; i++)
			this.template3((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][1], (Class<?>) testingData[i][3]);
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
		final Problem p = this.problemService.findOne(idEntity);
		p.setTitle(title);

		this.problemService.save(p);
		this.problemService.flush();

	}

}
