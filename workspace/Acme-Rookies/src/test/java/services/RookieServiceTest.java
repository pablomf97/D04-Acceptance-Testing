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
import domain.Rookie;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RookieServiceTest extends AbstractTest {

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
	private RookieService rookieService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "rookieT", "rookieT", "rookieT", "rookieT", "ES12345678",
						"https://www.foto.com", "rookieT@rookieT.rookieT",
						"666666666", "c/ rookieT", "rookieT", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ "rookieT", null, "rookieT", "rookieT", "ES12345678",
						"https://www.foto.com", "rookieT@rookieT.rookieT",
						"666666666", "c/ rookieT", "rookieT", "VISA",
						"4111111111111111", 02, 22, 123, null },
				{ "rookieT", "rookieT", "rookieT", "rookieT", null,
						"https://www.foto.com", "rookieT@rookieT.rookieT",
						"666666666", "c/ rookieT", "rookieT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "rookieT", "rookieT", "rookieT", "rookieT", "ES12345678",
						null, "rookieT@rookieT.rookieT", "666666666",
						"c/ rookieT", "rookieT", "VISA", "4111111111111111",
						02, 22, 123, IllegalArgumentException.class },
				{ "rookieT", "rookieT", "rookieT", "rookieT", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ rookieT", "rookieT", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "rookieT", "rookieT", "rookieT", "rookieT", "ES12345678",
						"https://www.foto.com", "rookieT@rookieT.rookieT",
						"666666666", "c/ rookieT", null, null, null, null,
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
			this.registerRookie(username, password, name, surname, VAT, photo,
					email, phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerRookie(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV) {

		RegisterFormObject rookieForm = new RegisterFormObject();
		Rookie newRookie = new Rookie();
		BindingResult binding = null;

		rookieForm.setUsername(username);
		rookieForm.setPassword(password);
		rookieForm.setPassConfirmation(password);
		rookieForm.setTermsAndConditions(true);
		rookieForm.setName(name);
		rookieForm.setSurname(surname);
		rookieForm.setVAT(VAT);
		rookieForm.setPhoto(photo);
		rookieForm.setEmail(email);
		rookieForm.setPhoneNumber(phoneNumber);
		rookieForm.setAddress(address);
		rookieForm.setHolder(holder);
		rookieForm.setMake(make);
		rookieForm.setNumber(number);
		rookieForm.setExpirationMonth(expirationMonth);
		rookieForm.setExpirationYear(expirationYear);
		rookieForm.setCVV(CVV);

		newRookie = this.rookieService.reconstruct(rookieForm, binding);

		this.validator.validate(newRookie, binding);
		this.rookieService.save(newRookie);
	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "rookie1", "rookie1", "rookie1", "ES12345678",
						"https://www.foto.com", "rookie1@rookie1.rookie1",
						"666666666", "c/ rookie1", "rookie1", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "rookie1", "rookie1", "ES12345678",
						"https://www.foto.com", "rookie1@rookie1.rookie1",
						"666666666", "c/ rookie1", "rookie1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "rookie1", "rookie1", "rookie1", null,
						"https://www.foto.com", "rookie1@rookie1.rookie1",
						"666666666", "c/ rookie1", "rookie1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "rookie1", "rookie1", "rookie1", "ES12345678", null,
						"rookie1@rookie1.rookie1", "666666666", "c/ rookie1",
						"rookie1", "VISA", "4111111111111111", 02, 22, 123,
						null },
				{ "rookie1", "rookie1", "rookie1", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ rookie1", "rookie1", "VISA", "4111111111111111",
						02, 22, 123, NullPointerException.class },
				{ "rookie1", "rookie1", "rookie1", "ES12345678",
						"https://www.foto.com", "rookie1@rookie1.rookie1",
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

			this.editRookie(username, name, surname, VAT, photo, email,
					phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editRookie(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String holder, String make, String number,
			Integer expirationMonth, Integer expirationYear, Integer CVV) {

		EditionFormObject rookieForm = new EditionFormObject(
				this.rookieService.findByUsername(username));
		Rookie newRookie = new Rookie();
		BindingResult binding = null;

		rookieForm.setUsername(username);
		rookieForm.setName(name);
		rookieForm.setSurname(surname);
		rookieForm.setVAT(VAT);
		rookieForm.setPhoto(photo);
		rookieForm.setEmail(email);
		rookieForm.setPhoneNumber(phoneNumber);
		rookieForm.setAddress(address);
		rookieForm.setHolder(holder);
		rookieForm.setMake(make);
		rookieForm.setNumber(number);
		rookieForm.setExpirationMonth(expirationMonth);
		rookieForm.setExpirationYear(expirationYear);
		rookieForm.setCVV(CVV);

		newRookie = this.rookieService.reconstruct(rookieForm, binding);

		this.validator.validate(newRookie, binding);
		this.rookieService.save(newRookie);
	}

}