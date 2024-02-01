package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import interfaces.ExamInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Exam;
import services.ExamRESTClient;

/**
 * Implementation of the ExamInterface.
 *
 * @author Alex
 */
public class ExamInterfaceImplementation implements ExamInterface {

    private ExamRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("ExamInterfaceImplementation");

    /**
     * Constructor to initialise the web client.
     */
    public ExamInterfaceImplementation() {
        webClient = new ExamRESTClient();
    }

    /**
     * Create method to add a new exam.
     *
     * @param exam The new exam to add.
     * @throws CreateErrorException When a creation error occurs.
     */
    @Override
    public void createExam(Exam exam) throws CreateErrorException {
        try {
            LOGGER.info("Creating an exam");
            webClient.createExam_XML(exam);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExamInterface: Error creating an exam: {0}", e.getMessage());
            throw new CreateErrorException("Error creating an exam: " + e.getMessage());
        }
    }

    /**
     * Update method to change the information of a specific exam.
     *
     * @param exam The exam to update.
     * @throws UpdateErrorException When an update error occurs.
     */
    @Override
    public void updateExam(Exam exam) throws UpdateErrorException {
        try {
            LOGGER.log(Level.INFO, "Updating exam with id {0}", exam.getId());
            webClient.updateExam_XML(exam);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExamInterface: Error updating exam " + exam.getId() + ": {0}", e.getMessage());
            throw new UpdateErrorException("Error updating exam " + exam.getId() + ": " + e.getMessage());
        }
    }

    /**
     * Delete method to remove an exam from the database.
     *
     * @param exam The exam to remove.
     * @throws DeleteErrorException When a deletion error occurs.
     */
    @Override
    public void deleteExam(Exam exam) throws DeleteErrorException {
        try {
            LOGGER.log(Level.INFO, "Deleteing exam {0}", exam.getId());
            webClient.deleteExam(exam.getId());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting exam {0}", exam.getId());
            throw new DeleteErrorException("Error deleting exam " + exam.getId());
        }
    }

    /**
     * Find method to search for all the exams.
     *
     * @return A Collection object with all the found exams.
     * @throws FindErrorException When a find error occurs.
     */
    @Override
    public Collection<Exam> findAllExams() throws FindErrorException {
        List<Exam> exams = null;
        try {
            LOGGER.info("Finding all exams...");
            exams = webClient.findAll_XML(new GenericType<List<Exam>>() {
            });
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "Error finding exams.");
            throw new FindErrorException("Error finding exams");
        }
        return exams;
    }

    /**
     * Find method to search for a specific exam with a given ID.
     *
     * @param id The ID of the exam to look for.
     * @return The found exam.
     * @throws FindErrorException When a find error occurs.
     */
    @Override
    public Exam findExamById(Integer id) throws FindErrorException {
        Exam exam = null;
        try {
            LOGGER.log(Level.INFO, "Searching for exam with id: {0}", id);
            exam = webClient.find_XML(Exam.class, id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "Error finding exam with id: {0}", id);
            throw new FindErrorException("Error finding exam with id: " + id);
        }
        return exam;
    }

    /**
     * Find method to search for all exams containing the given string in their
     * description.
     *
     * @param description The string to search for.
     * @return A Collection object with all the found exams.
     * @throws FindErrorException When a find error occurs.
     */
    @Override
    public Collection<Exam> findByDescription(String description) throws FindErrorException {
        Set<Exam> exams = null;
        try {
            LOGGER.log(Level.INFO, "Searching for exam like '{0}'", description);
            exams = webClient.findByDescription_XML(new GenericType<Set<Exam>>() {
            }, description);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE, "Error finding exam like ''{0}''", description);
            throw new FindErrorException("Error finding exam like '" + description + "'");
        }
        return exams;
    }

    /**
     * Find method to search all exams belonging to a specific subject of the
     * given ID.
     *
     * @param subjectId The given subject ID to search for.
     * @return A Collection object containing all found exams.
     * @throws FindErrorException When a find error occurs.
     */
    @Override
    public Collection<Exam> findBySubject(String subjectId) throws FindErrorException {
        Set<Exam> exams = null;
        try {
            LOGGER.log(Level.INFO, "Searching exams for subject with id {0}", subjectId);
            exams = webClient.findBySubject_XML(new GenericType<Set<Exam>>() {
            }, subjectId);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "Error finding exams for subject {0}", subjectId);
            throw new FindErrorException("Error finding exams for subject " + subjectId);
        }
        return exams;
    }
}
