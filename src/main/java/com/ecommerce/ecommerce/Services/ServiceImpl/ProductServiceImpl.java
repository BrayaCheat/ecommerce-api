package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.DTO.Mapper.ProductMapper;
import com.ecommerce.ecommerce.DTO.Request.ProductRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.ProductResponseDTO;
import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Models.Product;
import com.ecommerce.ecommerce.Repositories.CategoryRepository;
import com.ecommerce.ecommerce.Repositories.ProductRepository;
import com.ecommerce.ecommerce.Services.ProductService;
import com.ecommerce.ecommerce.Utils.FileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ObjectMapper objectMapper;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, FileService fileService, ObjectMapper objectMapper, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
        this.objectMapper = objectMapper;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponseDTO addProduct(Integer categoryId, ProductRequestDTO dto, List<MultipartFile> files) throws IOException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        List<String> images = files.stream().filter(file -> !file.isEmpty()).map(file -> {
            try {
                return fileService.uploadFile(file);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to upload image" + ex.getMessage());
            }
        }).filter(Objects::nonNull).toList();
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .imageUrl(objectMapper.writeValueAsString(images))
                .category(category)
                .build();
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO getProductById(Integer id) {
        return productMapper.toDTO(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product id: " + id + " not found.")));
    }

    @Override
    public Page<ProductResponseDTO> listProducts(Pageable pageable, String productName, Double minPrice, Double maxPrice) {
        System.out.println("Log all products: " + productRepository.findAll().toArray().length);
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productName != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(spec, pageable).map(productMapper::toDTO);
    }

    @Override
    public ProductResponseDTO updateProduct(Integer categoryId, Integer id, ProductRequestDTO dto, List<MultipartFile> files) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product id: " + id + " not found"));
        List<String> newImages = files.isEmpty()
                ? objectMapper.readValue(product.getImageUrl(), new TypeReference<List<String>>() {
        })
                : files.stream().map(file -> {
            try {
                return fileService.uploadFile(file);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to upload image" + ex.getMessage());
            }
        }).filter(Objects::nonNull).toList();
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getStockQuantity() != null) product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(objectMapper.writeValueAsString(newImages));
        if (!product.getCategory().getId().equals(categoryId)) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found."));
            product.setCategory(category);
        }

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public String deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found.");
        }
        productRepository.deleteById(id);
        return "Category deleted successfully.";
    }
}
