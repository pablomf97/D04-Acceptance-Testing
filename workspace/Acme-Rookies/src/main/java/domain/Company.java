package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Attributes

	private String commercialName;
	private Double score;

	// Getters and setters

	@NotBlank
	@Length(max=255)
	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	@Range(min = 0, max = 1)
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
