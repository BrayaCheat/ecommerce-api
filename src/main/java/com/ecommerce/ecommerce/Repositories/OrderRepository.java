package com.ecommerce.ecommerce.Repositories;

import com.ecommerce.ecommerce.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    @Query(value = "select * from orders o where o.status = 'PENDING' and o.created_at <= :timeLimit", nativeQuery = true)
    List<Order> findUnpaidOrders(@Param("timeLimit") LocalDateTime timeLimit);
}
