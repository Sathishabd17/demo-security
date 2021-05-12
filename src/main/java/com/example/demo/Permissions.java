package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long permissions_Id;
    private String name;

    public Long getPermissionsId() {
        return permissions_Id;
    }

    public void setPermissionsId(Long permissions_Id) {
        this.permissions_Id = permissions_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
