package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.DTO.Request.ProductRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponseDTO addProduct(Integer categoryId, ProductRequestDTO dto, List<MultipartFile> files) throws IOException;
    ProductResponseDTO getProductById(Integer id);
    Page<ProductResponseDTO> listProducts(Pageable pageable, String productName, Double minPrice, Double maxPrice);
    ProductResponseDTO updateProduct(Integer categoryId, Integer id, ProductRequestDTO dto, List<MultipartFile> files) throws IOException;
    String deleteProduct(Integer id);
}
