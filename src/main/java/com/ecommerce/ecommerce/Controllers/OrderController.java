package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.DTO.Request.OrderRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.OrderResponseDTO;
import com.ecommerce.ecommerce.Services.ServiceImpl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "Rest Endpoints for Order")
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/user/create-order")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestParam Integer userId, @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.status(201).body(orderService.createOrder(userId, dto));
    }

    @PostMapping("/user/cancel-order")
    public  ResponseEntity<?> cancelOrder(@RequestParam Integer orderId){
        orderService.cancelUnpaidOrder(orderId);
        return ResponseEntity.status(201).body("Order id: " + orderId + " cancelled.");
    }
}
