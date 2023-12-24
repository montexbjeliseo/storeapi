package com.mtxbjls.storeapi.repositories;


import com.mtxbjls.storeapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public boolean existsByName(String name);
}
