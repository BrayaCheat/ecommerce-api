package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.Models.Payment;
import com.ecommerce.ecommerce.Services.ServiceImpl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "Rest Endpoints for payment")
@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/user/payment")
    public ResponseEntity<String> purchaseOrder(@PathVariable Integer orderId, @RequestBody Payment dto) {
        return ResponseEntity.status(201).body(paymentService.purchaseOrder(orderId, dto));
    }

    @GetMapping("/admin/payments")
    public ResponseEntity<Page> listPayment(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.status(200).body(paymentService.listPayment(pageable));
    }

    @GetMapping("/user/payment/{paymentId}")
    public ResponseEntity<Payment> getOwnPayment(@PathVariable Integer paymentId, @RequestParam Integer userId){
        return ResponseEntity.status(200).body(paymentService.getOwnPayment(paymentId, userId));
    }
}
