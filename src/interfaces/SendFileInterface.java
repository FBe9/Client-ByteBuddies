package interfaces;

import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import java.io.File;
import models.Exam;
import models.Exercise;

/**
 * Interface for managing the transaction of files.
 *
 * @author Alex
 */
public interface SendFileInterface {

    /**
     * The method used to send the file. It modifies an exam.
     *
     * @param file The file to be sent.
     * @param exam The exam to update.
     * @throws UpdateErrorException When an update error occurs.
     */
    public void sendFile(File file, Exam exam) throws UpdateErrorException;

    /**
     * The method used to send the file. It modifies an exercise.
     *
     * @param file The file to be sent.
     * @param exercise The exercise to update.
     * @param fileType Whether it's the "File" or the "FileSolution".
     * @throws UpdateErrorException When an update error occurs.
     */
    public void sendFile(File file, Exercise exercise, String fileType) throws UpdateErrorException;

    /**
     * The method used to receive a specific file stored in a given path.
     *
     * @param path The given path.
     * @param object The given object.
     * @return The requested file.
     * @throws FindErrorException When an error occurs while finding object.
     */
    public File receiveFile(String path, Object object) throws FindErrorException;
}
