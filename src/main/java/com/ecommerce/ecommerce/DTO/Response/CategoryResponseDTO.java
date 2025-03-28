package com.ecommerce.ecommerce.DTO.Response;

import com.ecommerce.ecommerce.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private List<Product> products = new ArrayList<>();
}
