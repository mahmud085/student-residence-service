package org.contract.dataaccess.respositories.interfaces;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Entity;

import java.util.List;

public interface GenericRepository<T extends Entity> {
    T add(T obj);
    T get(String objId);
    List<T> getAll();
    void update(T obj) throws ObjectNotFoundException;
}
