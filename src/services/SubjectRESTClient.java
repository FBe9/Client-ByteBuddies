package services;

import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:SubjectFacadeREST
 * [entities.subject]<br>
 * USAGE:
 * <pre>
 *        NewJerseyClient client = new NewJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author irati
 */
public class SubjectRESTClient {

    private ResourceBundle configFile;
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("config.config").getString("BASE_URI");

    /**
     * Constructs a new SubjectRESTClient instance.
     */
    public SubjectRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.subject");
    }

    /**
     * Performs a POST request to create a new subject using XML representation.
     *
     * @param requestEntity The entity representing the subject to be created.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void createSubject_XML(Object requestEntity) throws WebApplicationException {
        webTarget
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Performs a GET request to find subjects by name using XML representation.
     *
     * @param responseType The type of the response expected.
     * @param name The name of the subjects to search for.
     * @return A response of the specified type containing subjects with the
     * given name.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsByName_XML(GenericType<T> responseType, String name) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("findSubjectsByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find subjects by end date using XML
     * representation.
     *
     * @param responseType The type of the response expected.
     * @param date The end date of the subjects to search for.
     * @return A response of the specified type containing subjects with the
     * given end date.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsByEndDate_XML(GenericType<T> responseType, String date) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("findSubjectsByEndDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a DELETE request to remove a subject identified by its ID.
     *
     * @param id The ID of the subject to be removed.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void removeSubject(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Performs a GET request to find subjects by enrollments using XML
     * representation.
     *
     * @param responseType The type of the response expected.
     * @param studentId The ID of the student for whom to find subjects by
     * enrollments.
     * @return A response of the specified type containing subjects associated
     * with the given student.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findByEnrollments_XML(GenericType<T> responseType, String studentId) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByEnrollments/{0}", new Object[]{studentId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find subjects by initial date using XML
     * representation.
     *
     * @param responseType The type of the response expected.
     * @param date The initial date of the subjects to search for.
     * @return A response of the specified type containing subjects with the
     * given initial date.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsByInitDate_XML(GenericType<T> responseType, String date) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectsByInitDate/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find all subjects using XML representation.
     *
     * @param responseType The type of the response expected.
     * @return A response of the specified type containing all subjects.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find subjects by teacher ID using XML
     * representation.
     *
     * @param responseType The type of the response expected.
     * @param teacherId The ID of the teacher for whom to find subjects.
     * @return A response of the specified type containing subjects associated
     * with the given teacher.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsByTeacherId_XML(GenericType<T> responseType, String teacherId) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectsByTeacherId/{0}", new Object[]{teacherId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find a subject by ID using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The class type of the response.
     * @param id The ID of the subject to be retrieved.
     * @return A response of the specified type containing the subject with the
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
     * Performs a GET request to find subjects with a specific count of enrolled
     * students using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The generic type of the response.
     * @param number The count of enrolled students to search for.
     * @return A response of the specified type containing subjects with the
     * given count of enrolled students.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsWithEnrolledStudentsCount_XML(GenericType<T> responseType, String number) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectsWithEnrolledStudentsCount/{0}", new Object[]{number}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a GET request to find subjects with a specific count of units
     * using XML representation.
     *
     * @param <T> The type of the response expected.
     * @param responseType The generic type of the response.
     * @param number The count of units to search for.
     * @return A response of the specified type containing subjects with the
     * given count of units.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsWithXUnits_XML(GenericType<T> responseType, String number) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findSubjectsWithXUnits/{0}", new Object[]{number}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Performs a PUT request to update a subject using XML representation.
     *
     * @param requestEntity The entity representing the updated subject.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public void updateSubject_XML(Object requestEntity) throws WebApplicationException {
        webTarget
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Performs a GET request to find subjects by teacher using XML
     * representation.
     *
     * @param responseType The type of the response expected.
     * @param name The name of the teacher to search for.
     * @return A response of the specified type containing subjects associated
     * with the given teacher.
     * @throws WebApplicationException If an error occurs during the web
     * application communication.
     */
    public <T> T findSubjectsByTeacher_XML(GenericType<T> responseType, String name) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("findSubjectByTeacher/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Closes the underlying client.
     */
    public void close() {
        client.close();
    }

}
