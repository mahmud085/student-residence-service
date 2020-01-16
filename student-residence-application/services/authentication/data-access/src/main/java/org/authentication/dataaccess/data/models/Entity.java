package org.authentication.dataaccess.data.models;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;


import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class Entity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
