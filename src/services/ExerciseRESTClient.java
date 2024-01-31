package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ExerciseFacadeREST
 * [entities.exercise]<br>
 * USAGE:
 * <pre>
 *        ExerciseFacadeREST client = new ExerciseFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Leire
 */
public class ExerciseRESTClient {

    private final WebTarget webTarget;
    private final Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    public ExerciseRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.exercise");
    }

    /**
     * Performs a POST request to create a new exercise using XML
     * representation.
     *
     * @param requestEntity The entity representing the exercise to be created.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Performs a PUT request to update an exercise using XML representation.
     *
     * @param requestEntity The entity representing the updated exercise.
     * @param id The ID of the exercise to be updated.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Performs a DELETE request to remove an exercise identified by its ID.
     *
     * @param id The ID of the exercise to be removed.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Performs a GET request to find an exercise by ID using XML
     * representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The class type of the response.
     * @param id The ID of the exercise to be retrieved.
     * @return A response of the specified type containing the exercise with the
     * given ID.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExerciseByID_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @return A response of the specified type containing all exercises.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getAllExercises_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises by number using XML
     * representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param number The number od the exercises.
     * @return A response of the specified type containing all exercises with
     * the given number.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByNumber_XML(GenericType<T> responseType, String number) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByNumber/{0}", new Object[]{number}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises by date using XML
     * representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param date The date of the exercises.
     * @return A response of the specified type containing all exercises with
     * the date given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByDate_XML(GenericType<T> responseType, String date) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises by level type using XML
     * representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param levelType The level type of the exercises.
     * @return A response of the specified type containing all exercises with
     * the level type given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByLevel_XML(GenericType<T> responseType, String levelType) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByLevelType/{0}", new Object[]{levelType}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises by unit name using XML
     * representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param name The name of the unit of the exercise.
     * @return A response of the specified type containing all exercises with
     * the unit name given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByUnitName_XML(GenericType<T> responseType, String name) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByUnitName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises by number and unit name
     * using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param number The number of the exercise.
     * @param name The name of the unit of the exercise.
     * @return A response of the specified type containing all exercises with
     * the nuber and the unit name given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByNumberAndUnitName_XML(GenericType<T> responseType, String number, String name) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByNumberAndUnitName/{0}/{1}", new Object[]{number, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param date The date of the exercise.
     * @param name The name of the unit of the exercise.
     * @return A response of the specified type containing all exercises with
     * the date and the unit name given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByDateAndUnitName_XML(GenericType<T> responseType, String date, String name) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByDateAndUnitName/{0}/{1}", new Object[]{date, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all exercises using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The type of the response expected.
     * @param levelType The level type of the exercise.
     * @param name The name of the unit of the exercise.
     * @return A response of the specified type containing all exercises with
     * the level type and the unit name given.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T getExercisesByLevelAndUnitName_XML(GenericType<T> responseType, String levelType, String name) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getByLevelTypeAndUnitName/{0}/{1}", new Object[]{levelType, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Closes the underlying client.
     */
    public void close() {
        client.close();
    }
}
