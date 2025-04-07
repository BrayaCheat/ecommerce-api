package com.ecommerce.ecommerce.Repositories;

import com.ecommerce.ecommerce.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>, JpaSpecificationExecutor<Payment> {
    @Query(value = "select * from payment where user_id = :userId", nativeQuery = true)
    Payment findUserPayment(@Param("userId") Integer userId);
}
