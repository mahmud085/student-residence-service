package org.appointment.dataaccess.respositories.interfaces;

import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.models.Entity;

import java.util.List;

public interface GenericRepository<T extends Entity> {
    T add(T obj);
    List<T> getAll();
}
