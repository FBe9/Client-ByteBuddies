/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.ObservableSet;
/**
 *
 * @author irati
 */
public class Student extends User {
    private final SimpleObjectProperty<LevelType> levelType;
    private final SimpleSetProperty<Enrolled> enrollments;
    
    public Student() {
        super();
        this.levelType = new SimpleObjectProperty();
        this.enrollments = new SimpleSetProperty();
        
    }

    public Student(LevelType levelType, ObservableSet<Subject> enrollments) {
        super();
        this.levelType = new SimpleObjectProperty(levelType);
        this.enrollments = new SimpleSetProperty(enrollments);
    }

    /**
     * Gets the level type of the student.
     *
     * @return The level type of the student.
     */
    public LevelType getLevelType() {
        return this.levelType.get();
    }

    /**
     * Sets the level type of the student.
     *
     * @param levelType The level type to set for the student.
     */
    public void setLevelType(LevelType levelType) {
        this.levelType.set(levelType);
    }


    /**
     * Gets the set of enrollments associated with the student.
     *
     * @return The set of enrollments associated with the student.
     */
    public ObservableSet<Enrolled> getEnrollments() {
        return this.enrollments.get();
    }

    /**
     * Sets the set of enrollments associated with the student.
     *
     * @param enrollments The set of enrollments to be associated with the
     * student.
     */
    public void setEnrollments(ObservableSet<Enrolled> enrollments) {
        this.enrollments.set(enrollments);
    }
    
    
}
