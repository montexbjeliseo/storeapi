package com.mtxbjls.storeapi.repositories;

import com.mtxbjls.storeapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
