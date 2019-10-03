package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemService {

    @Autowired
    ItemRepository repository;

    public Item addItem(Item item) {
        return repository.save(item);
    }

    public void deleteItem(long id) {
        repository.deleteById(id);
    }

    public List<Item> getItems() {
        return repository.findAll();
    }

    public Item getItemById(Long itemId){
        if(repository.existsById(itemId)){
            return repository.getOne(itemId);
        }
        else{
            return null;
        }
    }

    public Item updateItem(Item updatedItem){
        return repository.save(updatedItem);
    }
}
