package org.appointment.dataaccess.respositories.implementations;

import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.models.Room;
import org.appointment.dataaccess.respositories.interfaces.RoomRepository;
import org.appointment.dataaccess.store.DataStore;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    @Override
    public Room add(Room room) {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<Room> getAll() {
        throw new UnsupportedOperationException();
    }

}
