/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author irati
 */
public class EnrolledId implements Serializable {

    private final SimpleIntegerProperty studentId;
    private final SimpleIntegerProperty subjectId;

    public EnrolledId() {
        this.studentId = new SimpleIntegerProperty();
        this.subjectId = new SimpleIntegerProperty();
    }

    public EnrolledId(Integer studentId, Integer subjectId) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.subjectId = new SimpleIntegerProperty(subjectId);
    }

    public Integer getStudentId() {
        return this.studentId.get();
    }

    public void setStudentId(Integer studentId) {
        this.studentId.set(studentId);
    }

    public Integer getSubjectId() {
        return this.subjectId.get();
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId.set(subjectId);
    }
}
