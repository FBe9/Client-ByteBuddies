package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.util.Collection;
import models.Exam;

/**
 * Interface for managing exams.
 *
 * @author Alex
 */
public interface ExamInterface {

    /**
     * Create method to add a new exam.
     *
     * @param exam The new exam to add.
     * @throws CreateErrorException When a creation error occurs.
     */
    public void createExam(Exam exam) throws CreateErrorException;

    /**
     * Update method to change the information of a specific exam.
     *
     * @param exam The exam to update.
     * @throws UpdateErrorException When an update error occurs.
     */
    public void updateExam(Exam exam) throws UpdateErrorException;

    /**
     * Delete method to remove an exam from the database.
     *
     * @param exam The exam to remove.
     * @throws DeleteErrorException When a deletion error occurs.
     */
    public void deleteExam(Exam exam) throws DeleteErrorException;

    /**
     * Find method to search for all the exams.
     *
     * @return A Collection object with all the found exams.
     * @throws FindErrorException FindErrorException When a find error occurs.
     */
    public Collection<Exam> findAllExams() throws FindErrorException;

    /**
     * Find method to search for a specific exam with a given ID.
     *
     * @param id The ID of the exam to look for.
     * @return The found exam.
     * @throws FindErrorException When a find error occurs.
     */
    public Exam findExamById(Integer id) throws FindErrorException;

    /**
     * Find method to search for all exams containing the given string in their
     * description.
     *
     * @param description The string to search for.
     * @return A Collection object with all the found exams.
     * @throws FindErrorException When a find error occurs.
     */
    public Collection<Exam> findByDescription(String description) throws FindErrorException;

    /**
     * Find method to search all exams belonging to a specific subject of the
     * given ID.
     *
     * @param subject The given subject ID to search for.
     * @return A Collection object containing all found exams.
     * @throws FindErrorException When a find error occurs.
     */
    public Collection<Exam> findBySubject(String subject) throws FindErrorException;

}
