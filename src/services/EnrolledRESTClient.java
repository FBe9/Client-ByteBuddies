package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:EnrolledFacadeREST
 * [entities.enrolled]<br>
 * USAGE:
 * <pre>
 *        EnrolledRESTClient client = new EnrolledRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author irati
 */
public class EnrolledRESTClient {

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
     * Constructs a new EnrolledRESTClient instance.
     */
    public EnrolledRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.enrolled");
    }

    /**
     * Updates an enrolled entity using XML representation.
     *
     * @param requestEntity The entity to be updated.
     * @throws WebApplicationException If an error occurs during the web service
     * call.
     */
    public void updateEnrolled_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Creates a new enrolled entity using XML representation.
     *
     * @param requestEntity The entity to be created.
     * @throws WebApplicationException If an error occurs during the web service
     * call.
     */
    public void createEnrolled_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Removes an enrolled entity by studentId and subjectId.
     *
     * @param studentId The student ID.
     * @param subjectId The subject ID.
     * @throws WebApplicationException If an error occurs during the web service
     * call.
     */
    public void removeEnrolled(String studentId, String subjectId) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{studentId, subjectId})).request().delete();
    }

    /**
     * Closes the underlying client.
     */
    public void close() {
        client.close();
    }
}
