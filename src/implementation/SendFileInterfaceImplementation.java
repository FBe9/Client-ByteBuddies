package implementation;

import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import factories.ExamFactory;
import factories.ExerciseFactory;
import interfaces.ExamInterface;
import interfaces.ExerciseInterface;
import interfaces.SendFileInterface;
import java.io.File;
import java.util.logging.Logger;
import models.Exam;
import models.Exercise;

/**
 * Implementation of the SendFileInterface
 *
 * @author Alex
 */
public class SendFileInterfaceImplementation implements SendFileInterface {

    private final ExamInterface examInterface;
    private final ExerciseInterface exerciseInterface;
    private static final Logger LOGGER = Logger.getLogger("ExamInterfaceImplementation");

    /**
     * Constructor to initialise the web client.
     */
    public SendFileInterfaceImplementation() {
        examInterface = ExamFactory.getModel();
        exerciseInterface = ExerciseFactory.getModel();
    }

    /**
     * The method used to send the file. It modifies an exam.
     *
     * @param file The file to be sent.
     * @param exam The exam to update.
     * @throws UpdateErrorException When an update error occurs.
     */
    @Override
    public void sendFile(File file, Exam exam) throws UpdateErrorException {
        try {
            exam.setFilePath(file.getAbsolutePath());
            examInterface.updateExam(exam);
        } catch (UpdateErrorException ex) {
            throw new UpdateErrorException(ex.getMessage());
        }
    }

    /**
     * The method used to send the file. It modifies an exercise.
     *
     * @param file The file to be sent.
     * @param exercise The exercise to update.
     * @param fileType Whether it's the "File" or the "FileSolution".
     */
    @Override
    public void sendFile(File file, Exercise exercise, String fileType) {
        if (fileType.equals("file")) {
            exercise.setFile(file.getAbsolutePath());
        } else {
            exercise.setFileSolution(file.getAbsolutePath());
        }
    }

    /**
     * The method used to receive a specific file stored in a given path.
     *
     * @param path The given path.
     * @return The requested file.
     * @throws FindErrorException When a find error occurs.
     */
    @Override
    public File receiveFile(String path, Object object) throws FindErrorException {
        if (object instanceof Exam) {
            try {
                examInterface.findExamById(((Exam) object).getId());
            } catch (FindErrorException ex) {
                throw new FindErrorException(ex.getMessage());
            }
        } else if (object instanceof Exercise) {
        }
        return new File(path);
    }
}
