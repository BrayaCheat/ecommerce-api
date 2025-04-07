package com.ecommerce.ecommerce.Repositories;

import com.ecommerce.ecommerce.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query(value = "select * from products where category_id = :categoryId", nativeQuery = true)
    List<Product> getProductByCategoryId(@Param("categoryId") Integer categoryId);
}
