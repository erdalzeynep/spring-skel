package com.cepheid.cloud.skel.model;

import javax.persistence.*;

@Entity
public class Description extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private String description;

    public Description() {
    }

    public Description(Item item, String description) {
        this.item = item;
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
