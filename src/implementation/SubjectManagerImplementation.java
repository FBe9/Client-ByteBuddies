/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.util.Collection;
import models.Subject;
import interfaces.SubjectManager;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import services.SubjectRESTClient;

/**
 *
 * @author irati
 */
public class SubjectManagerImplementation implements SubjectManager {

    private SubjectRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("SubjectManagerImplementation ");

    public SubjectManagerImplementation() {
        webClient = new SubjectRESTClient();
    }

    @Override
    public Subject findSubjectById(String id) throws FindErrorException {
        Subject subject = null;
        try {
            LOGGER.info("Find the subject with id: " + id);
            subject = webClient.find_XML(Subject.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception finding subject with id " + id + ": " + e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return subject;
    }

    @Override
    public Collection<Subject> findAllSubject() throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects");
            subjects = webClient.findAll_XML(new GenericType<Set<Subject>>() {});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception all subjects" + e.getMessage());
            throw new FindErrorException("Error finding all subjects" + e.getMessage());
        }
        return subjects;

    }

    @Override
    public Collection<Subject> findSubjectsByName(String name) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that cointain the name " + name);
            subjects = webClient.findSubjectsByName_XML(new GenericType<Set<Subject>>() {}, name);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception all subjects that contain the searched name" + e.getMessage());
            throw new FindErrorException("Exception all subjects that contain the searched name" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectsByTeacher(String teacherName) throws FindErrorException {
         Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects for the teacher " + teacherName);
            subjects = webClient.findSubjectsByTeacher_XML(new GenericType<Set<Subject>>() {}, teacherName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception finding all the subject for a teacher" + e.getMessage());
            throw new FindErrorException("Exception finding all the subject for a teacher" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectByEndDate(String endDate) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subject that have this end Date" + endDate);
            subjects = webClient.findSubjectsByEndDate_XML(new GenericType<Set<Subject>>() {},endDate );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subject with a specific end Date" + e.getMessage());
            throw new FindErrorException("Find all subject with a specific end Date" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectByInitDate(String initDate) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that have this init date" + initDate);
            subjects = webClient.findSubjectsByInitDate_XML(new GenericType<Set<Subject>>() {},initDate );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subject with a specific init date" + e.getMessage());
            throw new FindErrorException("Find all subject with a specific init date" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectsWithXUnits(String number) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that have " + number + " number of units");
            subjects = webClient.findSubjectsWithXUnits_XML(new GenericType<Set<Subject>>() {},number);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subjects with a specific number of units" + e.getMessage());
            throw new FindErrorException("Find all subjects with a specific number of units" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectsWithEnrolledStudentsCount(String number) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that have " + number + "of enrolled students");
            subjects = webClient.findSubjectsWithEnrolledStudentsCount_XML(new GenericType<Set<Subject>>() {},number);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subjects with a specific number of enrolled students" + e.getMessage());
            throw new FindErrorException("Find all subjects with a specific number of enrolled students" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findByEnrollments(String studentId) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that a student is enrolled");
            subjects = webClient.findByEnrollments_XML(new GenericType<Set<Subject>>() {}, studentId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Finding all subjects that a student is enrolled" + e.getMessage());
            throw new FindErrorException("Finding all subjects that a student is enrolled" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public Collection<Subject> findSubjectsByTeacherId(String teacherId) throws FindErrorException {
        Set<Subject> subjects = null;
        
         try {
            LOGGER.info("Find all subjects that a teacher has");
            subjects = webClient.findSubjectsByTeacherId_XML(new GenericType<Set<Subject>>() {}, teacherId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Finding all subjects that a teacher has" + e.getMessage());
            throw new FindErrorException("Finding all subjects that a teacher has" + e.getMessage());
        }
        return subjects;
    }

    @Override
    public void createSubject(Subject subject) throws CreateErrorException {
         try {
            LOGGER.info("Creating a subject");
            webClient.createSubject_XML(subject);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Error creating a subject" + e.getMessage());
            throw new CreateErrorException("Error creating a subject" + e.getMessage());
        }
    }

    @Override
    public void deleteSubject(String id) throws DeleteErrorException {
       try {
            LOGGER.info("Delete a subject");
            webClient.removeSubject(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Error deleting a subject" + e.getMessage());
            throw new DeleteErrorException("Error deleting a subject" + e.getMessage());
        }
    }

    @Override
    public void updateSubject(Subject subject) throws UpdateErrorException {
       try {
            LOGGER.info("Update a subject");
            webClient.updateSubject_XML(subject);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Error updating a subject" + e.getMessage());
            throw new UpdateErrorException("Error updating a subject" + e.getMessage());
        }
    }

}
