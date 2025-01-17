package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ItemService {

    @Autowired
    ItemRepository repository;

    @Autowired
    DescriptionService descriptionService;

    public Item addItem(Item item) {
        return repository.save(item);
    }

    public List<Item> getItems() {
        return repository.findAll();
    }

    public Item getItemById(Long itemId) {
        Optional item = repository.findById(itemId);
        if (item.isPresent()) {
            return (Item) item.get();
        } else {
            return null;
        }
    }

    public List<Item> getItemsByName(String name) {
        return repository.findByName(name);
    }

    public Item updateItem(Item item) {
        return repository.save(item);
    }

    public void deleteItem(Item item) {
        repository.delete(item);
    }

    public List<Item> getItemsByNameContaining(String searchText){
        return repository.findByNameContaining(searchText);
    }

    public Set<Item> getItemsByDescriptionContaining(String searchText){
        List<Description> descriptions = descriptionService.getDescriptionsByDescriptionContaining(searchText);
        Set<Item> items = new HashSet<>();
        for (Description description : descriptions) {
            items.add(description.getItem());
        }
        return items;
    }
}
