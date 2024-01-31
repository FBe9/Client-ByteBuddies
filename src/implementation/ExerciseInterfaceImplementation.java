package implementation;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.UpdateErrorException;
import interfaces.ExerciseInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Exercise;
import services.ExerciseRESTClient;

/**
 * Implementation of the ExerciseInterface interface for exercise management.
 *
 * @author Leire
 */
public class ExerciseInterfaceImplementation implements ExerciseInterface {

    private final ExerciseRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("package implementation");

    /**
     * Constructor to initialize the ExerciseInterfaceImplementation.
     */
    public ExerciseInterfaceImplementation() {
        webClient = new ExerciseRESTClient();
    }

    /**
     * This method create a new exercise.
     *
     * @param exercise The Exercise to create.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * during creation.
     */
    @Override
    public void createExercise(Exercise exercise) throws CreateErrorException {
        try {
            LOGGER.log(Level.INFO, "Creating a new Exercise id= {0}", exercise.getId());
            webClient.create_XML(exercise);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation: Error creating the exercise{0}", e.getMessage());
            throw new CreateErrorException("Error creating an exercise" + e.getMessage());
        }
    }

    /**
     * This method update an exercise.
     *
     * @param exercise The Exercise to update.
     * @param id The ID of the exercise to update.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * during update.
     */
    @Override
    public void updateExercise(Exercise exercise, String id) throws UpdateErrorException {
        try {
            LOGGER.log(Level.INFO, "Updating the exercise id= {0}", exercise.getId());
            webClient.edit_XML(exercise, id);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation: Error updating the exercise{0}", e.getMessage());
            throw new UpdateErrorException("Error updating an exercise" + e.getMessage());
        }
    }

    /**
     * This method delete an exercise by id.
     *
     * @param id The Exercise entity object to be removed.
     * @throws DeleteErrorException Thrown when any error or exception occurs
     * during deletion.
     */
    @Override
    public void removeExercise(String id) throws DeleteErrorException {
        try {
            webClient.remove(id);
        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
            throw new DeleteErrorException(e.getMessage());
        }
    }

    /**
     * This method obtains an exercise using its id.
     *
     * @param id The id for the exercise to be got.
     * @return An Exercise entity object containing exercise data.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public Exercise findExerciseByID(String id) throws ExerciseErrorException {
        Exercise exercise = null;
        try {
            exercise = webClient.getExerciseByID_XML(Exercise.class, id);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExerciseByID(Integer id) {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercise;
    }

    /**
     * This method gets a list with all exercises.
     *
     * @return A List of Exercise entity objects.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Exercise> findAllexercises() throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getAllExercises_XML(new GenericType<List<Exercise>>() {
            });
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findAllexercises() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the number.
     *
     * @param number Number of the exercise.
     * @return List of the exercises with that specific number.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Exercise> findExercisesByNumber(String number) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByNumber_XML(new GenericType<List<Exercise>>() {
            }, number);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByNumber() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the date.
     *
     * @param date Date of the date of the exercise.
     * @return List of the exercises with that specific date.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Exercise> findExercisesByDate(String date) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByDate_XML(new GenericType<List<Exercise>>() {
            }, date);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByDate() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the level type.
     *
     * @param levelType Level type of the exercises.
     * @return List of the exercises with that specific level type.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Exercise> findExercisesByLevel(String levelType) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByLevel_XML(new GenericType<List<Exercise>>() {
            }, levelType);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByLevel() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the unit name.
     *
     * @param name Name of the unit.
     * @return List of the exercises with that specific unit name.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    @Override
    public List<Exercise> findExercisesByUnitName(String name) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByUnitName_XML(new GenericType<List<Exercise>>() {
            }, name);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByNumber() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the exercise number and
     * the unit name.
     *
     * @param number Number of the exercise.
     * @param name Name of the unit.
     * @return List of the exercises with that specific number and unit.
     * @throws ExerciseErrorException ExerciseErrorException Thrown when any
     * error or exception occurs during reading.
     */
    @Override
    public List<Exercise> findExercisesByNumberAndUnitName(String number, String name) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByNumberAndUnitName_XML(new GenericType<List<Exercise>>() {
            }, number, name);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByNumberAndUnitName() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the exercise date and
     * the unit name.
     *
     * @param date Date of the date of the exercise.
     * @param name Name of the unit.
     * @return List of the exercises with that specific date and unit.
     * @throws ExerciseErrorException ExerciseErrorException Thrown when any
     * error or exception occurs during reading.
     */
    @Override
    public List<Exercise> findExercisesByDateAndUnitName(String date, String name) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByDateAndUnitName_XML(new GenericType<List<Exercise>>() {
            }, date, name);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByNumber() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

    /**
     * This method gets a list with all exercises using the exercise level type
     * and the unit name.
     *
     * @param levelType Level type of the exercises.
     * @param name Name of the unit.
     * @return List of the exercises with that specific level type and unit.
     * @throws ExerciseErrorException ExerciseErrorException Thrown when any
     * error or exception occurs during reading.
     */
    @Override
    public List<Exercise> findExercisesByLevelAndUnitName(String levelType, String name) throws ExerciseErrorException {
        List<Exercise> exercises = null;
        try {
            exercises = webClient.getExercisesByLevelAndUnitName_XML(new GenericType<List<Exercise>>() {
            }, levelType, name);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "ExerciseInterfaceImplementation ->  findExercisesByNumber() {0}", e.getMessage());
            throw new ExerciseErrorException("Error finding exercise" + e.getMessage());
        }
        return exercises;
    }

}
