package org.contract.dataaccess.respositories.implementations;

import org.contract.common.exceptions.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Room;
import org.contract.dataaccess.respositories.interfaces.RoomRepository;

import javax.persistence.*;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {

    private EntityManager entityManager;

    public RoomRepositoryImpl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "contract_service_jpa" );
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Room add(Room room) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Room get(String roomId) {
        try {
            Query query = entityManager.createNamedQuery("Room.findByRoomId");
            query.setParameter("roomId", roomId);

            return (Room) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
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
