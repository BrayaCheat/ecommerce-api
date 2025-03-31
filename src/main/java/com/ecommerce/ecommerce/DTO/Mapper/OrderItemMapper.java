package com.ecommerce.ecommerce.DTO.Mapper;

import com.ecommerce.ecommerce.DTO.Request.OrderItemRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.OrderItemResponseDTO;
import com.ecommerce.ecommerce.Models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO dto);

    // Map Entity -> DTO (Response)
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDTO toDTO(OrderItem orderItem);
}
