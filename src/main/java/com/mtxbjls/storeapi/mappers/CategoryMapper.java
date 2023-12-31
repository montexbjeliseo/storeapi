package com.mtxbjls.storeapi.mappers;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.models.Category;

public class CategoryMapper {

    public static Category mapToCategory(RequestCategoryDTO requestCategoryDTO) {
        return Category
                .builder()
                .name(requestCategoryDTO.getName())
                .image(requestCategoryDTO.getImage())
                .build();
    }
    public static ResponseCategoryDTO mapToResponseCategoryDTO(Category category) {
        return ResponseCategoryDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .build();
    }

    public static Category updateCategory(Category category, RequestCategoryDTO requestCategoryDTO) {
        category.setName(requestCategoryDTO.getName() == null ? category.getName() : requestCategoryDTO.getName());
        category.setImage(requestCategoryDTO.getImage() == null ? category.getImage() : requestCategoryDTO.getImage());
        return category;
    }
}
