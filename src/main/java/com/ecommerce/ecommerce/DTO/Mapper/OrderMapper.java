package com.ecommerce.ecommerce.DTO.Mapper;

import com.ecommerce.ecommerce.DTO.Request.OrderRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.OrderResponseDTO;
import com.ecommerce.ecommerce.Models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Map DTO -> Entity (Request)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    // Map Entity -> DTO (Response)
    OrderResponseDTO toDTO(Order order);
}
