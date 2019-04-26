package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity implements Cloneable {

	//Attributes
	
	private String githubProfile;
	private String linkedIn;
	private String fullName;
	private String statement;
	private String phoneNumber;
	
	//Getters and setters
	
	@URL
	public String getGithubProfile() {
		return githubProfile;
	}
	public void setGithubProfile(String githubProfile) {
		this.githubProfile = githubProfile;
	}
	
	@URL
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	@NotBlank
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@NotBlank
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public PersonalData clone() throws CloneNotSupportedException {
		PersonalData personalClone = (PersonalData) super.clone();
		personalClone.setId(0);
		personalClone.setVersion(0);
		return personalClone;
	}

	
	
	
}
