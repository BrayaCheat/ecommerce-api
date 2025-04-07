package com.ecommerce.ecommerce.DTO.Response;

import com.ecommerce.ecommerce.Models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String imageUrl;
    private Integer categoryId;
    private String categoryName;
    private List<ReviewResponseDTO> reviews;
}
