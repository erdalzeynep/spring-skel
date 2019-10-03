package com.cepheid.cloud.skel.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Item extends AbstractEntity {

    @Column
    private String name;
    @Column
    private State state = State.UNDEFINED;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Description> descriptions;

    enum State {
        VALID, INVALID, UNDEFINED;
    }

    public Item() {
    }

    public Item(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return super.mId.equals(item.getId()) &&
                name.equals(item.getName());
    }
}
