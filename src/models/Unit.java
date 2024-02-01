package models;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing Unit. It has the following fields: unit id, name,
 * description, dateInit, dateEnd, hours, exercises list and subject object.
 *
 * @author Nerea
 */
@XmlRootElement
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the unit.
     */
    private final SimpleStringProperty id;
    /**
     * Name of the Unit.
     */
    private final SimpleStringProperty name;
    /**
     * The description
     */
    private final SimpleStringProperty description;
    /**
     * The Date of the first lesson of the Unit.
     */
    private final SimpleObjectProperty<Date> dateInit;
    /**
     * The Date of the last lesson of the Unit.
     */
    private final SimpleObjectProperty<Date> dateEnd;
    /**
     * Hours that the Unit lasts.
     */
    private final SimpleStringProperty hours;
    /**
     * Relational field containing subject of a unit.
     */
    private final SimpleObjectProperty<Subject> subject;

    //Setters and Getters
    /**
     * Gets the unit ID.
     *
     * @return An Integer with the unit ID.
     */
    public String getId() {
        return this.id.get();
    }

    /**
     * Sets the unit ID.
     *
     * @param id the unit ID to be set.
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Gets the unit Name.
     *
     * @return A String with the unit name.
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Sets the unit Name.
     *
     * @param name the unit name to be set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Gets the unit Description.
     *
     * @return A String with the unit description.
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * Sets the unit Description.
     *
     * @param description the unit description to be set.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Gets the unit DateInit.
     *
     * @return A Date with the unit dateInit.
     */
    public Date getDateInit() {
        return this.dateInit.get();
    }

    /**
     * Sets the unit DateInit.
     *
     * @param dateInit the unit dateInit to be set.
     */
    public void setDateInit(Date dateInit) {
        this.dateInit.set(dateInit);
    }

    /**
     * Gets the unit DateEnd.
     *
     * @return A Date with the unit dateEnd.
     */
    public Date getDateEnd() {
        return this.dateEnd.get();
    }

    /**
     * Sets the unit DateEnd.
     *
     * @param dateEnd the unit dateEnd to be set.
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    /**
     * Gets the unit Hours.
     *
     * @return A String with the unit hours.
     */
    public String getHours() {
        return this.hours.get();
    }

    /**
     * Sets the unit Hours.
     *
     * @param hours the unit hours to be set.
     */
    public void setHours(String hours) {
        this.hours.set(hours);
    }

    /**
     * Gets the unit Subject.
     *
     * @return A Subject object with the unit Subject.
     */
    public Subject getSubject() {
        return this.subject.get();
    }

    /**
     * Sets the unit Subject.
     *
     * @param subject the unit subject to be set.
     */
    public void setSubject(Subject subject) {
        this.subject.set(subject);
    }

    //Constructors
    /**
     * Creates a new instance of the Unit class with specified attributes.
     *
     * @param id the unit id.
     * @param name the name of the unit.
     * @param description the description of the unit.
     * @param dateInit the dateInit of the unit.
     * @param dateEnd the dateEnd of the unit.
     * @param hours the hours of the unit.
     * @param subject the subject of the unit.
     */
    public Unit(String id, String name, String description, Date dateInit, Date dateEnd, String hours, Subject subject) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.dateInit = new SimpleObjectProperty(dateInit);
        this.dateEnd = new SimpleObjectProperty(dateEnd);
        this.hours = new SimpleStringProperty(hours);
        this.subject = new SimpleObjectProperty(subject);
    }

    /**
     * Creates a new instance of the Unit class with default constructor.
     */
    public Unit() {
        this.id = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.dateInit = new SimpleObjectProperty<>();
        this.dateEnd = new SimpleObjectProperty<>();
        this.hours = new SimpleStringProperty();
        this.subject = new SimpleObjectProperty<>();
    }

    //HasCode
    /**
     * Computes the hash code for this object.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //Equals
    /**
     * Checks if this object is equal to another object.
     *
     * @param object the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unit)) {
            return false;
        }
        Unit other = (Unit) object;

        if (!((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))) {
            return false;
        }
        return true;
    }

    /**
     * This method shows only the unit name when you need to see a unit by
     * string.
     *
     * @return A string with the unit name.
     */
    @Override
    public String toString() {
        return name.getValue();
    }
}
