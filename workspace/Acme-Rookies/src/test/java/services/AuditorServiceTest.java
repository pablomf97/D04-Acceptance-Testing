package services;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import utilities.AbstractTest;
import domain.Auditor;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorServiceTest extends AbstractTest{
	
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
	 * Coverage of the total project (%):7.8
	 * 
	 * 
	 * Coverage of the total project (lines of codes): 3072
	 */

	// System under test ---------------------------------------
	@Autowired
	private Validator validator;

	@Autowired
	private AuditorService auditorService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as an auditor. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "auditorT", "auditorT", "auditorT", "auditorT", "ES12345678",
						"https://www.foto.com", "auditorT@auditorT.auditorT",
						"666666666", "c/ auditorT", "auditorT", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ "auditorT", null, "auditorT", "auditorT", "ES12345678",
						"https://www.foto.com", "auditorT@auditorT.auditorT",
						"666666666", "c/ auditorT", "auditorT", "VISA",
						"4111111111111111", 02, 22, 123, NullPointerException.class },
				{ "auditorT", "auditorT", "auditorT", "auditorT", null,
						"https://www.foto.com", "auditorT@auditorT.auditorT",
						"666666666", "c/ auditorT", "auditorT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "auditorT", "auditorT", "auditorT", "auditorT", "ES12345678",
						null, "auditorT@auditorT.auditorT", "666666666",
						"c/ auditorT", "auditorT", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "auditorT", "auditorT", "auditorT", "auditorT", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ auditorT", "auditorT", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "auditorT", "auditorT", "auditorT", "auditorT", "ES12345678",
						"https://www.foto.com", "auditorT@auditorT.auditorT",
						"666666666", "c/ auditorT", null, null, null, null,
						null, null, NullPointerException.class } };

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3],
					(String) testingData[i][4], (String) testingData[i][5],
					(String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9],
					(String) testingData[i][10], (String) testingData[i][11],
					(Integer) testingData[i][12], (Integer) testingData[i][13],
					(Integer) testingData[i][14], (Class<?>) testingData[i][15]);
		}
	}

	protected void template(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.registerAuditor(username, password, name, surname, VAT, photo,
					email, phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerAuditor(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV) {

		RegisterFormObject auditorForm = new RegisterFormObject();
		Auditor newAuditor = new Auditor();
		BindingResult binding = null;

		auditorForm.setUsername(username);
		auditorForm.setPassword(password);
		auditorForm.setPassConfirmation(password);
		auditorForm.setTermsAndConditions(true);
		auditorForm.setName(name);
		auditorForm.setSurname(surname);
		auditorForm.setVAT(VAT);
		auditorForm.setPhoto(photo);
		auditorForm.setEmail(email);
		auditorForm.setPhoneNumber(phoneNumber);
		auditorForm.setAddress(address);
		auditorForm.setHolder(holder);
		auditorForm.setMake(make);
		auditorForm.setNumber(number);
		auditorForm.setExpirationMonth(expirationMonth);
		auditorForm.setExpirationYear(expirationYear);
		auditorForm.setCVV(CVV);

		newAuditor = this.auditorService.reconstruct(auditorForm, binding);

		this.validator.validate(newAuditor, binding);
		this.auditorService.save(newAuditor);
	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "auditor1", "auditor1", "auditor1", "ES12345678",
						"https://www.foto.com", "auditor1@auditor1.auditor1",
						"666666666", "c/ auditor1", "auditor1", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "auditor1", "auditor1", "ES12345678",
						"https://www.foto.com", "auditor1@auditor1.auditor1",
						"666666666", "c/ auditor1", "auditor1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "auditor1", "auditor1", "auditor1", null,
						"https://www.foto.com", "auditor1@auditor1.auditor1",
						"666666666", "c/ auditor1", "auditor1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "auditor1", "auditor1", "auditor1", "ES12345678", null,
						"auditor1@auditor1.auditor1", "666666666", "c/ auditor1",
						"auditor1", "VISA", "4111111111111111", 02, 22, 123,
						null },
				{ "auditor1", "auditor1", "auditor1", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ auditor1", "auditor1", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "auditor1", "auditor1", "auditor1", "ES12345678",
						"https://www.foto.com", "auditor1@auditor1.auditor1",
						"666666666", "c/ admin", null, null, null, null, null,
						null, ValidationException.class } };

		for (int i = 0; i < editionTestingData.length; i++) {
			templateEdit((String) editionTestingData[i][0],
					(String) editionTestingData[i][1],
					(String) editionTestingData[i][2],
					(String) editionTestingData[i][3],
					(String) editionTestingData[i][4],
					(String) editionTestingData[i][5],
					(String) editionTestingData[i][6],
					(String) editionTestingData[i][7],
					(String) editionTestingData[i][8],
					(String) editionTestingData[i][9],
					(String) editionTestingData[i][10],
					(Integer) editionTestingData[i][11],
					(Integer) editionTestingData[i][12],
					(Integer) editionTestingData[i][13],
					(Class<?>) editionTestingData[i][14]);
		}
	}

	protected void templateEdit(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String holder, String make, String number,
			Integer expirationMonth, Integer expirationYear, Integer CVV,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.editAuditor(username, name, surname, VAT, photo, email,
					phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editAuditor(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String holder, String make, String number,
			Integer expirationMonth, Integer expirationYear, Integer CVV) {

		EditionFormObject auditorForm = new EditionFormObject(
				this.auditorService.findByUsername(username));
		Auditor newAuditor = new Auditor();
		BindingResult binding = null;

		auditorForm.setUsername(username);
		auditorForm.setName(name);
		auditorForm.setSurname(surname);
		auditorForm.setVAT(VAT);
		auditorForm.setPhoto(photo);
		auditorForm.setEmail(email);
		auditorForm.setPhoneNumber(phoneNumber);
		auditorForm.setAddress(address);
		auditorForm.setHolder(holder);
		auditorForm.setMake(make);
		auditorForm.setNumber(number);
		auditorForm.setExpirationMonth(expirationMonth);
		auditorForm.setExpirationYear(expirationYear);
		auditorForm.setCVV(CVV);

		newAuditor = this.auditorService.reconstruct(auditorForm, binding);

		this.validator.validate(newAuditor, binding);
		this.auditorService.save(newAuditor);
	}
	
}
