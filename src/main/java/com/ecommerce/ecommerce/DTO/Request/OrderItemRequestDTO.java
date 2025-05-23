package com.ecommerce.ecommerce.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @NotNull(message = "Product id is required.")
    private Integer productId;
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;
}


// {userId: 1, items: {productId: 1, quantity: 3}}