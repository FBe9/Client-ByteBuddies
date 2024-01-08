/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.UpdateErrorException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leire
 */
public interface ExerciseInterface {

    /**
     * This method create a new exercise.
     *
     * @param exercise The Exercise entity object containing new exercises data.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * during creation.
     */
    public void create_XML(Object requestEntity) throws CreateErrorException;

    /**
     * This method update an exercise.
     *
     * @param exercise The Exercise entity object containing modified exercise
     * data.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * during update.
     */
    public void edit_XML(Object requestEntity) throws UpdateErrorException;

    /**
     * This method delete an exercise by id.
     *
     * @param id The Exercise entity object to be removed.
     * @throws DeleteErrorException Thrown when any error or exception occurs
     * during deletion.
     */
    public void remove(Integer id) throws DeleteErrorException;

    /**
     * This method obtains an exercise using its id.
     *
     * @param id The id for the exercise to be got.
     * @return An Exercise entity object containing exercise data.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getExerciseByID_XML (Class<T> responseType, Integer id) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises.
     *
     * @return A List of Exercise entity objects.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getAllExercises_XML(GenericType<T> responseType) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the number.
     *
     * @param number Number of the exercise.
     * @return List of the exercises with that specific number.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getExercisesByNumber_XML(Class<T> responseType, Integer number) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the date.
     *
     * @param date Date of the date of the exercise.
     * @return List of the exercises with that specific date.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getExercisesByDate_XML(Class<T> responseType, Date date) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the level type.
     *
     * @param levelType Level type of the exercises.
     * @return List of the exercises with that specific level type.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getExercisesByLevel_XML(Class<T> responseType, LevelType levelType) throws ExerciseErrorException;

    /**
     * This method gets a list with all exercises using the unit name.
     *
     * @param name Name of the unit.
     * @return List of the exercises with that specific unit name.
     * @throws ExerciseErrorException Thrown when any error or exception occurs
     * during reading.
     */
    public <T> T getExercisesByUnitName_XML(Class<T> responseType, String name) throws ExerciseErrorException;

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
    public <T> T getExercisesByNumberAndUnitName_XML(Class<T> responseType, Integer number, String name) throws ExerciseErrorException;

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
    public <T> T getExercisesByDateAndUnitName_XML(Class<T> responseType, Date date, String name) throws ExerciseErrorException;

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
    public <T> T getExercisesByLevelAndUnitName_XML(Class<T> responseType, LevelType levelType, String name) throws ExerciseErrorException;
}
