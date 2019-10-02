package com.cepheid.cloud.skel.model;

import com.cepheid.cloud.skel.repository.ItemRepository;

import javax.persistence.Entity;

@Entity
public class Item extends AbstractEntity {
    private String name;

    public Item() {
    }

    public Item(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
