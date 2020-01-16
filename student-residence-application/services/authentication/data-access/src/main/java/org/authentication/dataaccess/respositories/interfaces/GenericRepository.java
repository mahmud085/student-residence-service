package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.Entity;

import java.util.List;
/**
 * GenericRepository is the repository interface for generic entity.
 *
 * @author Imtiaz Abedin
 *
 */
public interface GenericRepository<T extends Entity> {
    /**
     * Adds a entity.
     * @param obj New entity object to be added.
     * @return Added entity
     */
    T add(T obj);


    /**
     * Retrieves all entities.
     * @return List of all entities. Returns empty list if no entity is found.
     */
    List<T> getAll();
}
