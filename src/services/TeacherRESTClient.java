package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:TeacherFacadeREST
 * [entities.teacher]<br>
 * USAGE:
 * <pre>
 *        TeachesRESTClient client = new TeachesRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author irati
 */
public class TeacherRESTClient {

    /**
     * Represents a webTarget to interact with a web resource
     */
    private WebTarget webTarget;
    /**
     * Represents a client for managing communication with a web resource.
     */
    private Client client;
    /**
     * Uri of the server
     */

    private static final String BASE_URI = ResourceBundle.getBundle("config.config").getString("BASE_URI");

    /**
     * Constructs a new instance of TeacherRESTClient. Initializes the web
     * client and sets the base URI for the REST resource.
     */
    public TeacherRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.teacher");
    }

    /**
     * Performs a GET request to find all teachers using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The generic type of the response.
     * @return A response of the specified type containing all available
     * teachers.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Closes the underlying client.
     */
    public void close() {
        client.close();
    }

}
