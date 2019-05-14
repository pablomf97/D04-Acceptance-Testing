package services;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SystemConfigurationRepository;
import domain.Actor;
import domain.SystemConfiguration;

@Transactional
@Service
public class SystemConfigurationService {

	/* Working repository */

	@Autowired
	private SystemConfigurationRepository systemConfigurationRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private Validator validator;

	/* Simple CRUD methods */

	/* Saving a system configuration */
	public SystemConfiguration save(
			final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration, "null.system.configuration");
		Actor principal;
		SystemConfiguration result;
		SystemConfiguration aux = this.systemConfigurationRepository
				.findSystemConf();

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"),
				"not.allowed");

		if (aux.getAlreadyRebranded()) {
			Assert.isTrue(systemConfiguration.getAlreadyRebranded());
			Assert.isTrue(systemConfiguration.getSystemName().equals(
					aux.getSystemName()));
		} else if (!systemConfiguration.getSystemName().equals(
				aux.getSystemName())) {
			aux.setAlreadyRebranded(true);
			aux.setSystemName(systemConfiguration.getSystemName());

		}

		aux.setWelcomeMessage(systemConfiguration.getWelcomeMessage());
		aux.setBanner(systemConfiguration.getBanner());
		aux.setCountryCode(systemConfiguration.getCountryCode());
		aux.setTimeResultsCached(systemConfiguration.getTimeResultsCached());
		aux.setMaxResults(systemConfiguration.getMaxResults());
		aux.setBreachNotification(systemConfiguration.getBreachNotification());
		aux.setVATTax(systemConfiguration.getVATTax());

		result = this.systemConfigurationRepository.save(aux);

		return result;
	}

	/* Other methods */

	/* Find system configuration */
	public SystemConfiguration findMySystemConfiguration() {
		final SystemConfiguration result;

		result = this.systemConfigurationRepository.findSystemConf();

		return result;
	}

	/* Find banner */
	public String findMyBanner() {

		String result;

		result = this.findMySystemConfiguration().getBanner();

		return result;
	}

	/* Find welcome message */
	public Map<String, String> findWelcomeMessage() {
		final Map<String, String> result;

		result = this.findMySystemConfiguration().getWelcomeMessage();

		return result;
	}

	/* Find BreachNotification */
	public Map<String, String> findBreachNotification() {
		final Map<String, String> result;
		result = this.findMySystemConfiguration().getBreachNotification();
		return result;
	}

	public SystemConfiguration reconstruct(
			final SystemConfiguration systemConfiguration, final String nameES,
			final String nameEN, final String nEs, final String nEn,
			final BindingResult binding) {

		SystemConfiguration res = new SystemConfiguration();

		res.setWelcomeMessage(systemConfiguration.getWelcomeMessage());
		res.setBreachNotification(systemConfiguration.getBreachNotification());
		res.setSystemName(systemConfiguration.getSystemName());
		res.setBanner(systemConfiguration.getBanner());
		res.setCountryCode(systemConfiguration.getCountryCode());
		res.setTimeResultsCached(systemConfiguration.getTimeResultsCached());
		res.setMaxResults(systemConfiguration.getMaxResults());
		res.setVATTax(systemConfiguration.getVATTax());
		res.setAlreadyRebranded(systemConfiguration.getAlreadyRebranded());

		Assert.isTrue(this.actorService.checkAuthority(
				this.actorService.findByPrincipal(), "ADMIN"));

		res.setWelcomeMessage(new HashMap<String, String>());

		res.getWelcomeMessage().put("Español", nameES);
		res.getWelcomeMessage().put("English", nameEN);
		res.setBreachNotification(new HashMap<String, String>());
		res.getBreachNotification().put("Español", nEs);
		res.getBreachNotification().put("English", nEn);

		try {
			Assert.isTrue(!res.getSystemName().isEmpty());
		} catch (Throwable oops) {
			binding.rejectValue("systemName", "name.error");
		}

		try {
			Assert.isTrue(!res.getBanner().isEmpty());
		} catch (Throwable oops) {
			binding.rejectValue("banner", "banner.error");
		}

		try {
			Assert.isTrue(this.utilityService.checkCC(res.getCountryCode()));
		} catch (Throwable oops) {
			binding.rejectValue("countryCode", "cc.error");
		}

		try {
			Assert.isTrue(res.getTimeResultsCached() >= 1
					&& res.getTimeResultsCached() <= 24);
		} catch (Throwable oops) {
			binding.rejectValue("timeResultsCached", "time.error");
		}

		try {
			Assert.isTrue(res.getMaxResults() > 0 && res.getMaxResults() <= 100);
		} catch (Throwable oops) {
			binding.rejectValue("maxResults", "results.error");
		}

		try {
			Assert.isTrue(res.getVATTax() > 0 && res.getVATTax() < 1);
		} catch (Throwable oops) {
			binding.rejectValue("VATTax", "vat.error");
		}

		this.validator.validate(res, binding);

		return res;
	}

	/* Find one system configuration */
	public SystemConfiguration findOne(final int systemConfigurationId) {
		SystemConfiguration res;

		res = this.systemConfigurationRepository.findOne(systemConfigurationId);

		return res;

	}
	public void runOnlyOnceProcess(){
		SystemConfiguration res= this.findMySystemConfiguration();
		
		Assert.isTrue(res.getAlreadyRebranded()==false,"commit.error");
		Assert.isTrue(res!=null);
		
		res.setAlreadyRebranded(true);
		
		this.systemConfigurationRepository.save(res);
		
	}
	
}
