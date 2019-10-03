package com.cepheid.cloud.skel.dto;

public class CreateDescriptionDTO {
    private Long itemId;
    private String description;

    public CreateDescriptionDTO() {
    }

    public CreateDescriptionDTO(Long itemId, String description) {
        this.itemId = itemId;
        this.description = description;
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
}
