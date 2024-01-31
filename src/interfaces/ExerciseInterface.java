package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.UpdateErrorException;
import java.util.List;
import models.Exercise;

/**
 * Interface for managing exercises.
 * 
 * @author Leire
 */
public interface ExerciseInterface {

    /**
     * This method create a new exercise.
     *
     * @param exercise The Exercise to create.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * during creation.
     */
    public void createExercise(Exercise exercise) throws CreateErrorException;

    /**
     * This method update an exercise.
     *
     * @param exercise The Exercise to update.
     * @param id The ID of the exercise to update.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * during update.
     */
    public void updateExercise(Exercise exercise, String id) throws UpdateErrorException;

    /**
     * This method delete an exercise by id.
     *
     * @param id The Exercise entity object to be removed.
     * @throws DeleteErrorException Thrown when any error or exception occurs
     * during deletion.
     */
    public void removeExercise(String id) throws DeleteErrorException;

    /**
     * This method obtains an exercise using its id.
     *
     * @param id The id for the exercise to be got.
     * @return An Exercise entity object containing exercise data.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public Exercise findExerciseByID(String id) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises.
     *
     * @return A List of Exercise entity objects.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Exercise> findAllexercises() throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the number.
     *
     * @param number Number of the exercise.
     * @return List of the exercises with that specific number.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Exercise> findExercisesByNumber(String number) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the date.
     *
     * @param date Date of the date of the exercise.
     * @return List of the exercises with that specific date.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Exercise> findExercisesByDate(String date) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the level type.
     *
     * @param levelType Level type of the exercises.
     * @return List of the exercises with that specific level type.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Exercise> findExercisesByLevel(String levelType) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the unit name.
     *
     * @param name Name of the unit.
     * @return List of the exercises with that specific unit name.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public List<Exercise> findExercisesByUnitName(String name) throws ExerciseErrorException;

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
    public List<Exercise> findExercisesByNumberAndUnitName(String number, String name) throws ExerciseErrorException;

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
    public List<Exercise> findExercisesByDateAndUnitName(String date, String name) throws ExerciseErrorException;

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
    public List<Exercise> findExercisesByLevelAndUnitName(String levelType, String name) throws ExerciseErrorException;
}
