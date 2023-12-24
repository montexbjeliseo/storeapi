package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.models.Category;

public interface ICategoryService {
    public ResponseCategoryDTO createCategory(RequestCategoryDTO requestCategoryDTO);
}
