package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    String purchaseOrder(Integer orderId, Payment payment);
    Page<Payment> listPayment(Pageable pageable);
    Payment getOwnPayment(Integer paymentId, Integer userId);
}
