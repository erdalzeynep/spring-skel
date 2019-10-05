package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Long> {
    public List<Description> findDescriptionsByItem(Item item);
    public List<Description> findByDescriptionContaining(String description);
}
