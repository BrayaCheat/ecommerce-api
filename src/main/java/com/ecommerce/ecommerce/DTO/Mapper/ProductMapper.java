package com.ecommerce.ecommerce.DTO.Mapper;

import com.ecommerce.ecommerce.DTO.Request.ProductRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ProductResponseDTO;
import com.ecommerce.ecommerce.Models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    // Map Entity -> DTO (Response)
    @Mapping(target = "categoryId", source = "category.id")
    ProductResponseDTO toDTO(Product product);

}
