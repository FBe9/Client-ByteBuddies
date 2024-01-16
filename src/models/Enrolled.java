/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author irati
 */
public class Enrolled {
    private final SimpleObjectProperty<EnrolledId> id;
    private final SimpleObjectProperty<Student> student;
    private final SimpleObjectProperty<Subject> subject;
    private final SimpleBooleanProperty isMatriculate;
    
    public Enrolled(){
        this.id = new SimpleObjectProperty();
        this.student = new SimpleObjectProperty();
        this.subject = new SimpleObjectProperty();
        this.isMatriculate = new SimpleBooleanProperty();
    }
    public Enrolled(EnrolledId id, Student student, Subject subject, Boolean isMatriculate) {
        this.id =  new SimpleObjectProperty(id);
        this.student =  new SimpleObjectProperty(student);
        this.subject =  new SimpleObjectProperty(subject);
        this.isMatriculate = new SimpleBooleanProperty(isMatriculate);
    }
    
    // Setters and Getters
    /**
     * Gets the composite key for the enrollment.
     *
     * @return the enrollment ID
     */
    public EnrolledId getId() {
        return id.get();
    }

    /**
     * Sets the composite key for the enrollment.
     *
     * @param id the enrollment ID to be set
     */
    public void setId(EnrolledId id) {
        this.id.set(id);
    }

    /**
     * Gets the associated subject.
     *
     * @return the subject
     */
    public Subject getSubject() {
        return this.subject.get();
    }

    /**
     * Sets the associated subject.
     *
     * @param subject the subject to be set
     */
    public void setSubject(Subject subject) {
        this.subject.set(subject);
    }

    /**
     * Gets the associated student.
     *
     * @return the student
     */
    public Student getStudent() {
        return this.student.get();
    }

    /**
     * Sets the associated student.
     *
     * @param student the student to be set
     */
    public void setStudent(Student student) {
        this.student.set(student);
    }

    /**
     * Gets the flag indicating whether the student is matriculated in the
     * subject.
     *
     * @return true if the student is matriculated, false otherwise
     */
    public Boolean getIsMatriculate() {
        return this.isMatriculate.get();
    }

    /**
     * Sets the flag indicating whether the student is matriculated in the
     * subject.
     *
     * @param isMatriculate true if the student is matriculated, false otherwise
     */
    public void setIsMatriculate(Boolean isMatriculate) {
        this.isMatriculate.set(isMatriculate);
    }
}
