package org.contract.dataaccess.data.models;

public class Room extends Entity implements Cloneable {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Room clone() {
        try {
            return (Room) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
