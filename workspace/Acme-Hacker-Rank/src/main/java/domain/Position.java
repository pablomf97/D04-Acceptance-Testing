
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	// Attributes

	private String				title;
	private String				description;
	private Date				deadline;
	private String				profileRequired;
	private String				technologiesRequired;
	private Double				salary;
	private String				ticker;
	private String				skillsRequired;
	private Company				company;
	private Boolean				isDraft;
	private Boolean				isCancelled;

	private Collection<Problem>	problems;


	// Getters and setters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotBlank
	public String getProfileRequired() {
		return this.profileRequired;
	}

	public void setProfileRequired(final String profileRequired) {
		this.profileRequired = profileRequired;
	}

	@NotBlank
	public String getTechnologiesRequired() {
		return this.technologiesRequired;
	}

	public void setTechnologiesRequired(final String technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}

	@Range(min = 0)
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}


	//@Pattern(regexp = "\\[A-Z]{4}-d{4}")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getSkillsRequired() {
		return this.skillsRequired;
	}

	public void setSkillsRequired(final String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	@NotNull
	public Boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	@NotNull
	public Boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	@ManyToMany
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

}
