package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findByName(String name);
    public List<Item>findByNameContaining(String searchText);
}
