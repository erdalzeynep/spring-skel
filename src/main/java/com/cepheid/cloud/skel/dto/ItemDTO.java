package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Item;

import java.util.Objects;

public class ItemDTO {
    private Long itemId;
    private String name;

    public ItemDTO() {
    }

    public ItemDTO(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(itemId, itemDTO.itemId) &&
                Objects.equals(name, itemDTO.name);
    }
}
