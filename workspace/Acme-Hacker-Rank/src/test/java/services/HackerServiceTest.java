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
import domain.Hacker;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HackerServiceTest extends AbstractTest {

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

	// System under test ---------------------------------------
	@Autowired
	private Validator validator;

	@Autowired
	private HackerService hackerService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "hackerT", "hackerT", "hackerT", "hackerT", "ES12345678",
						"https://www.foto.com", "hackerT@hackerT.hackerT",
						"666666666", "c/ hackerT", "hackerT", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ "hackerT", null, "hackerT", "hackerT", "ES12345678",
						"https://www.foto.com", "hackerT@hackerT.hackerT",
						"666666666", "c/ hackerT", "hackerT", "VISA",
						"4111111111111111", 02, 22, 123, null },
				{ "hackerT", "hackerT", "hackerT", "hackerT", null,
						"https://www.foto.com", "hackerT@hackerT.hackerT",
						"666666666", "c/ hackerT", "hackerT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "hackerT", "hackerT", "hackerT", "hackerT", "ES12345678",
						null, "hackerT@hackerT.hackerT", "666666666",
						"c/ hackerT", "hackerT", "VISA", "4111111111111111",
						02, 22, 123, IllegalArgumentException.class },
				{ "hackerT", "hackerT", "hackerT", "hackerT", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ hackerT", "hackerT", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "hackerT", "hackerT", "hackerT", "hackerT", "ES12345678",
						"https://www.foto.com", "hackerT@hackerT.hackerT",
						"666666666", "c/ hackerT", null, null, null, null,
						null, null, ValidationException.class } };

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
			this.registerHacker(username, password, name, surname, VAT, photo,
					email, phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerHacker(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV) {

		RegisterFormObject hackerForm = new RegisterFormObject();
		Hacker newHacker = new Hacker();
		BindingResult binding = null;

		hackerForm.setUsername(username);
		hackerForm.setPassword(password);
		hackerForm.setPassConfirmation(password);
		hackerForm.setTermsAndConditions(true);
		hackerForm.setName(name);
		hackerForm.setSurname(surname);
		hackerForm.setVAT(VAT);
		hackerForm.setPhoto(photo);
		hackerForm.setEmail(email);
		hackerForm.setPhoneNumber(phoneNumber);
		hackerForm.setAddress(address);
		hackerForm.setHolder(holder);
		hackerForm.setMake(make);
		hackerForm.setNumber(number);
		hackerForm.setExpirationMonth(expirationMonth);
		hackerForm.setExpirationYear(expirationYear);
		hackerForm.setCVV(CVV);

		newHacker = this.hackerService.reconstruct(hackerForm, binding);

		this.validator.validate(newHacker, binding);
		this.hackerService.save(newHacker);
	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "hacker1", "hacker1", "hacker1", "ES12345678",
						"https://www.foto.com", "hacker1@hacker1.hacker1",
						"666666666", "c/ hacker1", "hacker1", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "hacker1", "hacker1", "ES12345678",
						"https://www.foto.com", "hacker1@hacker1.hacker1",
						"666666666", "c/ hacker1", "hacker1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "hacker1", "hacker1", "hacker1", null,
						"https://www.foto.com", "hacker1@hacker1.hacker1",
						"666666666", "c/ hacker1", "hacker1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "hacker1", "hacker1", "hacker1", "ES12345678", null,
						"hacker1@hacker1.hacker1", "666666666", "c/ hacker1",
						"hacker1", "VISA", "4111111111111111", 02, 22, 123,
						null },
				{ "hacker1", "hacker1", "hacker1", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ hacker1", "hacker1", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "hacker1", "hacker1", "hacker1", "ES12345678",
						"https://www.foto.com", "hacker1@hacker1.hacker1",
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

			this.editHacker(username, name, surname, VAT, photo, email,
					phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editHacker(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String holder, String make, String number,
			Integer expirationMonth, Integer expirationYear, Integer CVV) {

		EditionFormObject hackerForm = new EditionFormObject(
				this.hackerService.findByUsername(username));
		Hacker newHacker = new Hacker();
		BindingResult binding = null;

		hackerForm.setUsername(username);
		hackerForm.setName(name);
		hackerForm.setSurname(surname);
		hackerForm.setVAT(VAT);
		hackerForm.setPhoto(photo);
		hackerForm.setEmail(email);
		hackerForm.setPhoneNumber(phoneNumber);
		hackerForm.setAddress(address);
		hackerForm.setHolder(holder);
		hackerForm.setMake(make);
		hackerForm.setNumber(number);
		hackerForm.setExpirationMonth(expirationMonth);
		hackerForm.setExpirationYear(expirationYear);
		hackerForm.setCVV(CVV);

		newHacker = this.hackerService.reconstruct(hackerForm, binding);

		this.validator.validate(newHacker, binding);
		this.hackerService.save(newHacker);
	}

}