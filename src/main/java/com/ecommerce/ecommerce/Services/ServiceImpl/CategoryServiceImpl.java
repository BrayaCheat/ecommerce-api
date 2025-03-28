package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.DTO.Mapper.CategoryMapper;
import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Repositories.CategoryRepository;
import com.ecommerce.ecommerce.Services.CategoryService;
import com.ecommerce.ecommerce.Utils.FileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, FileService fileService, ObjectMapper objectMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.fileService = fileService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CategoryResponseDTO addCategory(CategoryRequestDTO dto, List<MultipartFile> files) throws IOException {
        List<String> images = files.stream().filter(file -> !file.isEmpty()).map(file -> {
            try {
                return fileService.uploadFile(file);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to upload image" + ex.getMessage());
            }
        }).filter(Objects::nonNull).toList();
        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageUrl(objectMapper.writeValueAsString(images))
                .build();
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> listCategory(Pageable pageable, String categoryName) {
        Specification<Category> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (categoryName != null) {
                predicates.add(cb.like(root.get("name"), "%" + categoryName + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return categoryRepository.findAll(spec, pageable)
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResponseDTO getCategory(Integer id) {
        return categoryMapper.toDTO(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found.")));
    }

    @Override
    public CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto, List<MultipartFile> files) throws IOException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        List<String> newImages = files.isEmpty()
                ? objectMapper.readValue(category.getImageUrl(), new TypeReference<List<String>>() {
        })
                : files.stream().map(file -> {
            try {
                return fileService.uploadFile(file);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to upload image" + ex.getMessage());
            }
        }).filter(Objects::nonNull).toList();


        if (dto.getName() != null) category.setName(dto.getName());
        if (dto.getDescription() != null) category.setDescription(dto.getDescription());
        category.setImageUrl(objectMapper.writeValueAsString(newImages));
        return categoryMapper.toDTO(categoryRepository.save(category));
    }


    @Override
    public String deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found.");
        }
        categoryRepository.deleteById(id);
        return "Category deleted successfully.";
    }
}
