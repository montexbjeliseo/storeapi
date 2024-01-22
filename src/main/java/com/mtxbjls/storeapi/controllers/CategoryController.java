package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.dtos.RequestCategoryDTO;
import com.mtxbjls.storeapi.dtos.ResponseCategoryDTO;
import com.mtxbjls.storeapi.services.ICategoryService;
import com.mtxbjls.storeapi.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = Constants.Docs.Tags.CATEGORIES, description = Constants.Docs.Tags.CATEGORIES_DESCRIPTION)
@RestController
@RequestMapping(Constants.Endpoints.CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @Operation(
            summary = Constants.Docs.Operations.CATEGORIES_CREATE_NEW,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CREATED,
                description = Constants.Docs.ResponseDescriptions.CATEGORY_CREATED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CONFLICT,
                description = Constants.Docs.ResponseDescriptions.CONFLICT),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.INTERNAL_SERVER_ERROR,
                description = Constants.Docs.ResponseDescriptions.INTERNAL_SERVER_ERROR),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCategoryDTO createCategory(@RequestBody RequestCategoryDTO requestCategoryDTO){
        return categoryService.createCategory(requestCategoryDTO);
    }

    @Operation(summary = Constants.Docs.Operations.CATEGORIES_GET_ALL)
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.CATEGORIES_RETRIEVED
        ),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCategoryDTO> getCategories(){
        return categoryService.getAllCategories();
    }

    @Operation(
            summary = Constants.Docs.Operations.CATEGORIES_GET_BY_ID,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @GetMapping(value = Constants.PathVariables.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @Operation(
            summary = Constants.Docs.Operations.CATEGORIES_UPDATE,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.CATEGORY_UPDATED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.INTERNAL_SERVER_ERROR,
                description = Constants.Docs.ResponseDescriptions.INTERNAL_SERVER_ERROR
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.NOT_FOUND,
                description = Constants.Docs.ResponseDescriptions.CATEGORY_NOT_FOUND
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CONFLICT,
                description = Constants.Docs.ResponseDescriptions.CONFLICT
        )
    })
    @PatchMapping(value = Constants.PathVariables.ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO updateCategory(@PathVariable Long id, @RequestBody RequestCategoryDTO requestCategoryDTO){
        return categoryService.updateCategory(id, requestCategoryDTO);
    }

    @Operation(
            summary = Constants.Docs.Operations.CATEGORIES_DELETE,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.CATEGORY_DELETED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.INTERNAL_SERVER_ERROR,
                description = Constants.Docs.ResponseDescriptions.INTERNAL_SERVER_ERROR
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN
        )
    })
    @DeleteMapping(value = Constants.PathVariables.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseCategoryDTO deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
