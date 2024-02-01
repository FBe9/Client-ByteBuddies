package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import models.Student;

/**
 * Jersey REST client generated for REST resource:StudentFacadeREST
 * [entities.student]<br>
 * USAGE:
 * <pre>
 *        StudentRESTClient client = new StudentRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author irati
 */
public class StudentRESTClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("config.config").getString("BASE_URI");

    /**
     * Constructs a new instance of StudentRESTClient. Initializes the web
     * client and sets the base URI for the REST resource.
     */
    public StudentRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.student");
    }

    /**
     * Performs a GET request to find a student by ID using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The class type of the response.
     * @param id The ID of the student to be retrieved.
     * @return A response of the specified type containing the student with the
     * given ID.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a POST request to create a new student using XML representation.
     *
     * @param requestEntity The entity representing the student to be created.
     * @return the student created.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public Student createStudent_XML(Object requestEntity) throws WebApplicationException {
        return webTarget
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Student.class);
    }

    /**
     * Performs a GET request to find all students using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The generic type of the response.
     * @return A response of the specified type containing all available
     * students.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Closes the client, releasing its resources.
     */
    public void close() {
        client.close();
    }

}
