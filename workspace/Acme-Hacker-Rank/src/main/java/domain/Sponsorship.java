package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	/* Attributes */

	private CreditCard creditCard;
	private String banner;
	private String target;
	private Provider provider;

	/* Getters & Setters */

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@NotNull
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
