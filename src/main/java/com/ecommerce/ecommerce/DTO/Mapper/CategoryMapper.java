package com.ecommerce.ecommerce.DTO.Mapper;

import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import com.ecommerce.ecommerce.Models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryRequestDTO dto);

    // Map Entity -> DTO (Response)
    @Mapping(target = "products", ignore = true)
    CategoryResponseDTO toDTO(Category category);

}
