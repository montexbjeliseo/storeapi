package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.models.Category;
import com.mtxbjls.storeapi.repositories.CategoryRepository;
import com.mtxbjls.storeapi.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO) {


        if (requestCategoryDTO.getName() == null || requestCategoryDTO.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }

        if (requestCategoryDTO.getImage().isBlank()) {
            throw new RuntimeException("Image is required");
        }

        if (categoryRepository.existsByName(requestCategoryDTO.getName())) {
            throw new RuntimeException("Category already exists");
        }

        Category category = Category
                .builder()
                .name(requestCategoryDTO.getName())
                .image(requestCategoryDTO.getImage())
                .build();
        Category savedCategory = categoryRepository.save(category);

        return mapToResponseCategoryDTO(savedCategory);
    }

    @Override
    public List<ResponseCategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToResponseCategoryDTO).toList();
    }

    public ResponseCategoryDTO mapToResponseCategoryDTO(Category category) {
        return ResponseCategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .build();
    }
}
