package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Description;

import java.util.Objects;

public class DescriptionDTO {
    private Long itemId;
    private String description;

    public DescriptionDTO() {
    }

    public DescriptionDTO(Description description) {
        this.itemId = description.getItem().getId();
        this.description = description.getDescription();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionDTO that = (DescriptionDTO) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, description);
    }
}
