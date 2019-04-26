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
import domain.Company;
import forms.EditionCompanyFormObject;
import forms.RegisterCompanyFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

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
	private CompanyService companyService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "companyT", "companyT", "companyT", "companyT", "ES12345678",
						"https://www.foto.com", "companyT@companyT.companyT",
						"666666666", "c/ companyT", "companyT", "companyT",
						"VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ "companyT", null, "companyT", "companyT", "ES12345678",
						"https://www.foto.com", "companyT@companyT.companyT",
						"666666666", "c/ companyT", "companyT", "companyT",
						"VISA", "4111111111111111", 02, 22, 123, null },
				{ "companyT", "companyT", "companyT", "companyT", null,
						"https://www.foto.com", "companyT@companyT.companyT",
						"666666666", "c/ companyT", "companyT", "companyT",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "companyT", "companyT", "companyT", "companyT", "ES12345678",
						null, "companyT@companyT.companyT", "666666666",
						"c/ companyT", "companyT", "companyT", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "companyT", "companyT", "companyT", "companyT", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ companyT", "companyT", "companyT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "companyT", "companyT", "companyT", "companyT", "ES12345678",
						"https://www.foto.com", "companyT@companyT.companyT",
						"666666666", "c/ companyT", "companyT", null, null,
						null, null, null, null, ValidationException.class } };

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3],
					(String) testingData[i][4], (String) testingData[i][5],
					(String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9],
					(String) testingData[i][10], (String) testingData[i][11],
					(String) testingData[i][12], (Integer) testingData[i][13],
					(Integer) testingData[i][14], (Integer) testingData[i][15],
					(Class<?>) testingData[i][16]);
		}
	}

	protected void template(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String commercialName,
			String holder, String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			this.registerCompany(username, password, name, surname, VAT, photo,
					email, phoneNumber, address, commercialName, holder, make,
					number, expirationMonth, expirationYear, CVV);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerCompany(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String commercialName,
			String holder, String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV) {

		RegisterCompanyFormObject comForm = new RegisterCompanyFormObject();
		Company newCom = new Company();
		BindingResult binding = null;

		comForm.setUsername(username);
		comForm.setPassword(password);
		comForm.setPassConfirmation(password);
		comForm.setTermsAndConditions(true);
		comForm.setCommercialName(commercialName);
		comForm.setName(name);
		comForm.setSurname(surname);
		comForm.setVAT(VAT);
		comForm.setPhoto(photo);
		comForm.setEmail(email);
		comForm.setPhoneNumber(phoneNumber);
		comForm.setAddress(address);
		comForm.setHolder(holder);
		comForm.setMake(make);
		comForm.setNumber(number);
		comForm.setExpirationMonth(expirationMonth);
		comForm.setExpirationYear(expirationYear);
		comForm.setCVV(CVV);

		newCom = this.companyService.reconstruct(comForm, binding);

		this.validator.validate(newCom, binding);
		this.companyService.save(newCom);

	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "company1", "company1", "company1", "ES12345678",
						"https://www.foto.com", "company1@company1.company1",
						"666666666", "c/ company1", "company1", "company1",
						"VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "company1", "company1", "ES12345678",
						"https://www.foto.com", "company1@company1.company1",
						"666666666", "c/ company1", "company1", "company1",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "company1", "company1", "company1", null,
						"https://www.foto.com", "company1@company1.company1",
						"666666666", "c/ company1", "company1", "company1",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "company1", "company1", "company1", "ES12345678", null,
						"company1@company1.company1", "666666666",
						"c/ company1", "company1", "company1", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "company1", "company1", "company1", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ company1", "company1", "company1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "company1", "company1", "company1", "ES12345678",
						"https://www.foto.com", "company1@company1.company1",
						"666666666", "c/ admin", "company1", null, null, null,
						null, null, null, ValidationException.class } };

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
					(String) editionTestingData[i][11],
					(Integer) editionTestingData[i][12],
					(Integer) editionTestingData[i][13],
					(Integer) editionTestingData[i][14],
					(Class<?>) editionTestingData[i][15]);
		}
	}

	protected void templateEdit(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String commercialName, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.editCompany(username, name, surname, VAT, photo, email,
					phoneNumber, address, commercialName, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editCompany(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String commercialName, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV) {

		EditionCompanyFormObject comForm = new EditionCompanyFormObject(
				this.companyService.findByUsername(username));
		Company newCom = new Company();
		BindingResult binding = null;

		comForm.setUsername(username);
		comForm.setName(name);
		comForm.setSurname(surname);
		comForm.setVAT(VAT);
		comForm.setPhoto(photo);
		comForm.setEmail(email);
		comForm.setPhoneNumber(phoneNumber);
		comForm.setAddress(address);
		comForm.setCommercialName(commercialName);
		comForm.setHolder(holder);
		comForm.setMake(make);
		comForm.setNumber(number);
		comForm.setExpirationMonth(expirationMonth);
		comForm.setExpirationYear(expirationYear);
		comForm.setCVV(CVV);

		newCom = this.companyService.reconstruct(comForm, binding);

		this.validator.validate(newCom, binding);
		this.companyService.save(newCom);
	}
}