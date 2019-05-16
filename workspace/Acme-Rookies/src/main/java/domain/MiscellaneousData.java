package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousData extends DomainEntity implements Cloneable {

	// Attributes

	private String text;
	private String attachements;

	// Getters and setters

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@URL
	public String getAttachements() {
		return attachements;
	}

	public void setAttachements(String attachements) {
		this.attachements = attachements;
	}
	
	@Override
	public MiscellaneousData clone() throws CloneNotSupportedException {
		MiscellaneousData miscellaneousClone = (MiscellaneousData) super.clone();
		miscellaneousClone.setId(0);
		miscellaneousClone.setVersion(0);
		return miscellaneousClone;
	}

}
