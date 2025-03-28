package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.DTO.Mapper.CategoryMapper;
import com.ecommerce.ecommerce.DTO.Request.CategoryRequestDTO;
import com.ecommerce.ecommerce.DTO.Response.CategoryResponseDTO;
import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Repositories.CategoryRepository;
import com.ecommerce.ecommerce.Services.CategoryService;
import com.ecommerce.ecommerce.Utils.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        if(files.isEmpty()) throw new RuntimeException("File not found");
        List<String> listFiles = new ArrayList<>();
        for(MultipartFile file : files){
            if(!file.isEmpty()){
                String fileUrl = fileService.uploadFile(file);
                listFiles.add(fileUrl);
            }
        }
        // will normalize image table
        Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageUrl(objectMapper.writeValueAsString(listFiles))
                .build();
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDTO> listCategory() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategory(Integer id) {
        return categoryMapper.toDTO(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found.")));
    }

    @Override
    public CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto, List<MultipartFile> files) throws IOException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        category.setName(dto.getName() != null ? dto.getName() : category.getName());
        category.setDescription(dto.getDescription() != null ? dto.getDescription() : category.getDescription());
        if(files.isEmpty()) throw new RuntimeException("File not found.");
        List<String> listFiles = new ArrayList<>();
        for(MultipartFile file : files){
            if(!file.isEmpty()){
                String fileUrl = fileService.uploadFile(file);
                listFiles.add(fileUrl);
            }
        }
        String images = String.join(",", listFiles);
        category.setImageUrl(images);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public String deleteCategory(Integer id) {
        if(!categoryRepository.existsById(id)) throw new RuntimeException("Category not found.");
        categoryRepository.deleteById(id);
        if(categoryRepository.existsById(id)){
            return "Failed to delete category";
        }else{
            return "Category deleted";
        }
    }
}
