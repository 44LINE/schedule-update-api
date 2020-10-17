package com.github.line.sheduleupdateapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lecturers", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name", "surname", "shortname"}))
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "surname")
    @NotNull
    private String surname;

    @Column(name = "shortname")
    @NotNull
    private String shortName;

    @Column(name = "email")
    private String email;

    public Lecturer() {
    }

    public Lecturer(Long id, @NotNull String name, @NotNull String surname, @NotNull String shortName, @NotNull String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.shortName = shortName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
