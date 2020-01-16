package org.contract.dataaccess.respositories.interfaces;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Entity;

import java.util.List;

/**
 * GenericRepository is the repository interface for generic entity.
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public interface GenericRepository<T extends Entity> {
    /**
     * Adds a entity.
     * @param obj New entity object to be added.
     * @return Added entity.
     * @throws UnsupportedOperationException Thrown if the method is not implemented.
     */
    T add(T obj) throws UnsupportedOperationException;

    /**
     * Retrieves a specific entity.
     * @param objId Entity Id of the entity to be retrieved.
     * @return Entity with the specified id. Returns null if no entity is found with the specified id.
     * @throws UnsupportedOperationException Thrown if the method is not implemented.
     */
    T get(String objId) throws UnsupportedOperationException;

    /**
     * Retrieves all entities.
     * @return List of all entities. Returns empty list if no entity is found.
     * @throws UnsupportedOperationException Thrown if the method is not implemented.
     */
    List<T> getAll() throws UnsupportedOperationException;

    /**
     * Updates a specific entity.
     * @param obj Entity object to be updated.
     * @throws ObjectNotFoundException Thrown if no entity is found with the specified entity id.
     * @throws UnsupportedOperationException Thrown if the method is not implemented.
     */
    void update(T obj) throws ObjectNotFoundException, UnsupportedOperationException;
}
