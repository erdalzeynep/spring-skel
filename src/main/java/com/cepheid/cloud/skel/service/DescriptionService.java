package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DescriptionService {
    @Autowired
    DescriptionRepository descriptionRepository;

    @Autowired
    ItemRepository itemRepository;

    public Description addDescription(Description description) {
        return descriptionRepository.save(description);
    }

    public List<Description> getDescriptionsByItemId(Long itemId){
        Item item = itemRepository.getOne(itemId);
        return descriptionRepository.findDescriptionsByItem(item);
    }
    public Description getDescriptionById(Long descriptionId) {
        Optional description = descriptionRepository.findById(descriptionId);
        if (description.isPresent()) {
            return (Description) description.get();
        } else {
            return null;
        }
    }
    public Description updateDescription(Description description){
        return descriptionRepository.save(description);
    }
}
