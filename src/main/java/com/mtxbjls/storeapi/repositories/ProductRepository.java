package com.mtxbjls.storeapi.repositories;

import com.mtxbjls.storeapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:title is null or p.title = :title) and " +
            "(:category_id is null or p.category.id = :category_id) and " +
            "(:priceMin is null or p.price >= :priceMin) and " +
            "(:priceMax is null or p.price <= :priceMax)")
    List<Product> filterProducts(
            @Param("title") String title,
            @Param("category_id") Long category_id,
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax
    );
}
