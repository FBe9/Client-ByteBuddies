package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.UpdateErrorException;
import interfaces.EnrolledInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Enrolled;
import services.EnrolledRESTClient;

/**
 *
 * @author irati
 */
public class EnrolledInterfaceImplementation implements EnrolledInterface {

    /**
     * The webclient for EnrolledRESTClient.
     */
    private EnrolledRESTClient webClient;
    /**
     * The logger for the class.
     */
    private static final Logger LOGGER = Logger.getLogger("EnrolledInterfaceImplementation.class");

    /**
     * Default constructor
     */
    public EnrolledInterfaceImplementation() {
        webClient = new EnrolledRESTClient();
    }

    /**
     * Creates a new enrolled entity.
     *
     * @param enrolled The enrolled entity to be created.
     * @throws CreateErrorException If an error occurs while creating the
     * enrolled entity.
     */
    @Override
    public void createEnrolled(Enrolled enrolled) throws CreateErrorException {
        try {
            LOGGER.info("Creating a new enrolled");
            webClient.createEnrolled_XML(enrolled);
            LOGGER.info("Created!");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error creating an enrolled" + ex.getMessage());
            throw new CreateErrorException("Error creating an enrolled" + ex.getMessage());
        }
    }

    /**
     * Deletes an enrolled entity identified by studentId and subjectId.
     *
     * @param studentId The ID of the student associated with the enrolled
     * entity.
     * @param subjectId The ID of the subject associated with the enrolled
     * entity.
     * @throws DeleteErrorException If an error occurs while deleting the
     * enrolled entity.
     */
    @Override
    public void deleteEnrolled(String studentId, String subjectId) throws DeleteErrorException {
        try {
            LOGGER.info("Deleting a new enrolled");
            webClient.removeEnrolled(studentId, subjectId);
            LOGGER.info("Deleted!");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error deleting an enrolled " + ex.getMessage());
            throw new DeleteErrorException("Error creating an enrolled" + ex.getMessage());
        }

    }

    /**
     * Updates an existing enrolled entity.
     *
     * @param enrolled The enrolled entity to be updated.
     * @throws UpdateErrorException If an error occurs while updating the
     * enrolled entity.
     */
    @Override
    public void updateEnrolled(Enrolled enrolled) throws UpdateErrorException {
        try {
            LOGGER.info("Updating a new enrolled");
            webClient.updateEnrolled_XML(enrolled);
            LOGGER.info("Updated!");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "EnrolledManager: Error updating an enrolled " + ex.getMessage());
            throw new UpdateErrorException("Error updating an enrolled" + ex.getMessage());
        }
    }

}
