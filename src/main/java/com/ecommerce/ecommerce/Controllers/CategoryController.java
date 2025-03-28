package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import com.ecommerce.ecommerce.Services.ServiceImpl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Category", description = "Rest Endpoints For Category")
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryResponseDTO>> listCategory() {
        return ResponseEntity.status(200).body(categoryService.listCategory());
    }

    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(categoryService.getCategory(id));
    }

    @PostMapping(value = "/admin/categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> addCategory(@Valid @ModelAttribute CategoryRequestDTO dto, @RequestParam List<MultipartFile> files) throws IOException {
        return ResponseEntity.status(201).body(categoryService.addCategory(dto, files));
    }

    @PutMapping(value = "/admin/categories/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Integer id, @ModelAttribute CategoryRequestDTO dto, @RequestParam List<MultipartFile> files) throws IOException {
        return ResponseEntity.status(201).body(categoryService.updateCategory(id, dto, files));
    }

    @DeleteMapping(value = "/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
        return ResponseEntity.status(200).body(categoryService.deleteCategory(id));
    }
}
