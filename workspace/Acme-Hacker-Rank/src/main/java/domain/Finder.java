package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes

	private String keyWord;
	private Date deadline;
	private Double minimumSalary;
	private Date maximumDeadline;
	private Collection<Position> results;
	private Date searchMoment;

	// Getters and setters

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Double getMinimumSalary() {
		return minimumSalary;
	}

	public void setMinimumSalary(Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaximumDeadline() {
		return maximumDeadline;
	}

	public void setMaximumDeadline(Date maximumDeadline) {
		this.maximumDeadline = maximumDeadline;
	}

	@Valid
	@ManyToMany
	public Collection<Position> getResults() {
		return results;
	}

	public void setResults(Collection<Position> results) {
		this.results = results;
	}
	
	public Date getSearchMoment() {
		return searchMoment;
	}

	public void setSearchMoment(Date searchMoment) {
		this.searchMoment = searchMoment;
	}
	

}
