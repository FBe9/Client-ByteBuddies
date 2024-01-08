package models;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Entity representing Unit. It has the following
 * fields: unit id, name, description, dateInit, dateEnd, hours, exercises list and subject object. 
 *
 * @author Nerea
 */

public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the unit.
     */
    private SimpleIntegerProperty id;
    /**
     * Name of the Unit.
     */
    private SimpleStringProperty name;
    /**
     * The description 
     */
    private SimpleStringProperty description;
    /**
     * The Date of the first lesson of the Unit.
     */
    private SimpleObjectProperty<Date> dateInit;
    /**
     * The Date of the last lesson of the Unit.
     */
    private SimpleObjectProperty<Date> dateEnd;
    /**
     * Hours that the Unit lasts.
     */
    private SimpleIntegerProperty hours;
    /**
     * Relational field containing exercises of the unit.
     */
    private SimpleSetProperty<Exercise> exercises;
    /**
     * Relational field containing subject of a unit.
     */
    private SimpleObjectProperty<Subject> subject;

    //Setters and Getters
    /**
     * Gets the unit ID.
     *
     * @return An Integer with the unit ID.
     */
    public SimpleIntegerProperty getId() {
        return id;
    }
    
    /**
     * Sets the unit ID.
     *
     * @param id the unit ID to be set.
     */
    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }
    
    /**
     * Gets the unit Name. 
     * 
     * @return A String with the unit name.
     */
    public SimpleStringProperty getName() {
        return name;
    }
    
    /**
     * Sets the unit Name.
     * 
     * @param name the unit name to be set
     */
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }
    
    /**
     * Gets the unit Description.  
     * 
     * @return A String with the unit description.
     */
    public SimpleStringProperty getDescription() {
        return description;
    }
    
    /**
     * Sets the unit Description.
     * 
     * @param description the unit description to be set.
     */
    public void setDescription(SimpleStringProperty description) {
        this.description = description;
    }
    
    /**
     * Gets the unit DateInit. 
     * 
     * @return A Date with the unit dateInit.
     */
    public SimpleObjectProperty<Date> getDateInit() {
        return dateInit;
    }
    
    /**
     * Sets the unit DateInit.
     * 
     * @param dateInit the unit dateInit to be set.
     */
    public void setDateInit(SimpleObjectProperty<Date> dateInit) {
        this.dateInit = dateInit;
    }
    
    /**
     * Gets the unit DateEnd. 
     * 
     * @return A Date with the unit dateEnd.
     */
    public SimpleObjectProperty<Date> getDateEnd() {
        return dateEnd;
    }
    
    /**
     * Sets the unit DateEnd.
     * 
     * @param dateEnd the unit dateEnd to be set.
     */
    public void setDateEnd(SimpleObjectProperty<Date> dateEnd) {
        this.dateEnd = dateEnd;
    }
    
    /**
     * Gets the unit Hours.  
     * 
     * @return An Integer with the unit hours.
     */
    public SimpleIntegerProperty getHours() {
        return hours;
    }
    
    /**
     * Sets the unit Hours.
     * 
     * @param hours the unit hours to be set.
     */
    public void setHours(SimpleIntegerProperty hours) {
        this.hours = hours;
    }
    
    /**
     * Gets the unit Exercises.  
     * 
     * @return A Set with the unit exercises.
     */
    public SimpleSetProperty<Exercise> getExercises() {
        return exercises;
    }
    
    /**
     * Sets the unit Exercises.
     * 
     * @param exercises the unit exercises to be set.
     */
    public void setExercises(SimpleSetProperty<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Gets the unit Subject. 
     * 
     * @return A Subject object with the unit Subject.
     */
    public SimpleObjectProperty<Subject> getSubject() {
        return subject;
    }
    
    /**
     * Sets the unit Subject.
     * 
     * @param subject the unit subject to be set.
     */
    public void setSubject(SimpleObjectProperty<Subject> subject) {
        this.subject = subject;
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
     * @param exercises the exercises of the unit.
     * @param subject the subject of the unit.
     */
    public Unit(SimpleIntegerProperty id, SimpleStringProperty name, SimpleStringProperty description, SimpleObjectProperty<Date> dateInit, SimpleObjectProperty<Date> dateEnd, SimpleIntegerProperty hours, SimpleSetProperty<Exercise> exercises, SimpleObjectProperty<Subject> subject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.hours = hours;
        this.exercises = exercises;
        this.subject = subject;
    }

    /**
     * Creates a new instance of the Unit class with default constructor.
     */
    public Unit() {
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
}
