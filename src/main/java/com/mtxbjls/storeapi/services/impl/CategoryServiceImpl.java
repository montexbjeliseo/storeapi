package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.mappers.CategoryMapper;
import com.mtxbjls.storeapi.models.Category;
import com.mtxbjls.storeapi.repositories.CategoryRepository;
import com.mtxbjls.storeapi.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Category category = CategoryMapper.mapToCategory(requestCategoryDTO);
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.mapToResponseCategoryDTO(savedCategory);
    }

    @Override
    public List<ResponseCategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::mapToResponseCategoryDTO).toList();
    }

    @Override
    public ResponseCategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        return CategoryMapper.mapToResponseCategoryDTO(category.get());
    }

}
