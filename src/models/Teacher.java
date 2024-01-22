/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableSet;
import javax.persistence.DiscriminatorValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author irati
 */
@XmlRootElement(name = "Teacher")
public class Teacher extends User {
    private final SimpleObjectProperty<StudiesType> studiesType;
    private final SimpleStringProperty qualifications;
    private final SimpleSetProperty<Subject> subjects;

    public Teacher(){
        super();
        this.studiesType = new SimpleObjectProperty();
        this.qualifications = new SimpleStringProperty();
        this.subjects = new SimpleSetProperty();
    }
    
    public Teacher(StudiesType studiesType, String qualifications, ObservableSet<Subject> subjects) {
        super();
        this.studiesType =  new SimpleObjectProperty(studiesType);
        this.qualifications = new SimpleStringProperty(qualifications);
        this.subjects = new SimpleSetProperty(subjects);
    }
    
      /**
     * Gets the type of studies the teacher is qualified in.
     *
     * @return The studies type of the teacher.
     */
    public StudiesType getStudiesType() {
        return this.studiesType.get();
    }

    /**
     * Sets the type of studies the teacher is qualified in.
     *
     * @param studiesType The studies type to set for the teacher.
     */
    public void setStudiesType(StudiesType studiesType) {
        this.studiesType.set(studiesType);
    }

    /**
     * Gets the qualifications of the teacher.
     *
     * @return The qualifications of the teacher.
     */
    public String getQualifications() {
        return this.qualifications.get();
    }

    /**
     * Sets the qualifications of  the teacher.
     *
     * @param qualifications The qualifications to set for the teacher.
     */
    public void setQualifications(String qualifications) {
        this.qualifications.set(qualifications);
    }

    /**
     * Gets the set of subjects associated with the teacher.
     *
     * @return The set of subjects associated with the teacher.
     */
    public ObservableSet<Subject> getSubjects() {
        return this.subjects.get();
    }

    /**
     * Sets the set of subjects associated with the teacher.
     *
     * @param subjects The set of subjects to be associated with the teacher.
     */
    public void setSubjects(ObservableSet<Subject> subjects) {
        this.subjects.set(subjects);
    }

    @Override
    public String toString() {
        return super.toString();
    }

   
  
    
}
