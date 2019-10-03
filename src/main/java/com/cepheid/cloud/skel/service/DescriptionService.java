package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DescriptionService {
    @Autowired
    DescriptionRepository repository;

    public Description addDescription(Description description) {
        return repository.save(description);
    }

    public void deleteDescription(long id) {
        repository.deleteById(id);
    }

    public List<Description> getDescriptions() {
        return repository.findAll();
    }
}
