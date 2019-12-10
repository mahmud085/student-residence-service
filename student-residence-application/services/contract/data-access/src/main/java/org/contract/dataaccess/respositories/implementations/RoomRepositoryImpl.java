package org.contract.dataaccess.respositories.implementations;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Room;
import org.contract.dataaccess.respositories.interfaces.RoomRepository;
import org.contract.dataaccess.store.DataStore;

import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {
    @Override
    public Room add(Room room) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Room get(String roomNumber) {
        Room room = DataStore.rooms.stream()
                .filter(x -> x.getNumber().equalsIgnoreCase(roomNumber))
                .findFirst()
                .orElse(null);

        return room != null ? room.clone() : null;
    }

    @Override
    public List<Room> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Room obj) throws ObjectNotFoundException {
        throw new UnsupportedOperationException();
    }
}
