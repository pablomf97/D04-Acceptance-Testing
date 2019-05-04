package services;

import java.text.ParseException;
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

import repositories.ProviderRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.Item;
import domain.Provider;
import domain.Sponsorship;
import forms.EditionCompanyFormObject;
import forms.RegisterCompanyFormObject;

@Transactional
@Service
public class ProviderService {

	/* Working repository */

	@Autowired
	private ProviderRepository providerRepository;

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
	private ItemService itemService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	/* Simple CRUD methods */

	public Provider create() {
		Provider res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();
		res = new Provider();

		auth.setAuthority(Authority.PROVIDER);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Provider findOne(Integer providerId) {
		Provider res;

		Assert.notNull(providerId);
		res = this.providerRepository.findOne(providerId);

		return res;
	}

	public List<Provider> findAll() {

		return this.providerRepository.findAll();
	}

	public Provider save(Provider provider) {
		Provider res;
		Actor principal;

		Assert.notNull(provider);

		if (provider.getId() == 0) {

			/* Managing phone number */
			char[] phoneArray = provider.getPhoneNumber().toCharArray();
			if ((!provider.getPhoneNumber().equals(null) && !provider
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					provider.setPhoneNumber(cc + " "
							+ provider.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = provider.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, provider
							.getUserAccount().getAuthorities().iterator()
							.next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(provider.getPhoto()),
					"actor.photo.error");
		} else {
			principal = this.actorService.findByPrincipal();

			Assert.isTrue(principal.getId() == provider.getId(),
					"no.permission");

			provider.setUserAccount(principal.getUserAccount());

			/* Managing phone number */
			char[] phoneArray = provider.getPhoneNumber().toCharArray();
			if ((!provider.getPhoneNumber().equals(null) && !provider
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					provider.setPhoneNumber(cc + " "
							+ provider.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = provider.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, provider
							.getUserAccount().getAuthorities().iterator()
							.next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(provider.getPhoto()),
					"actor.photo.error");
		}

		res = this.providerRepository.save(provider);
		return res;
	}

	/* Other methods */

	/**
	 * Reconstruct an Provider from the database
	 * 
	 * @param Provider
	 * 
	 * @return Provider
	 */
	public Provider reconstruct(EditionCompanyFormObject form,
			BindingResult binding) {

		Provider res = this.create();

		res.setId(form.getId());
		res.setVersion(form.getVersion());
		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());
		res.setProviderMake(form.getCommercialName());

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
			} catch (ParseException pe) {
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
						"COMPANY"), "actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	/**
	 * Reconstruct an provider from a register object form from the database
	 * 
	 * @param RegisterFormObject
	 * 
	 * @return Provider
	 */
	public Provider reconstruct(RegisterCompanyFormObject form,
			BindingResult binding) {

		/* Creating provider */
		Provider res = this.create();

		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());
		res.setProviderMake(form.getCommercialName());

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
		authority.setAuthority(Authority.PROVIDER);
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
						"COMPANY"), "actor.email.error");
			} catch (Throwable oops) {
				binding.rejectValue("email", "email.error");
			}
		}

		return res;
	}

	public Provider findByUsername(String username) {
		return this.providerRepository.findByUsername(username);
	}

	public void delete(Provider provider) {
		Actor principal;

		Assert.notNull(provider);

		Assert.isTrue(provider.getId() != 0);

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == provider.getId(), "no.permission");
		
		Collection<Item> col=this.itemService.itemsPerProvider(provider.getId());
		
		this.itemService.deleteItemsPerProvider(col);
		
		Collection<Sponsorship> spos=this.sponsorshipService.sponsorshipsPerProvider(provider.getId());
		this.sponsorshipService.deleteSponsorshipsPerProvider(spos);
		
		this.providerRepository.delete(provider);
	}
	public Double []statsItemsPerProvider(){
		return this.providerRepository.statsItemsPerProvider();
	}
	
	public Double[] statsSponsorshipsPerProvider(){
		return this.providerRepository.statsSponsorshipsPerProvider();
	}
	public Collection<String> top5ProvidersWithItems(){
		
				List<String> col= (List<String>) this.providerRepository.top5ProvidersWithItems();
				return col.subList(0,5);
	}
	
	public Collection<String> Percentage10AVGSponsorshipPerProvider(){

		return this.providerRepository.Percentage10AVGSponsorshipPerProvider();
	}
	
}
