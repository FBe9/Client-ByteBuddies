/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import exceptions.*;
import interfaces.UnitInterface;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import models.Unit;
import services.UnitRESTClient;

/**
 *
 * @author Nerea
 */
public class UnitManagerImplementation implements UnitInterface {

    private final UnitRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("package implementation");

    public UnitManagerImplementation() {
        webClient = new UnitRESTClient();
    }

    @Override
    public void createUnit(Unit unit) throws CreateErrorException {
        try {
            LOGGER.log(Level.INFO, "Creating a new Unit id= {0}", unit.getId());
            webClient.createUnit_XML(unit);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation: Error creating a unit{0}", e.getMessage());
            throw new CreateErrorException("Error creating a unit" + e.getMessage());
        }
    }

    @Override
    public void updateUnit(Unit unit) throws UpdateErrorException {
        try {
            LOGGER.log(Level.INFO, "Updating the unit  id= {0}", unit.getId());
            webClient.updateAlbum_XML(unit);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation: Error updating a unit{0}", e.getMessage());
            throw new UpdateErrorException("Error updating a unit" + e.getMessage());
        }
    }

    @Override
    public void removeUnit(Unit unit) throws DeleteErrorException {
        try {
            LOGGER.log(Level.INFO, "Deleting the unit  id= {0}", unit.getId());
            webClient.removeUnit(unit.getId());

        } catch (ClientErrorException e) {
            LOGGER.severe(e.getMessage());
            throw new DeleteErrorException(e.getMessage());
        }
    }

    @Override
    public Unit findUnitByID(Integer id) throws FindErrorException {
        Unit unit = null;
        try {
            unit = webClient.findUnitByID_XML(Unit.class, id);
        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitByID(Integer id) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return unit;
    }

    @Override
    public List<Unit> findAllUnits() throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findAllUnits_XML(new GenericType<List<Unit>>() {
            });

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findAllUnits() {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findSubjectUnits(String name) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnits_XML(new GenericType<List<Unit>>() {
            }, name);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnits(String name) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findSubjectUnitsByName(String name, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findAllUnits_XML(new GenericType<List<Unit>>() {
            });

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByName(String name, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public Unit findOneSubjectUnitByName(String name, String subject) throws FindErrorException {
        Unit unit = null;
        try {
            unit = webClient.findOneSubjectUnitByName_XML(new GenericType<Unit>() {
            }, name, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findOneSubjectUnitByName(String name, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return unit;
    }

    @Override
    public List<Unit> findSubjectUnitsByDateInit(Date dateInit, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByDateInit_XML(new GenericType<List<Unit>>() {
            }, subject, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByDateInit(Date dateInit, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findSubjectUnitsByDateEnd(Date dateEnd, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByDateEnd_XML(new GenericType<List<Unit>>() {
            }, subject, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByDateEnd(Date dateEnd, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findSubjectUnitsByHours(Integer hours, String subject) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findSubjectUnitsByHours_XML(new GenericType<List<Unit>>() {
            }, hours, subject);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findSubjectUnitsByHours(Integer hours, String subject) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findUnitsFromTeacherSubjects(Integer userId) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findUnitsFromTeacherSubjects_XML(new GenericType<List<Unit>>() {
            }, userId);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitsFromTeacherSubjects(Integer userId) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

    @Override
    public List<Unit> findUnitsFromStudentSubjects(Integer userId) throws FindErrorException {
        List<Unit> units = null;
        try {
            units = webClient.findUnitsFromStudentSubjects_XML(new GenericType<List<Unit>>() {
            }, userId);

        } catch (ClientErrorException e) {
            LOGGER.log(Level.SEVERE, "UnitManagerImplementation ->  findUnitsFromStudentSubjects(Integer userId) {0}", e.getMessage());
            throw new FindErrorException("Error finding subject" + e.getMessage());
        }
        return units;
    }

}
