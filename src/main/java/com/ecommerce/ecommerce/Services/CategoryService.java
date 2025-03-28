package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    CategoryResponseDTO addCategory(CategoryRequestDTO dto, List<MultipartFile> files) throws IOException;
    List<CategoryResponseDTO> listCategory(Pageable pageable, String categoryName);
    CategoryResponseDTO getCategory(Integer id);
    CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto, List<MultipartFile> files) throws IOException;
    String deleteCategory(Integer id);
}
