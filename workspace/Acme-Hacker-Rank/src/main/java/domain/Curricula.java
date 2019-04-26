package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes

	private Collection<MiscellaneousData> miscellaneousData;
	private Collection<EducationData> educationData;
	private Collection<PositionData> positionData;
	private PersonalData personalData;
	private Boolean isCopy;
	private Hacker hacker;

	// Getters and setters

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(
			Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	@NotNull
	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	@Valid
	@ManyToOne(optional=false)
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

	
}
