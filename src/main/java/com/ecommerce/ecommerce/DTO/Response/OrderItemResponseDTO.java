package com.ecommerce.ecommerce.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Integer productId;
    private String productName;
    private Double priceAtPurchase;
    private Integer quantity;
}
