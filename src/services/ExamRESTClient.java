package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import models.Exam;

/**
 * Jersey REST client generated for REST resource:ExamFacadeREST
 * [entities.exam]<br>
 * USAGE:
 * <pre>
 *        ExamRESTClient client = new ExamRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Alex
 */
public class ExamRESTClient {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("config.config").getString("BASE_URI");

    /**
     * Constructs a new ExamRESTClient instance.
     */
    public ExamRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.exam");
    }

    /**
     * Create method that send the information of a new exam to the server.
     *
     * @param requestEntity The exam to be created.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the creation.
     */
    public void createExam_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Exam.class);
    }

    /**
     * Delete method to remove an exam from the server.
     *
     * @param id The ID of the exam to remove.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public void deleteExam(Integer id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Exam.class);
    }

    /**
     * Update method to modify the information within an exam.
     *
     * @param requestEntity The exam to modify.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public void updateExam_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Exam.class);
    }

    /**
     * Find method to search for a list of exams selected by the ID fo the
     * subject they belong to.
     *
     * @param responseType The type of response expected.
     * @param subjectId The ID of the exam to search for.
     * @return A collection of exams found.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public <T> T findBySubject_XML(GenericType<T> responseType, String subjectId) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findBySubject/{0}", new Object[]{subjectId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Find method to search for a specific exam.
     *
     * @param responseType The type of response expected.
     * @param id The ID of the exam to search for.
     * @return A generic object type containing the information found.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public <T> T find_XML(Class<T> responseType, Integer id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Find method to search for all exams containing the given parameter in
     * their description.
     *
     * @param responseType The type of response expected.
     * @param description The parameter to search for.
     * @return A generic object type containing the information found.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public <T> T findByDescription_XML(GenericType<T> responseType, String description) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByDescription/{0}", new Object[]{description}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Find method to search for all exams.
     *
     * @param responseType The type of response expected.
     * @return A generic object type containing all the information found.
     * @throws WebApplicationException When there is an error in the
     * communication with the server or during the transaction.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Closes the connection.
     */
    public void close() {
        client.close();
    }

}
