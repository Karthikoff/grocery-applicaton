package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository <Payment,Long> {
}
