package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.Hacker;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Transactional
@Service
public class HackerService {

	/* Working repository */

	@Autowired
	private HackerRepository hackerRepository;

	/* Services */

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private FinderService finderService;

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private ApplicationService applicationService;

	/* Simple CRUD methods */

	public Hacker create() {
		Hacker res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();

		res = new Hacker();

		auth.setAuthority(Authority.HACKER);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Hacker findOne(Integer hackerId) {
		Hacker res;

		Assert.notNull(hackerId);
		res = this.hackerRepository.findOne(hackerId);

		return res;
	}

	public List<Hacker> findAll() {

		return this.hackerRepository.findAll();
	}

	/**
	 * Save an hacker
	 * 
	 * @param Hacker
	 * 
	 * @return Hacker
	 */
	public Hacker save(Hacker hacker) {
		Hacker res;
		Hacker principal;

		Assert.notNull(hacker);

		if (hacker.getId() == 0) {

			/* Managing phone number */
			char[] phoneArray = hacker.getPhoneNumber().toCharArray();
			if ((!hacker.getPhoneNumber().equals(null) && !hacker
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					hacker.setPhoneNumber(cc + " " + hacker.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = hacker.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, hacker.getUserAccount()
							.getAuthorities().iterator().next().toString()),
					"actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(hacker.getPhoto()),
					"actor.photo.error");
		} else {
			principal = (Hacker) this.actorService.findByPrincipal();
			Assert.isTrue(principal.getId() == hacker.getId(), "no.permission");

			/* Managing email */
			String email = hacker.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, hacker.getUserAccount()
							.getAuthorities().iterator().next().toString()),
					"actor.email.error");

			/* Managing phone number */
			char[] phoneArray = hacker.getPhoneNumber().toCharArray();
			if ((!hacker.getPhoneNumber().equals(null) && !hacker
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					hacker.setPhoneNumber(cc + " " + hacker.getPhoneNumber());
				}
			}

			hacker.setUserAccount(principal.getUserAccount());

			if (principal.getFinder() != null) {
				hacker.setFinder(principal.getFinder());
			}
		}

		res = this.hackerRepository.save(hacker);
		return res;
	}

	/* Other methods */

	/**
	 * Reconstruct an Hacker from the database
	 * 
	 * @param Hacker
	 * 
	 * @return Hacker
	 */
	public Hacker reconstruct(EditionFormObject form, BindingResult binding) {

		Hacker res = this.create();

		res.setId(form.getId());
		res.setVersion(form.getVersion());
		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());

		/* Creating credit card */
		CreditCard creditCard = new CreditCard();

		creditCard.setHolder(form.getHolder());
		creditCard.setMake(form.getMake());
		creditCard.setNumber(form.getNumber());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setCVV(form.getCVV());

		res.setCreditCard(creditCard);

		/* VAT */
		if (form.getVAT() != null) {
			try {
				Assert.isTrue(this.utilityService.checkVAT(form.getVAT()),
						"VAT.error");
			} catch (Throwable oops) {
				binding.rejectValue("VAT", "VAT.error");
			}
		}

		/* Credit card */
		if (form.getNumber() != null) {
			try {
				Assert.isTrue(this.creditCardService
						.checkCreditCardNumber(creditCard.getNumber()),
						"card.number.error");
			} catch (Throwable oops) {
				binding.rejectValue("number", "number.error");
			}
		}

		if (creditCard.getExpirationMonth() != null
				&& creditCard.getExpirationYear() != null) {

			try {
				Assert.isTrue(
						!this.creditCardService.checkIfExpired(
								creditCard.getExpirationMonth(),
								creditCard.getExpirationYear()),
						"card.date.error");
			} catch (Throwable oops) {
				binding.rejectValue("expirationMonth", "card.date.error");
			}

			if (form.getCVV() != null) {
				try {
					Assert.isTrue(form.getCVV() < 999 && form.getCVV() > 100,
							"CVV.error");
				} catch (Throwable oops) {
					binding.rejectValue("CVV", "CVV.error");
				}
			}
		}

		if (form.getEmail() != null) {
			try {
				Assert.isTrue(
						this.actorService.checkEmail(form.getEmail(), "HACKER"),
						"actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	/**
	 * Reconstruct a hacker from a register object form from the database
	 * 
	 * @param RegisterFormObject
	 * 
	 * @return Hacker
	 */
	public Hacker reconstruct(RegisterFormObject form, BindingResult binding) {

		/* Creating hacker */
		Hacker res = this.create();

		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());

		/* Creating credit card */
		CreditCard creditCard = new CreditCard();

		creditCard.setHolder(form.getHolder());
		creditCard.setMake(form.getMake());
		creditCard.setNumber(form.getNumber());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setCVV(form.getCVV());

		res.setCreditCard(creditCard);

		/* Creating user account */
		UserAccount userAccount = new UserAccount();

		List<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		userAccount.setUsername(form.getUsername());

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		userAccount
				.setPassword(encoder.encodePassword(form.getPassword(), null));

		res.setUserAccount(userAccount);

		/* VAT */
		if (form.getVAT() != null) {
			try {
				Assert.isTrue(this.utilityService.checkVAT(form.getVAT()),
						"VAT.error");
			} catch (Throwable oops) {
				binding.rejectValue("VAT", "VAT.error");
			}
		}

		/* Password confirmation */
		if (form.getPassword() != null) {
			try {
				Assert.isTrue(
						form.getPassword().equals(form.getPassConfirmation()),
						"pass.confirm.error");
			} catch (Throwable oops) {
				binding.rejectValue("password", "pass.confirm.error");
			}
		}

		/* Terms&Conditions */
		if (form.getTermsAndConditions() != null) {
			try {
				Assert.isTrue((form.getTermsAndConditions()), "terms.error");
			} catch (Throwable oops) {
				binding.rejectValue("termsAndConditions", "terms.error");
			}
		}

		/* Credit card */
		if (form.getNumber() != null) {
			try {
				Assert.isTrue(this.creditCardService
						.checkCreditCardNumber(creditCard.getNumber()),
						"card.number.error");
			} catch (Throwable oops) {
				binding.rejectValue("number", "number.error");
			}
		}

		if (creditCard.getExpirationMonth() != null
				&& creditCard.getExpirationYear() != null) {

			try {
				Assert.isTrue(
						!this.creditCardService.checkIfExpired(
								creditCard.getExpirationMonth(),
								creditCard.getExpirationYear()),
						"card.date.error");
			} catch (Throwable oops) {
				binding.rejectValue("expirationMonth", "card.date.error");
			}

			if (form.getCVV() != null) {
				try {
					Assert.isTrue(form.getCVV() < 999 && form.getCVV() > 100,
							"CVV.error");
				} catch (Throwable oops) {
					binding.rejectValue("CVV", "CVV.error");
				}
			}
		}

		if (form.getEmail() != null) {
			try {
				Assert.isTrue(
						this.actorService.checkEmail(form.getEmail(), "HACKER"),
						"actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	public String hackerWithMoreApplications() {

		String res = this.hackerRepository.hackerWithMoreApplications();
		if (res == null) {
			res = "";
		}
		return res;

	}

	public void flush() {
		this.hackerRepository.flush();
	}

	public Hacker findByUsername(String username) {
		return this.hackerRepository.findByUsername(username);

	}

	public void delete(Hacker hacker) {
		Actor principal;

		Assert.notNull(hacker);

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == hacker.getId(), "no.permission");

		this.applicationService.deleteApp(hacker);

		this.curriculaService.deleteCV(hacker);

		this.finderService.deleteFinder(hacker);

		this.hackerRepository.delete(hacker);
	}
}
