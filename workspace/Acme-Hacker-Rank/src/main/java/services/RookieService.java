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

import repositories.RookieRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.Finder;
import domain.Rookie;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Transactional
@Service
public class RookieService {

	/* Working repository */

	@Autowired
	private RookieRepository rookieRepository;

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

	public Rookie create() {
		Rookie res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();

		res = new Rookie();

		auth.setAuthority(Authority.ROOKIE);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Rookie findOne(final Integer rookieId) {
		Rookie res;

		Assert.notNull(rookieId);
		res = this.rookieRepository.findOne(rookieId);

		return res;
	}

	public List<Rookie> findAll() {

		return this.rookieRepository.findAll();
	}

	/**
	 * Save an rookie
	 * 
	 * @param Rookie
	 * 
	 * @return Rookie
	 */
	public Rookie save(final Rookie rookie) {
		Rookie res;
		Rookie principal;

		Assert.notNull(rookie);

		if (rookie.getId() == 0) {

			/* Managing phone number */
			final char[] phoneArray = rookie.getPhoneNumber().toCharArray();
			if ((!rookie.getPhoneNumber().equals(null) && !rookie
					.getPhoneNumber().equals("")))
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					final String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					rookie.setPhoneNumber(cc + " " + rookie.getPhoneNumber());
				}

			/* Managing email */
			final String email = rookie.getEmail();
			// Assert.isTrue(this.actorService.checkEmail(email,
			// rookie.getUserAccount().getAuthorities().iterator().next().toString()),
			// "actor.email.error");

			/* Managing photo */
			/*
			 * Assert.isTrue(ResourceUtils.isUrl(rookie.getPhoto()),
			 * "actor.photo.error");
			 */

		} else {
			principal = (Rookie) this.actorService.findByPrincipal();
			Assert.isTrue(principal.getId() == rookie.getId(), "no.permission");

			/* Managing email */
			final String email = rookie.getEmail();
			// Assert.isTrue(this.actorService.checkEmail(email,
			// rookie.getUserAccount().getAuthorities().iterator().next().toString()),
			// "actor.email.error");

			/* Managing phone number */
			final char[] phoneArray = rookie.getPhoneNumber().toCharArray();
			if ((!rookie.getPhoneNumber().equals(null) && !rookie
					.getPhoneNumber().equals("")))
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					final String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					rookie.setPhoneNumber(cc + " " + rookie.getPhoneNumber());
				}

			rookie.setUserAccount(principal.getUserAccount());

			if (principal.getFinder() != null)
				rookie.setFinder(principal.getFinder());

		}
		res = this.rookieRepository.save(rookie);

