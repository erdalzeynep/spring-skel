package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Item;

public class UpdateStateDTO {
    private Item.State state;

    public UpdateStateDTO() {
    }

    public UpdateStateDTO(Item.State state) {
        this.state = state;
    }

    public Item.State getState() {
        return state;
    }

    public void setState(Item.State state) {
        this.state = state;
    }
}
