package org.contract.dataaccess.data.models;

public class Room extends Entity implements Cloneable {
    private String roomNumber;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room clone() {
        try {
            return (Room) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
