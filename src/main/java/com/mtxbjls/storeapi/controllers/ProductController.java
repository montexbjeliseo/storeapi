package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.services.IProductService;
import com.mtxbjls.storeapi.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = Constants.Docs.Tags.PRODUCTS, description = Constants.Docs.Tags.PRODUCTS_DESCRIPTION)
@RestController
@RequestMapping(value = Constants.Endpoints.PRODUCTS)
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @Operation(
            summary = Constants.Docs.Operations.PRODUCTS_CREATE_NEW,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CREATED,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_CREATED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.BAD_REQUEST,
                description = Constants.Docs.ResponseDescriptions.BAD_REQUEST
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.CONFLICT,
                description = Constants.Docs.ResponseDescriptions.CONFLICT
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProductDTO createProduct(@RequestBody RequestProductDTO requestProductDTO) {
        return productService.createProduct(requestProductDTO);
    }

    @Operation(
            summary = Constants.Docs.Operations.PRODUCTS_GET_ALL
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_RETRIEVED
        )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDTO> getAllProducts(
            @Parameter(
                    name = Constants.Docs.Parameters.TITLE,
                    description = Constants.Docs.Parameters.TITLE_DESCRIPTION
            )
            @RequestParam(required = false) String title,
            @Parameter(
                    name = Constants.Docs.Parameters.CATEGORY_ID,
                    description = Constants.Docs.Parameters.CATEGORY_ID_DESCRIPTION
            )
            @RequestParam(required = false) Long category_id,
            @Parameter(
                    name = Constants.Docs.Parameters.PRICE_MIN,
                    description = Constants.Docs.Parameters.PRICE_MIN_DESCRIPTION
            )
            @RequestParam(required = false) Double price_min,
            @Parameter(
                    name = Constants.Docs.Parameters.PRICE_MAX,
                    description = Constants.Docs.Parameters.PRICE_MAX_DESCRIPTION
            )
            @RequestParam(required = false) Double price_max,
            @Parameter(
                    name = Constants.Docs.Parameters.OFFSET,
                    description = Constants.Docs.Parameters.OFFSET_DESCRIPTION
            )
            @RequestParam(defaultValue = "0") int offset,
            @Parameter(
                    name = Constants.Docs.Parameters.LIMIT,
                    description = Constants.Docs.Parameters.LIMIT_DESCRIPTION
            )
            @RequestParam(defaultValue = "100") int limit
    ) {
        if (title != null || category_id != null || price_min != null && price_max != null) {
            return productService.getFilteredProducts(title, category_id, price_min, price_max, offset, limit);
        } else {
            return productService.getAllProducts(offset, limit);
        }
    }

    @Operation(
            summary = Constants.Docs.Operations.PRODUCTS_GET_BY_ID
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_RETRIEVED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.NOT_FOUND,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_NOT_FOUND
        )
    })
    @GetMapping(value = Constants.PathVariables.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(
            summary = Constants.Docs.Operations.PRODUCTS_UPDATE,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_UPDATED
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
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN
        )
    })
    @PatchMapping(value = Constants.PathVariables.ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO updateProduct(@PathVariable Long id, @RequestBody RequestProductDTO requestProductDTO) {
        return productService.updateProduct(id, requestProductDTO);
    }

    @Operation(
            summary = Constants.Docs.Operations.PRODUCTS_DELETE,
            security = @SecurityRequirement(name = Constants.Docs.BEARER_AUTH)
    )
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.SUCCESS,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_DELETED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.UNAUTHORIZED,
                description = Constants.Docs.ResponseDescriptions.UNAUTHORIZED
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.FORBIDDEN,
                description = Constants.Docs.ResponseDescriptions.FORBIDDEN
        ),
        @ApiResponse(
                responseCode = Constants.Docs.ResponseCodes.NOT_FOUND,
                description = Constants.Docs.ResponseDescriptions.PRODUCT_NOT_FOUND
        )
    })
    @DeleteMapping(value = Constants.PathVariables.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
