package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
}
