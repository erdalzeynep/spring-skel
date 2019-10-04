package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemService {

    @Autowired
    ItemRepository repository;

    public Item addItem(Item item) {
        return repository.save(item);
    }

    public List<Item> getItems() {
        return repository.findAll();
    }

    public List<Item> getItemsByState(String state) {
        List<Item> items = repository.findAll();
        List<Item> itemsWithGivenState = new ArrayList<>();
        for (Item item : items) {
            if (item.getState().toString().equalsIgnoreCase(state)) {
                itemsWithGivenState.add(item);
            }
        }
        return itemsWithGivenState;
    }


    public Item getItemById(Long itemId) {
        Optional item = repository.findById(itemId);
        if (item.isPresent()) {
            return (Item) item.get();
        } else {
            return null;
        }
    }

    public Item updateItem(Item item) {
        return repository.save(item);
    }

    public void deleteItem(Item item) {
        repository.delete(item);
    }

    public void deleteItems( List<Item> items) {
        repository.deleteInBatch(items);
    }
}
