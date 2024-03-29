package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.UpdateErrorException;
import models.Enrolled;

/**
 * An interface defining operations for managing enrolled entities.
 *
 * @author irati
 */
public interface EnrolledInterface {

    /**
     * Creates a new enrolled entity.
     *
     * @param enrolled The enrolled entity to be created.
     * @throws CreateErrorException If an error occurs while creating the
     * enrolled entity.
     */
    public void createEnrolled(Enrolled enrolled) throws CreateErrorException;

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
    public void deleteEnrolled(String studentId, String subjectId) throws DeleteErrorException;

    /**
     * Updates an existing enrolled entity.
     *
     * @param enrolled The enrolled entity to be updated.
     * @throws UpdateErrorException If an error occurs while updating the
     * enrolled entity.
     */
    public void updateEnrolled(Enrolled enrolled) throws UpdateErrorException;

}
