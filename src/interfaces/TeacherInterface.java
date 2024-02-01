package interfaces;

import exceptions.FindErrorException;
import java.util.Collection;
import models.Teacher;

/**
 * Interface for teacher model
 * @author irati
 */
public interface TeacherInterface {

    /**
     * Retrieves an teacher .
     *
     * @return An teacher
     * @throws FindErrorException If an error occurs during teacher retrieval.
     */
    public Collection<Teacher> findAll() throws FindErrorException;
}
