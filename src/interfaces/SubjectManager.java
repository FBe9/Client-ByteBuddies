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
import models.Subject;

/**
 * Interface for managing subjects.
 *
 * @author irati
 */
public interface SubjectManager {

    /**
     * Finds a subject by its ID.
     *
     * @param id The ID of the subject to find.
     * @return The found subject.
     * @throws FindErrorException If an error occurs while finding the subject.
     */
    public Subject findSubjectById(String id) throws FindErrorException;

    /**
     * Finds all subjects.
     *
     * @return A collection of all subjects.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findAllSubjects() throws FindErrorException;

    /**
     * Finds subjects by name.
     *
     * @param name The name of the subjects to find.
     * @return A collection of subjects with the specified name.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectsByName(String name) throws FindErrorException;

    /**
     * Finds subjects by teacher's name.
     *
     * @param teacherName The name of the teacher.
     * @return A collection of subjects taught by the specified teacher.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectsByTeacher(String teacherName) throws FindErrorException;

    /**
     * Finds subjects by end date.
     *
     * @param endDate The end date of the subjects to find.
     * @return A collection of subjects with the specified end date.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectByEndDate(String endDate) throws FindErrorException;

    /**
     * Finds subjects by start date.
     *
     * @param initDate The start date of the subjects to find.
     * @return A collection of subjects with the specified start date.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectByInitDate(String initDate) throws FindErrorException;

    /**
     * Finds subjects with a certain number of units.
     *
     * @param number The number of units.
     * @return A collection of subjects with the specified number of units.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectsWithXUnits(String number) throws FindErrorException;

    /**
     * Finds subjects with a certain count of enrolled students.
     *
     * @param number The count of enrolled students.
     * @return A collection of subjects with the specified count of enrolled
     * students.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectsWithEnrolledStudentsCount(String number) throws FindErrorException;

    /**
     * Finds subjects that a student is enrolled.
     *
     * @param studentId The ID of the student.
     * @return A collection of subjects in which the specified student is
     * enrolled.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findByEnrollments(String studentId) throws FindErrorException;

    /**
     * Finds subjects by teacher ID.
     *
     * @param teacherId The ID of the teacher.
     * @return A collection of subjects taught by the specified teacher.
     * @throws FindErrorException If an error occurs while finding subjects.
     */
    public Collection<Subject> findSubjectsByTeacherId(String teacherId) throws FindErrorException;

    /**
     * Creates a new subject.
     *
     * @param subject The subject to create.
     * @throws CreateErrorException If an error occurs while creating the
     * subject.
     */
    public void createSubject(Subject subject) throws CreateErrorException;

    /**
     * Deletes a subject by its ID.
     *
     * @param id The ID of the subject to delete.
     * @throws DeleteErrorException If an error occurs while deleting the
     * subject.
     */
    public void deleteSubject(String id) throws DeleteErrorException;

    /**
     * Updates a subject.
     *
     * @param subject The subject to update.
     * @throws UpdateErrorException If an error occurs while updating the
     * subject.
     */
    public void updateSubject(Subject subject) throws UpdateErrorException;
}
