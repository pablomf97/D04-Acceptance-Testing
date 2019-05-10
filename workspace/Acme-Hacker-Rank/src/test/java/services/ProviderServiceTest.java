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
import domain.Provider;
import forms.EditionCompanyFormObject;
import forms.RegisterCompanyFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest{
	
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
	private ProviderService providerService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "providerT", "providerT", "providerT", "providerT", "ES12345678",
						"https://www.foto.com", "providerT@providerT.providerT",
						"666666666", "c/ providerT", "providerT", "providerT",
						"VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ "providerT", null, "providerT", "providerT", "ES12345678",
						"https://www.foto.com", "providerT@providerT.providerT",
						"666666666", "c/ providerT", "providerT", "providerT",
						"VISA", "4111111111111111", 02, 22, 123, null },
				{ "providerT", "providerT", "providerT", "providerT", null,
						"https://www.foto.com", "providerT@providerT.providerT",
						"666666666", "c/ providerT", "providerT", "providerT",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "providerT", "providerT", "providerT", "providerT", "ES12345678",
						null, "providerT@providerT.providerT", "666666666",
						"c/ providerT", "providerT", "providerT", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "providerT", "providerT", "providerT", "providerT", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ providerT", "providerT", "providerT", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "providerT", "providerT", "providerT", "providerT", "ES12345678",
						"https://www.foto.com", "providerT@providerT.providerT",
						"666666666", "c/ providerT", "providerT", null, null,
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
			this.registerProvider(username, password, name, surname, VAT, photo,
					email, phoneNumber, address, commercialName, holder, make,
					number, expirationMonth, expirationYear, CVV);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerProvider(String username, String password, String name,
			String surname, String VAT, String photo, String email,
			String phoneNumber, String address, String commercialName,
			String holder, String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV) {

		RegisterCompanyFormObject providerForm = new RegisterCompanyFormObject();
		Provider newProvider = new Provider();
		BindingResult binding = null;

		providerForm.setUsername(username);
		providerForm.setPassword(password);
		providerForm.setPassConfirmation(password);
		providerForm.setTermsAndConditions(true);
		providerForm.setCommercialName(commercialName);
		providerForm.setName(name);
		providerForm.setSurname(surname);
		providerForm.setVAT(VAT);
		providerForm.setPhoto(photo);
		providerForm.setEmail(email);
		providerForm.setPhoneNumber(phoneNumber);
		providerForm.setAddress(address);
		providerForm.setHolder(holder);
		providerForm.setMake(make);
		providerForm.setNumber(number);
		providerForm.setExpirationMonth(expirationMonth);
		providerForm.setExpirationYear(expirationYear);
		providerForm.setCVV(CVV);

		newProvider = this.providerService.reconstruct(providerForm, binding);

		this.validator.validate(newProvider, binding);
		this.providerService.save(newProvider);

	}

	/* ######################################################################## */

	@Test
	public void driverEdit() {
		Object editionTestingData[][] = {
				/* Positive case */
				{ "provider1", "provider1", "provider1", "ES12345678",
						"https://www.foto.com", "provider1@provider1.provider1",
						"666666666", "c/ provider1", "provider1", "provider1",
						"VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative cases: invalid data */
				{ null, "provider1", "provider1", "ES12345678",
						"https://www.foto.com", "provider1@provider1.provider1",
						"666666666", "c/ provider1", "provider1", "provider1",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "provider1", "provider1", "provider1", null,
						"https://www.foto.com", "provider1@provider1.provider1",
						"666666666", "c/ provider1", "provider1", "provider1",
						"VISA", "4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "provider1", "provider1", "provider1", "ES12345678", null,
						"provider1@provider1.provider1", "666666666",
						"c/ provider1", "provider1", "provider1", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				{ "provider1", "provider1", "provider1", "ES12345678",
						"https://www.foto.com", null, "666666666",
						"c/ provider1", "provider1", "provider1", "VISA",
						"4111111111111111", 02, 22, 123,
						NullPointerException.class },
				{ "provider1", "provider1", "provider1", "ES12345678",
						"https://www.foto.com", "provider1@provider1.provider1",
						"666666666", "c/ admin", "provider1", null, null, null,
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

			this.editProvider(username, name, surname, VAT, photo, email,
					phoneNumber, address, commercialName, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void editProvider(String username, String name, String surname,
			String VAT, String photo, String email, String phoneNumber,
			String address, String commercialName, String holder, String make,
			String number, Integer expirationMonth, Integer expirationYear,
			Integer CVV) {

		EditionCompanyFormObject providerForm = new EditionCompanyFormObject(
				this.providerService.findByUsername(username));
		Provider newProvider = new Provider();
		BindingResult binding = null;

		providerForm.setUsername(username);
		providerForm.setName(name);
		providerForm.setSurname(surname);
		providerForm.setVAT(VAT);
		providerForm.setPhoto(photo);
		providerForm.setEmail(email);
		providerForm.setPhoneNumber(phoneNumber);
		providerForm.setAddress(address);
		providerForm.setCommercialName(commercialName);
		providerForm.setHolder(holder);
		providerForm.setMake(make);
		providerForm.setNumber(number);
		providerForm.setExpirationMonth(expirationMonth);
		providerForm.setExpirationYear(expirationYear);
		providerForm.setCVV(CVV);

		newProvider = this.providerService.reconstruct(providerForm, binding);

		this.validator.validate(newProvider, binding);
		this.providerService.save(newProvider);
	}
}
