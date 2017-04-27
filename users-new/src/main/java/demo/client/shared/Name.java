package demo.client.shared;

/**
 * This class was automatically generated by the data modeler tool.
 */

@javax.persistence.Entity
public class Name implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "NAME_ID_GENERATOR")
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(sequenceName = "NAME_ID_SEQ", name = "NAME_ID_GENERATOR")
	private java.lang.Long id;

	@org.kie.api.definition.type.Label(value = "First Name")
	private java.lang.String first;

	@org.kie.api.definition.type.Label(value = "Last Name")
	private java.lang.String last;

	public Name() {
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getFirst() {
		return this.first;
	}

	public void setFirst(java.lang.String first) {
		this.first = first;
	}

	public java.lang.String getLast() {
		return this.last;
	}

	public void setLast(java.lang.String last) {
		this.last = last;
	}

	public Name(java.lang.Long id, java.lang.String first, java.lang.String last) {
		this.id = id;
		this.first = first;
		this.last = last;
	}

}