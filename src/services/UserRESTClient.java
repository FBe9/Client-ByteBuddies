package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST
 * [entities.user]<br>
 * USAGE:
 * <pre>
 *        UserRESTClient client = new UserRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author irati
 */
public class UserRESTClient {

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
     * Constructs a new instance of UserRESTClient.
     */
    public UserRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.user");
    }

    /**
     * Makes a POST request to the "login" endpoint with the provided request
     * entity. The response is processed based on the specified response type.
     *
     * @param requestEntity The request entity to be sent in the request body.
     * @param responseType The class of the expected response type.
     * @return The response object based on the specified type.
     * @throws WebApplicationException If an error occurs during the web
     * application operation.
     */
    public <T> T login(Object requestEntity, Class<T> responseType) throws WebApplicationException {
        return webTarget.path("login").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), responseType);
    }
    /**
     * Closes the underlying client.
     */
    public void close() {
        client.close();
    }

}
