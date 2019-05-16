
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity implements Cloneable {

	//Attributes

	private String	githubProfile;
	private String	linkedIn;
	private String	fullName;
	private String	statement;
	private String	phoneNumber;


	//Getters and setters

	@URL
	@Pattern(regexp = "http(s)?:\\/\\/(www\\.)?github\\.com\\/[A-z0-9_-]+\\/?")
	public String getGithubProfile() {
		return this.githubProfile;
	}
	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@URL
	@Pattern(regexp = "http(s)?:\\/\\/([\\w]+\\.)?linkedin\\.com\\/in\\/[A-z0-9_-]+\\/?")
	public String getLinkedIn() {
		return this.linkedIn;
	}
	public void setLinkedIn(final String linkedIn) {
		this.linkedIn = linkedIn;
	}

	@NotBlank
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Length(max=510)
	public String getStatement() {
		return this.statement;
	}
	public void setStatement(final String statement) {
		this.statement = statement;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public PersonalData clone() throws CloneNotSupportedException {
		final PersonalData personalClone = (PersonalData) super.clone();
		personalClone.setId(0);
		personalClone.setVersion(0);
		return personalClone;
	}

}
