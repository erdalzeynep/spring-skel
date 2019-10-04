package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ItemDTO {
    private Long itemId;
    private String name;
    private Item.State state;
    private Set<DescriptionDTO> descriptions = new HashSet<>();

    public ItemDTO() {
    }

    public ItemDTO(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.state = item.getState();
        for (Description description : item.getDescriptions()){
            this.descriptions.add(new DescriptionDTO(description));
        }
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

    public Set<DescriptionDTO> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<DescriptionDTO> descriptions) {
        this.descriptions = descriptions;
    }

    public Item.State getState() {
        return state;
    }

    public void setState(Item.State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(itemId, itemDTO.itemId) &&
                Objects.equals(name, itemDTO.name) &&
                state == itemDTO.state &&
                Objects.equals(descriptions, itemDTO.descriptions);
    }
}
