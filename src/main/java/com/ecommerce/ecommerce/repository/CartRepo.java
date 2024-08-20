package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Products> {


     Cart findByProducts(Products products);

     Cart findById(Long id);
}
