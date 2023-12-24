package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.models.Category;

import java.util.List;

public interface ICategoryService {
    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO);
    public List<ResponseCategoryDTO> getAllCategories();
    public ResponseCategoryDTO getCategoryById(Long id);
    public ResponseCategoryDTO updateCategory(Long id, RequestCategoryDTO requestCategoryDTO);
}
