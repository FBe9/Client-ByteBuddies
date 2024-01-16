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
import java.util.Set;
import java.util.logging.Level;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Subject;

/**
 *
 * @author irati
 */
public interface SubjectManager {

  
    public Subject findSubjectById(String id) throws FindErrorException;

    public Collection<Subject> findAllSubject() throws FindErrorException;

    public Collection<Subject> findSubjectsByName(String name) throws FindErrorException;
    public Collection<Subject> findSubjectsByTeacher(String teacherName) throws FindErrorException;
  
    public Collection<Subject> findSubjectByEndDate(String endDate) throws FindErrorException;

 
    public Collection<Subject> findSubjectByInitDate(String initDate) throws FindErrorException;

 
    public Collection<Subject> findSubjectsWithXUnits(String number) throws FindErrorException;
   
    public Collection<Subject> findSubjectsWithEnrolledStudentsCount(String number) throws FindErrorException;

    public Collection<Subject> findByEnrollments(String studentId) throws FindErrorException;

    public Collection<Subject> findSubjectsByTeacherId(String teacherId) throws FindErrorException;

    public void createSubject(Subject subject) throws CreateErrorException;

    public void deleteSubject(String id) throws DeleteErrorException;
    public void updateSubject(Subject subject) throws UpdateErrorException;


  
}
