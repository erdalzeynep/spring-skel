package com.cepheid.cloud.skel.dto;

public class UpdateItemDTO {
    private String name;
    private String state;

    public UpdateItemDTO(String name, String state) {
        this.name = name;
        this.state = state.toUpperCase();
    }

    public UpdateItemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