		return res;
	}

	public void saveFinder(final Rookie hack, final Finder f) {
		hack.setFinder(f);
		this.rookieRepository.save(hack);

	}

	/* Other methods */

	/**
	 * Reconstruct an Rookie from the database
	 * 
	 * @param Rookie
	 * 
	 * @return Rookie
	 */
	public Rookie reconstruct(final EditionFormObject form,
			final BindingResult binding) {

		final Rookie res = this.create();

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
		final CreditCard creditCard = new CreditCard();

		creditCard.setHolder(form.getHolder());
		creditCard.setMake(form.getMake());
		creditCard.setNumber(form.getNumber());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setCVV(form.getCVV());

		res.setCreditCard(creditCard);

		/* VAT */
		if (form.getVAT() != null)
			try {
				Assert.isTrue(this.utilityService.checkVAT(form.getVAT()),
						"VAT.error");
			} catch (final Throwable oops) {
				binding.rejectValue("VAT", "VAT.error");
			}

		/* Credit card */
		if (form.getNumber() != null)
			try {
				Assert.isTrue(this.creditCardService
						.checkCreditCardNumber(creditCard.getNumber()),
						"card.number.error");
			} catch (final Throwable oops) {
				binding.rejectValue("number", "number.error");
			}

		if (creditCard.getExpirationMonth() != null
				&& creditCard.getExpirationYear() != null) {

			try {
				Assert.isTrue(
						!this.creditCardService.checkIfExpired(
								creditCard.getExpirationMonth(),
								creditCard.getExpirationYear()),
						"card.date.error");
			} catch (final Throwable oops) {
				binding.rejectValue("expirationMonth", "card.date.error");
			}

			if (form.getCVV() != null)
				try {
					Assert.isTrue(form.getCVV() < 999 && form.getCVV() > 100,
							"CVV.error");
				} catch (final Throwable oops) {
					binding.rejectValue("CVV", "CVV.error");
				}
		}

		if (form.getEmail() != null)
			try {
				Assert.isTrue(
						this.actorService.checkEmail(form.getEmail(), "ROOKIE"),
						"actor.email.error");
			} catch (final Throwable oops) {
				binding.rejectValue("email", "email.error");
			}

		return res;
	}

	/**
	 * Reconstruct a rookie from a register object form from the database
	 * 
	 * @param RegisterFormObject
	 * 
	 * @return Rookie
	 */
	public Rookie reconstruct(final RegisterFormObject form,
			final BindingResult binding) {

		/* Creating rookie */
		final Rookie res = this.create();

		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());

		/* Creating credit card */
		final CreditCard creditCard = new CreditCard();

		creditCard.setHolder(form.getHolder());
		creditCard.setMake(form.getMake());
		creditCard.setNumber(form.getNumber());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setCVV(form.getCVV());

		res.setCreditCard(creditCard);

		/* Creating user account */
		final UserAccount userAccount = new UserAccount();

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		userAccount.setUsername(form.getUsername());

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		userAccount
				.setPassword(encoder.encodePassword(form.getPassword(), null));

		res.setUserAccount(userAccount);

		/* VAT */
		if (form.getVAT() != null)
			try {
				Assert.isTrue(this.utilityService.checkVAT(form.getVAT()),
						"VAT.error");
			} catch (final Throwable oops) {
				binding.rejectValue("VAT", "VAT.error");
			}

		/* Password confirmation */
		if (form.getPassword() != null)
			try {
				Assert.isTrue(
						form.getPassword().equals(form.getPassConfirmation()),
						"pass.confirm.error");
			} catch (final Throwable oops) {
				binding.rejectValue("password", "pass.confirm.error");
			}

		/* Terms&Conditions */
		if (form.getTermsAndConditions() != null)
			try {
				Assert.isTrue((form.getTermsAndConditions()), "terms.error");
			} catch (final Throwable oops) {
				binding.rejectValue("termsAndConditions", "terms.error");
			}

		/* Credit card */
		if (form.getNumber() != null)
			try {
				Assert.isTrue(this.creditCardService
						.checkCreditCardNumber(creditCard.getNumber()),
						"card.number.error");
			} catch (final Throwable oops) {
				binding.rejectValue("number", "number.error");
			}

		if (creditCard.getExpirationMonth() != null
				&& creditCard.getExpirationYear() != null) {

			try {
				Assert.isTrue(
						!this.creditCardService.checkIfExpired(
								creditCard.getExpirationMonth(),
								creditCard.getExpirationYear()),
						"card.date.error");
			} catch (final Throwable oops) {
				binding.rejectValue("expirationMonth", "card.date.error");
			}

			if (form.getCVV() != null)
				try {
					Assert.isTrue(form.getCVV() < 999 && form.getCVV() > 100,
							"CVV.error");
				} catch (final Throwable oops) {
					binding.rejectValue("CVV", "CVV.error");
				}
		}

		if (form.getEmail() != null)
			try {
				Assert.isTrue(
						this.actorService.checkEmail(form.getEmail(), "ROOKIE"),
						"actor.email.error");
			} catch (final Throwable oops) {
				binding.rejectValue("email", "email.error");
			}

		return res;
	}

	public String rookieWithMoreApplications() {

		String res = this.rookieRepository.rookieWithMoreApplications();
		if (res == null)
			res = "";
		return res;

	}

	public void flush() {
		this.rookieRepository.flush();
	}

	public Rookie findByUsername(final String username) {
		return this.rookieRepository.findByUsername(username);

	}

	public void delete(final Rookie rookie) {
		Actor principal;

		Assert.notNull(rookie);

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == rookie.getId(), "no.permission");

		this.applicationService.deleteApp(rookie);

		this.curriculaService.deleteCV(rookie);

		this.finderService.deleteFinder(rookie);

		this.rookieRepository.delete(rookie);
	}
}
