package org.appointment.dataaccess.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Entity {
    @JsonIgnore
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
