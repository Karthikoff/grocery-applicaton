package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.ecommerce.model.User;


public interface  UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
