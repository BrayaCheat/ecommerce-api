package com.ecommerce.ecommerce.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Integer rating;
    private String comment;
    private Integer userId;
    private Integer productId;
}
