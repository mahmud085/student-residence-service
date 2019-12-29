package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.Entity;

import java.util.List;

public interface GenericRepository<T extends Entity> {
    T add(T obj);
    List<T> getAll();
}
