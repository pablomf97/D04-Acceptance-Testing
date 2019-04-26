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
import domain.Administrator;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

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
	private AdministratorService administratorService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driverRegister() {
		Object registerTestingData[][] = {
				/* Positive case */
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com",
						"adminT@adminT.adminT", "666666666", "c/ adminT",
						"adminT", "VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative: invalid admin */
				{ null, "adminT", "adminT", "adminT", "adminT", "ES12345678",
						"https://www.foto.com", "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				/* Negative cases: invalid data */
				{ "admin", "adminT", "adminT", "adminT", "adminT", null,
						"https://www.foto.com", "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", null, "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com", null,
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com",
						"adminT@adminT.adminT", "666666666", "c/ adminT", null,
						null, null, null, null, null, ValidationException.class } };

		for (int i = 0; i < registerTestingData.length; i++) {
			templateRegister((String) registerTestingData[i][0],
					(String) registerTestingData[i][1],
					(String) registerTestingData[i][2],
					(String) registerTestingData[i][3],
					(String) registerTestingData[i][4],
					(String) registerTestingData[i][5],
					(String) registerTestingData[i][6],
					(String) registerTestingData[i][7],
					(String) registerTestingData[i][8],
					(String) registerTestingData[i][9],
					(String) registerTestingData[i][10],
					(String) registerTestingData[i][11],
					(String) registerTestingData[i][12],
					(Integer) registerTestingData[i][13],
					(Integer) registerTestingData[i][14],
					(Integer) registerTestingData[i][15],
					(Class<?>) registerTestingData[i][16]);
		}
	}

	protected void templateRegister(String creatorUsername, String username,
			String password, String name, String surname, String VAT,
			String photo, String email, String phoneNumber, String address,
			String holder, String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(creatorUsername);

			this.registerAdministrator(username, password, name, surname, VAT,
					photo, email, phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerAdministrator(String username, String password,
			String name, String surname, String VAT, String photo,
			String email, String phoneNumber, String address, String holder,
			String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV) {

		RegisterFormObject adminForm = new RegisterFormObject();
		Administrator newAdmin = new Administrator();
		BindingResult binding = null;

		adminForm.setUsername(username);
		adminForm.setPassword(password);
		adminForm.setPassConfirmation(password);
		adminForm.setTermsAndConditions(true);
		adminForm.setName(name);
		adminForm.setSurname(surname);
		adminForm.setVAT(VAT);
		adminForm.setPhoto(photo);
		adminForm.setEmail(email);
		adminForm.setPhoneNumber(phoneNumber);
		adminForm.setAddress(address);
		adminForm.setHolder(holder);
		adminForm.setMake(make);
		adminForm.setNumber(number);
		adminForm.setExpirationMonth(expirationMonth);
		adminForm.setExpirationYear(expirationYear);
		adminForm.setCVV(CVV);

		newAdmin = this.administratorService.reconstruct(adminForm, binding);

		this.validator.validate(newAdmin, binding);
		this.administratorService.save(newAdmin);
	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "admin", "admin", "admin", "ES12345678",
						"https://www.foto.com", "admin@admin.admin",
						"666666666", "c/ admin", "admin", "VISA",
						"4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "admin", "admin", "ES12345678", "https://www.foto.com",
						"admin@admin.admin", "666666666", "c/ admin", "admin",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "admin", "admin", "admin", null, "https://www.foto.com",
						"admin@admin.admin", "666666666", "c/ admin", "admin",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "admin", "admin", "admin", "ES12345678", null,
						"admin@admin.admin", "666666666", "c/ admin", "adminT",
						"VISA", "4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "admin", "admin", "admin", "ES12345678",
						"https://www.foto.com", null, "666666666", "c/ admin",
						"admin", "VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "admin", "admin", "admin", "ES12345678",
						"https://www.foto.com", "admin@admin.admin",
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

			this.editAdministrator(username, name, surname, VAT, photo, email,
					phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editAdministrator(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String holder, String make, String number,
			Integer expirationMonth, Integer expirationYear, Integer CVV) {

		EditionFormObject adminForm = new EditionFormObject(
				this.administratorService.findByUsername(username));
		Administrator newAdmin = new Administrator();
		BindingResult binding = null;

		adminForm.setUsername(username);
		adminForm.setName(name);
		adminForm.setSurname(surname);
		adminForm.setVAT(VAT);
		adminForm.setPhoto(photo);
		adminForm.setEmail(email);
		adminForm.setPhoneNumber(phoneNumber);
		adminForm.setAddress(address);
		adminForm.setHolder(holder);
		adminForm.setMake(make);
		adminForm.setNumber(number);
		adminForm.setExpirationMonth(expirationMonth);
		adminForm.setExpirationYear(expirationYear);
		adminForm.setCVV(CVV);

		newAdmin = this.administratorService.reconstruct(adminForm, binding);

		this.validator.validate(newAdmin, binding);
		this.administratorService.save(newAdmin);
	}

}