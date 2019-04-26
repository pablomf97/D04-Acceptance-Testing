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
	private Validator validator;

	/* Simple CRUD methods */

	/* Saving a system configuration */
	public SystemConfiguration save(
			final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration, "null.system.configuration");
		Actor principal;
		SystemConfiguration result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "ADMIN"),
				"not.allowed");

		result = this.systemConfigurationRepository.save(systemConfiguration);

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

		Assert.isTrue(systemConfiguration.getId() == this
				.findMySystemConfiguration().getId());
		Assert.isTrue(this.actorService.checkAuthority(
				this.actorService.findByPrincipal(), "ADMIN"));

		final SystemConfiguration bd = this.systemConfigurationRepository
				.findOne(systemConfiguration.getId());

		systemConfiguration.setWelcomeMessage(new HashMap<String, String>());

		systemConfiguration.getWelcomeMessage().put("Español", nameES);
		systemConfiguration.getWelcomeMessage().put("English", nameEN);
		systemConfiguration
				.setBreachNotification(new HashMap<String, String>());
		systemConfiguration.getBreachNotification().put("Español", nEs);
		systemConfiguration.getBreachNotification().put("English", nEn);

		bd.setWelcomeMessage(systemConfiguration.getWelcomeMessage());
		bd.setBreachNotification(systemConfiguration.getBreachNotification());
		bd.setSystemName(systemConfiguration.getSystemName());
		bd.setBanner(systemConfiguration.getBanner());
		bd.setCountryCode(systemConfiguration.getCountryCode());
		bd.setTimeResultsCached(systemConfiguration.getTimeResultsCached());
		bd.setMaxResults(systemConfiguration.getMaxResults());

		this.validator.validate(bd, binding);

		res = bd;

		return res;
	}

	/* Find one system configuration */
	public SystemConfiguration findOne(final int systemConfigurationId) {
		SystemConfiguration res;

		res = this.systemConfigurationRepository.findOne(systemConfigurationId);

		return res;

	}
}
