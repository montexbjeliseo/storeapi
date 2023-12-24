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
    public List<ResponseProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
