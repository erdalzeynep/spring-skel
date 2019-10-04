package com.cepheid.cloud.skel.dto;

import com.cepheid.cloud.skel.model.Description;

import java.util.Objects;

public class UpdateDescriptionDTO {
    private String description;

    public UpdateDescriptionDTO() {
    }

    public UpdateDescriptionDTO(String description) {
        this.description = description;
    }

    public UpdateDescriptionDTO(Description description) {
        this.description = description.getDescription();
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
        UpdateDescriptionDTO that = (UpdateDescriptionDTO) o;
        return Objects.equals(description, that.description);
    }
}
