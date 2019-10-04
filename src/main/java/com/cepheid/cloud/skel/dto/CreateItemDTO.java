package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Item;

public class CreateItemDTO {
    private String name;

    public CreateItemDTO(Item item) {
        this.name = item.getName();
    }

    public CreateItemDTO(String name) {
        this.name = name;
    }

    public CreateItemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
