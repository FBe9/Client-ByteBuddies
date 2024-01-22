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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import services.SubjectRESTClient;

/**
 *Implementation of the SubjectManager interface for subject management.
 * @author irati
 */
public class SubjectManagerImplementation implements SubjectManager {

    private final SubjectRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("SubjectManagerImplementation ");

    /**
     * Constructor to initialize the SubjectManagerImplementation.
     */
    public SubjectManagerImplementation() {
        webClient = new SubjectRESTClient();
    }

    /**
     * Method to find a subject by id.
     *
     * @param id The unique identifier of the subject to find.
     * @return The subject with the specified ID.
     * @throws FindErrorException If an error occurs while finding the subject.
     */
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

    /**
     * Method to find all subjects
     *
     * @return A collection containing all subjects.
     * @throws FindErrorException If an error occurs while finding all subjects.
     */
    @Override
    public Collection<Subject> findAllSubjects() throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects");
            subjects = webClient.findAll_XML(new GenericType<Set<Subject>>() {
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception all subjects" + e.getMessage());
            throw new FindErrorException("Error finding all subjects" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Method to find subject by its name
     *
     * @param name The name to search for in subjects.
     * @return A collection of subjects that contain the specified name.
     * @throws FindErrorException If an error occurs while finding subjects by
     * name.
     */
    @Override
    public Collection<Subject> findSubjectsByName(String name) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that contain the name " + name);
            subjects = webClient.findSubjectsByName_XML(new GenericType<Set<Subject>>() {
            }, name);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception finding all subjects that contain the searched name" + e.getMessage());
            throw new FindErrorException("Exception all subjects that contain the searched name" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Method to find subject by teacher name.
     *
     * @param teacherName The name of the teacher for whom to find subjects.
     * @return A collection of subjects for the specified teacher.
     * @throws FindErrorException If an error occurs while finding subjects by
     * teacher.
     */
    @Override
    public Collection<Subject> findSubjectsByTeacher(String teacherName) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects for the teacher " + teacherName);
            subjects = webClient.findSubjectsByTeacher_XML(new GenericType<Set<Subject>>() {
            }, teacherName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Exception finding all the subject for a teacher" + e.getMessage());
            throw new FindErrorException("Exception finding all the subject for a teacher" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Method to find a subject by its end date.
     *
     * @param endDate The end date to search for in subjects.
     * @return A collection of subjects with the specified end date.
     * @throws FindErrorException If an error occurs while finding subjects by
     * end date.
     */
    @Override
    public Collection<Subject> findSubjectByEndDate(String endDate) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subject that have this end Date" + endDate);
            subjects = webClient.findSubjectsByEndDate_XML(new GenericType<Set<Subject>>() {
            }, endDate);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subject with a specific end Date" + e.getMessage());
            throw new FindErrorException("Find all subject with a specific end Date" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Method to find a subject by its init date.
     *
     * @param initDate The start date to search for in subjects.
     * @return A collection of subjects with the specified start date.
     * @throws FindErrorException If an error occurs while finding subjects by
     * start date.
     */
    @Override
    public Collection<Subject> findSubjectByInitDate(String initDate) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that have this init date" + initDate);
            subjects = webClient.findSubjectsByInitDate_XML(new GenericType<Set<Subject>>() {
            }, initDate);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subject with a specific init date" + e.getMessage());
            throw new FindErrorException("Find all subject with a specific init date" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Find subjects that have at least x number of units
     *
     * @param number The number of units to search for in subjects.
     * @return A collection of subjects with the specified number of units.
     * @throws FindErrorException If an error occurs while finding subjects by
     * unit count.
     */
    @Override
    public Collection<Subject> findSubjectsWithXUnits(String number) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that have " + number + " number of units");
            subjects = webClient.findSubjectsWithXUnits_XML(new GenericType<Set<Subject>>() {
            }, number);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subjects with a specific number of units" + e.getMessage());
            throw new FindErrorException("Find all subjects with a specific number of units" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Find subjects that have x number of students.
     *
     * @param number The number of enrolled students to search for in subjects.
     * @return A collection of subjects with the specified number of enrolled
     * students.
     * @throws FindErrorException If an error occurs while finding subjects by
     * enrolled student count.
     */
    @Override
    public Collection<Subject> findSubjectsWithEnrolledStudentsCount(String number) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that have " + number + "of enrolled students");
            subjects = webClient.findSubjectsWithEnrolledStudentsCount_XML(new GenericType<Set<Subject>>() {
            }, number);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Find all subjects with a specific number of enrolled students" + e.getMessage());
            throw new FindErrorException("Find all subjects with a specific number of enrolled students" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Find the subjects that a student is enrolled
     *
     * @param studentId The ID of the student to search for in subjects.
     * @return A collection of subjects in which the specified student is
     * enrolled.
     * @throws FindErrorException If an error occurs while finding subjects by
     * student enrollment.
     */
    @Override
    public Collection<Subject> findByEnrollments(String studentId) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that a student is enrolled");
            subjects = webClient.findByEnrollments_XML(new GenericType<Set<Subject>>() {
            }, studentId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Finding all subjects that a student is enrolled" + e.getMessage());
            throw new FindErrorException("Finding all subjects that a student is enrolled" + e.getMessage());
        }
        return subjects;
    }

    /**
     * Find subjects by teacher's id.
     *
     * @param teacherId The ID of the teacher to search for in subjects.
     * @return A collection of subjects taught by the specified teacher.
     * @throws FindErrorException If an error occurs while finding subjects by
     * teacher ID.
     */
    @Override
    public Collection<Subject> findSubjectsByTeacherId(String teacherId) throws FindErrorException {
        Set<Subject> subjects = null;

        try {
            LOGGER.info("Find all subjects that a teacher has");
            subjects = webClient.findSubjectsByTeacherId_XML(new GenericType<Set<Subject>>() {
            }, teacherId);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "SubjectManager: Finding all subjects that a teacher has" + e.getMessage());
            throw new FindErrorException("Finding all subjects that a teacher has" + e.getMessage());
        }
        return subjects;
    }

    /**
     *
     * @param subject The subject to create.
     * @throws CreateErrorException If an error occurs while creating the
     * subject.
     */
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

    /**
     *
     * @param id The unique identifier of the subject to delete.
     * @throws DeleteErrorException if an error while deleting the subject
     */
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

    /**
     *
     * @param subject The subject to update.
     * @throws UpdateErrorException if an error while updating the subject.
     */
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
