/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.util.Collection;
import java.util.Date;
import javax.ws.rs.core.GenericType;
import models.Subject;

/**
 *
 * @author irati
 */
public interface SubjectManager {

    public Subject findSubjectById(Integer id) throws FindErrorException;

    public Collection<Subject> findAllSubject() throws FindErrorException;

    public Collection<Subject> findSubjectsByName(String name) throws FindErrorException;

    public Collection<Subject> findSubjectsByTeacher(String teacherName) throws FindErrorException;

    public Collection<Subject> findSubjectByEndDate(Date endDate) throws FindErrorException;

    public Collection<Subject> findSubjectByInitDate(Date initDate) throws FindErrorException;

    public Collection<Subject> findSubjectsWithXUnits(Long number) throws FindErrorException;

    public Collection<Subject> findSubjectsWithEnrolledStudentsCount(Long number) throws FindErrorException;

    public Collection<Subject> findByEnrollments(Integer studentId) throws FindErrorException;

    public Collection<Subject> findSubjectsByTeacherId(Integer teacherId) throws FindErrorException;

    public void createSubject(Subject subject) throws CreateErrorException;

    public void deleteSubject(Subject subject) throws DeleteErrorException;

    public void updateSubject(Subject subject) throws UpdateErrorException;
}
