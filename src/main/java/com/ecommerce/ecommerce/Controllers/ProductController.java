package com.ecommerce.ecommerce.Controllers;

import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Request.ProductRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import com.ecommerce.ecommerce.DTO.Response.ProductResponseDTO;
import com.ecommerce.ecommerce.Services.ServiceImpl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Product", description = "Rest Endpoints For Products")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductResponseDTO>> listProduct(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String categoryId
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.status(200).body(productService.listProducts(pageable, productName, minPrice, maxPrice, categoryId));
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(productService.getProductById(id));
    }

    @PostMapping(value = "/admin/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestParam Integer categoryId, @Valid @ModelAttribute ProductRequestDTO dto, @RequestParam List<MultipartFile> files) throws IOException {
        return ResponseEntity.status(201).body(productService.addProduct(categoryId, dto, files));
    }

    @PutMapping(value = "/admin/products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestParam Integer categoryId, @PathVariable Integer id, @ModelAttribute ProductRequestDTO dto, @RequestParam List<MultipartFile> files) throws IOException {
        return ResponseEntity.status(201).body(productService.updateProduct(categoryId, id, dto, files));
    }

    @DeleteMapping(value = "/admin/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(productService.deleteProduct(id));
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProductByCategoryId (@PathVariable Integer categoryId){
        return ResponseEntity.status(200).body(productService.getProductByCategoryId(categoryId));
    }
}
