package com.ecommerce.ecommerce.DTO.Mapper;

import com.ecommerce.ecommerce.DTO.Request.ReviewRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ReviewResponseDTO;
import com.ecommerce.ecommerce.Models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toEntity(ReviewRequestDTO dto);

    // Map Entity -> DTO (Response)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    ReviewResponseDTO toDTO(Review review);

}
