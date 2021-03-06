package com.lab1Spring.musiquorum.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Tag {


    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private UUID id;

    @Column(unique = true)
    private String name;

    public Tag(String name) {
        this.name = name;
    }


    public Tag() {

    }
}
