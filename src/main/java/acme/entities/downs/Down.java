package acme.entities.downs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.items.Item;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Down extends AbstractEntity{
	
	//Serialisation identifier
	
	protected static final long serialVersionUID = 1L;
	
	//Attributes  
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\w{2}\\:\\d{2}\\:(0?[1-9]|1[012])\\:\\d{2}\\:(0?[1-9]|[12][0-9]|3[01])$", message = "inventor.down.form.error.code-regex")
	
	protected String code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	protected Date creationMoment;
	
	@NotBlank
	@Length(min = 1, max=100)
	protected String subject;
	
	@NotBlank
	@Length(min = 1, max=255)
	protected String explanation;
	
	@NotNull
	protected Date startPeriod;
	
	@NotNull
	protected Date endPeriod;
	
	@Valid
	@NotNull
	protected Money quantity;
	
	@URL
	protected String additionalInfo;
	
	@OneToOne(optional = true)
    @Valid
    @NotNull
    protected Item item; // COMPONENT
 
}
