package com.ecommerce.ecommerce.Repositories;

import com.ecommerce.ecommerce.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    @Query(value = "select * from reviews where product_id = :product_id", nativeQuery = true)
    List<Review> findAllByProductId(@Param("productId") Integer productId);
}
