package com.cepheid.cloud.skel.model;

import com.cepheid.cloud.skel.dto.CreateItemDTO;
import com.cepheid.cloud.skel.dto.UpdateItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item extends AbstractEntity {

    @Column
    private String name;
    @Column
    private State state ;

    @JsonIgnoreProperties("item")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Description> descriptions = new HashSet<>();

    public enum State {
        VALID {
            @Override
            public String toString() {
                return "VALID";
            }
        },
        INVALID {
            @Override
            public String toString() {
                return "INVALID";
            }
        },
        UNDEFINED {
            @Override
            public String toString() {
                return "UNDEFINED";
            }
        }
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

    public Item(UpdateItemDTO itemDTO){
        this.name = itemDTO.getName();
        this.state = State.valueOf(itemDTO.getState());
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
