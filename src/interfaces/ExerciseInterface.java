package interfaces;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.UpdateErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Leire
 */
public interface ExerciseInterface {

    public void create_XML(Object requestEntity) throws CreateErrorException;

    public void edit_XML(Object requestEntity, String id) throws UpdateErrorException;

    public void remove(String id) throws DeleteErrorException;

    public <T> T getExerciseByID_XML(Class<T> responseType, String id) throws ExerciseErrorException;

    public <T> T getAllExercises_XML(GenericType<T> responseType) throws ExerciseErrorException;

    public <T> T getExercisesByNumber_XML(Class<T> responseType, String number) throws ExerciseErrorException;

    public <T> T getExercisesByDate_XML(Class<T> responseType, String date) throws ExerciseErrorException;

    public <T> T getExercisesByLevel_XML(Class<T> responseType, String levelType) throws ExerciseErrorException;

    public <T> T getExercisesByUnitName_XML(Class<T> responseType, String name) throws ExerciseErrorException;

    public <T> T getExercisesByNumberAndUnitName_XML(Class<T> responseType, String number, String name) throws ExerciseErrorException;

    public <T> T getExercisesByDateAndUnitName_XML(Class<T> responseType, String date, String name) throws ExerciseErrorException;

    public <T> T getExercisesByLevelAndUnitName_XML(Class<T> responseType, String levelType, String name) throws ExerciseErrorException;
}
