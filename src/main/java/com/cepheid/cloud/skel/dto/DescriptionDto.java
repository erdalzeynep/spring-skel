package com.cepheid.cloud.skel.dto;

public class DescriptionDto {
    private Long itemID;
    private String description;

    public DescriptionDto() {
    }

    public DescriptionDto(Long itemID, String description) {
        this.itemID = itemID;
        this.description = description;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
