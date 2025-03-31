package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.DTO.Request.OrderRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.OrderResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(Integer userId, OrderRequestDTO dto);
    void cancelUnpaidOrder(Integer orderId);
    void autoCancelUnpaidOrderTransactional();
    List<OrderResponseDTO> listOrder(Pageable pageable);
}
