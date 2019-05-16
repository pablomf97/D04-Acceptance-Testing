package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	private String providerMake;

	@NotBlank
	public String getProviderMake() {
		return providerMake;
	}

	public void setProviderMake(String providerMake) {
		this.providerMake = providerMake;
	}

}