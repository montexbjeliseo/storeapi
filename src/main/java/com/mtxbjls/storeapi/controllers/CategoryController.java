package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.services.ICategoryService;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.Endpoints.CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCategoryDTO createCategory(@RequestBody RequestCategoryDTO requestCategoryDTO){
        return categoryService.createCategory(requestCategoryDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCategoryDTO> getCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PatchMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO updateCategory(@PathVariable Long id, @RequestBody RequestCategoryDTO requestCategoryDTO){
        return categoryService.updateCategory(id, requestCategoryDTO);
    }

    @DeleteMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
