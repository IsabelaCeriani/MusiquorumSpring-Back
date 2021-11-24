package com.lab1Spring.musiquorum.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Tag {


    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }


    public Tag() {

    }
}
