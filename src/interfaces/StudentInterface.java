/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author irati
 */
public interface StudentInterface {

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException;

    public void createStudent_XML(Object requestEntity) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;
}
