package com.mtxbjls.storeapi.controllers;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.services.IProductService;
import com.mtxbjls.storeapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.Endpoints.PRODUCTS)
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseProductDTO createProduct(@RequestBody RequestProductDTO requestProductDTO) {
        return productService.createProduct(requestProductDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseProductDTO> getAllProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Double price_min,
            @RequestParam(required = false) Double price_max,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int limit
    ) {
        if (title != null || category_id != null || price_min != null && price_max != null) {
            return productService.getFilteredProducts(title, category_id, price_min, price_max, offset, limit);
        } else {
            return productService.getAllProducts(offset, limit);
        }
    }

    @GetMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO updateProduct(@PathVariable Long id, @RequestBody RequestProductDTO requestProductDTO) {
        return productService.updateProduct(id, requestProductDTO);
    }

    @DeleteMapping(Constants.PathVariables.ID)
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
