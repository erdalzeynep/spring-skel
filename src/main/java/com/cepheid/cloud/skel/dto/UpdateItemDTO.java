package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Item;

public class UpdateItemDTO {
    private String name;
    private Item.State state;

    public UpdateItemDTO(String name, Item.State state) {
        this.name = name;
        this.state = state;
    }

    public UpdateItemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item.State getState() {
        return state;
    }

    public void setState(Item.State state) {
        this.state = state;
    }
}
