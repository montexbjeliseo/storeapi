package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.mappers.CategoryMapper;
import com.mtxbjls.storeapi.models.Category;
import com.mtxbjls.storeapi.repositories.CategoryRepository;
import com.mtxbjls.storeapi.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO) {


        if (requestCategoryDTO.getName() == null || requestCategoryDTO.getName().isBlank()) {
            throw new MandatoryFieldException("Name is required");
        }

        if (requestCategoryDTO.getImage() == null || requestCategoryDTO.getImage().isBlank()) {
            throw new MandatoryFieldException("Image is required");
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
            throw new ResourceNotFoundException("Category not found");
        }
        return CategoryMapper.mapToResponseCategoryDTO(category.get());
    }

    @Override
    public ResponseCategoryDTO updateCategory(Long id, RequestCategoryDTO requestCategoryDTO) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }
        Category updatedCategory = CategoryMapper.updateCategory(category.get(), requestCategoryDTO);

        return CategoryMapper.mapToResponseCategoryDTO(categoryRepository.save(updatedCategory));
    }

    @Override
    @Transactional
    public ResponseCategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        ResponseCategoryDTO responseCategoryDTO = CategoryMapper.mapToResponseCategoryDTO(category);
        categoryRepository.delete(category);
        return responseCategoryDTO;
    }


}
