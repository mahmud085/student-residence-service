package org.contract.dataaccess.data.models;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "room")
@NamedQueries({
        @NamedQuery(name = "Room.findByRoomId",
                query = "select r from Room r where r.roomId = :roomId")
})
public class Room extends Entity {
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
