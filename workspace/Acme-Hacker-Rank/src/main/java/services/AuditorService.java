package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Transactional
@Service
public class AuditorService {

	/* Working repository */

	@Autowired
	private AuditorRepository auditorRepository;

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
	private AuditService auditService;

	/* Simple CRUD methods */

	public Auditor create() {
		Auditor res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();

		res = new Auditor();

		auth.setAuthority(Authority.AUDITOR);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Auditor findOne(Integer auditorId) {
		Auditor res;

		Assert.notNull(auditorId);
		res = this.auditorRepository.findOne(auditorId);

		return res;
	}

	public List<Auditor> findAll() {

		return this.auditorRepository.findAll();
	}

	/**
	 * Save an auditor
	 * 
	 * @param Auditor
	 * 
	 * @return Auditor
	 */
	public Auditor save(Auditor auditor) {
		Auditor res;
		Auditor principal;

		Assert.notNull(auditor);

		if (auditor.getId() == 0) {

			/* Managing phone number */
			char[] phoneArray = auditor.getPhoneNumber().toCharArray();
			if ((!auditor.getPhoneNumber().equals(null) && !auditor
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					auditor.setPhoneNumber(cc + " " + auditor.getPhoneNumber());
				}
			}

			/* Managing email */
			/*
			 * String email = auditor.getEmail(); Assert.isTrue(
			 * this.actorService.checkEmail(email, auditor
			 * .getUserAccount().getAuthorities().iterator()
			 * .next().toString()), "actor.email.error");
			 */

			/* Managing photo */
			/*
			 * Assert.isTrue(ResourceUtils.isUrl(auditor.getPhoto()),
			 * "actor.photo.error");
			 */
		} else {
			principal = (Auditor) this.actorService.findByPrincipal();
			Assert.isTrue(principal.getId() == auditor.getId(), "no.permission");

			/* Managing email */
			// String email = auditor.getEmail();
			/*
			 * Assert.isTrue( this.actorService.checkEmail(email, auditor
			 * .getUserAccount().getAuthorities().iterator()
			 * .next().toString()), "actor.email.error");
			 */

			/* Managing phone number */
			char[] phoneArray = auditor.getPhoneNumber().toCharArray();
			if ((!auditor.getPhoneNumber().equals(null) && !auditor
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					auditor.setPhoneNumber(cc + " " + auditor.getPhoneNumber());
				}
			}

			auditor.setUserAccount(principal.getUserAccount());
		}
		res = this.auditorRepository.save(auditor);
		return res;
	}

	/* Other methods */

	/**
	 * Reconstruct an Auditor from the database
	 * 
	 * @param Auditor
	 * 
	 * @return Auditor
	 */
	public Auditor reconstruct(EditionFormObject form, BindingResult binding) {

		Auditor res = this.create();

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
				Assert.isTrue(this.actorService.checkEmail(form.getEmail(),
						"AUDITOR"), "actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	/**
	 * Reconstruct a auditor from a register object form from the database
	 * 
	 * @param RegisterFormObject
	 * 
	 * @return Auditor
	 */
	public Auditor reconstruct(RegisterFormObject form, BindingResult binding) {

		/* Creating auditor */
		Auditor res = this.create();

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
		authority.setAuthority(Authority.AUDITOR);
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
				Assert.isTrue(this.actorService.checkEmail(form.getEmail(),
						"AUDITOR"), "actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	public void flush() {
		this.auditorRepository.flush();
	}

	public Auditor findByUsername(String username) {
		return this.auditorRepository.findByUsername(username);

	}

	public void delete(Auditor auditor) {
		Actor principal;

		Assert.notNull(auditor);

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == auditor.getId(), "no.permission");
		Collection<Audit> cols = this.auditService.auditsPerAuditor(auditor
				.getId());
		this.auditService.deleteAuditsPerAuditor(cols);
		this.auditorRepository.delete(auditor);
	}
}
