package com.cepheid.cloud.skel.model;

import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.dto.UpdateItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item extends AbstractEntity {

    @Column
    private String name;
    @Column
    private State state;

    @JsonIgnoreProperties("item")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Description> descriptions = new HashSet<>();

    public enum State {
        @JsonProperty("IN STOCK")
        IN_STOCK,
        @JsonProperty("SHORT STOCK")
        SHORT_STOCK,
        @JsonProperty("OUT OF STOCK")
        OUT_OF_STOCK;
    }

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, Set<Description> descriptions) {
        this.name = name;
        this.descriptions = descriptions;
    }

    public Item(CreateItemDTO createItemDTO) {

        this.name = createItemDTO.getName();
    }

    public Item(UpdateItemDTO itemDTO) {
        this.name = itemDTO.getName();
        this.state = itemDTO.getState();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }
}
