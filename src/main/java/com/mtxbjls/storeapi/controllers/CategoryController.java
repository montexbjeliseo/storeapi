package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.services.ICategoryService;
import com.mtxbjls.storeapi.utils.Contants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Contants.Endpoints.CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCategoryDTO createCategory(@RequestBody RequestCategoryDTO requestCategoryDTO){
        return categoryService.createCategory(requestCategoryDTO);
    }
}
