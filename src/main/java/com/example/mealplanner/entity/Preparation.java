package com.example.mealplanner.entity;

import javax.persistence.*;

@Entity
@Table(name = "preparation")
public class Preparation {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String description;

    public Preparation(String description) {
        this.description = description;
    }

    public Preparation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}