package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Products,Long> {
    Products findByName(String name);
}
