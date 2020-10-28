package com.github.line.sheduleupdateapi.domain;

import javax.persistence.*;

@Entity
@Table(name = "class_objects", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ClassObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    public ClassObject() {
    }

    public ClassObject(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}
