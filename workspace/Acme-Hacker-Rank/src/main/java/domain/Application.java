package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Attributes

	private Date applicationMoment;
	private String explanation;
	private String linkCode;
	private Date submitMoment;
	private String status;
	private Problem problem;
	private Hacker hacker;
	private Position position;
	private Curricula copyCurricula;

	// Getters and setters

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getApplicationMoment() {
		return applicationMoment;
	}

	public void setApplicationMoment(Date applicationMoment) {
		this.applicationMoment = applicationMoment;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@URL
	public String getLinkCode() {
		return linkCode;
	}

	public void setLinkCode(String linkCode) {
		this.linkCode = linkCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSubmitMoment() {
		return submitMoment;
	}

	public void setSubmitMoment(Date submitMoment) {
		this.submitMoment = submitMoment;
	}

	@NotBlank
	@Pattern(regexp = "\\b(PENDING|SUBMITTED|ACCEPTED|REJECTED)\\b")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Valid
	@NotNull
	@OneToOne
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Valid
	@OneToOne(optional = true)
	public Curricula getCopyCurricula() {
		return copyCurricula;
	}

	public void setCopyCurricula(Curricula copyCurricula) {
		this.copyCurricula = copyCurricula;
	}

}
