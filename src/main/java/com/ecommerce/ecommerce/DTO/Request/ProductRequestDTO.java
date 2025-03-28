package com.ecommerce.ecommerce.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required.")
    private String name;
    @NotBlank(message = "Product description is required.")
    private String description;
    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be at least 0.")
    private Double price;
    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock quantity cannot be negative.")
    private Integer stockQuantity;
}
