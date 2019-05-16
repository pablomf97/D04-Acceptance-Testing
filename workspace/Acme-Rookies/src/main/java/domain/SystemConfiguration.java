package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	/* Attributes */

	private String systemName;
	private Map<String, String> welcomeMessage;
	private String banner;
	private String countryCode;
	private Integer timeResultsCached;
	private Integer maxResults;
	private Map<String, String> breachNotification;
	private Double VATTax;
	private Double flatRate;
	private Boolean alreadyRebranded;

	/* Getters&Setters */

	@ElementCollection
	public Map<String, String> getBreachNotification() {
		return this.breachNotification;
	}

	public void setBreachNotification(
			final Map<String, String> breachNotification) {
		this.breachNotification = breachNotification;
	}

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotNull
	@NotEmpty
	@ElementCollection
	public Map<String, String> getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final Map<String, String> welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 1, max = 24)
	public Integer getTimeResultsCached() {
		return this.timeResultsCached;
	}

	public void setTimeResultsCached(final Integer timeResultsCached) {
		this.timeResultsCached = timeResultsCached;
	}

	@Range(min = 0, max = 100)
	public Integer getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final Integer maxResults) {
		this.maxResults = maxResults;
	}

	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 1)
	public Double getVATTax() {
		return VATTax;
	}

	public void setVATTax(Double vATTax) {
		VATTax = vATTax;
	}
	
	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 5)
	public Double getFlatRate() {
		return flatRate;
	}

	public void setFlatRate(Double flatRate) {
		this.flatRate = flatRate;
	}

	public Boolean getAlreadyRebranded() {
		return alreadyRebranded;
	}

	public void setAlreadyRebranded(Boolean alreadyRebranded) {
		this.alreadyRebranded = alreadyRebranded;
	}

}