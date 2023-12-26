package com.mtxbjls.storeapi.repositories;

import com.mtxbjls.storeapi.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:title is null or LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) and " +
            "(:category_id is null or p.category.id = :category_id) and " +
            "(:priceMin is null or p.price >= :priceMin) and " +
            "(:priceMax is null or p.price <= :priceMax)")
    Page<Product> filterProducts(
            @Param("title") String title,
            @Param("category_id") Long category_id,
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax,
            Pageable pageable
    );
}
