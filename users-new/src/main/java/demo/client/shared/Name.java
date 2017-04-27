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

    public Name() {
    }
    
    public Name(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.Long getId() {
        return this.id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }




}