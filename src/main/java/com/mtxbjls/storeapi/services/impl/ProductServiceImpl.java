package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.mappers.ProductMapper;
import com.mtxbjls.storeapi.models.Category;
import com.mtxbjls.storeapi.models.Product;
import com.mtxbjls.storeapi.repositories.CategoryRepository;
import com.mtxbjls.storeapi.repositories.ProductRepository;
import com.mtxbjls.storeapi.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ResponseProductDTO createProduct(RequestProductDTO requestProductDTO) {

        if(requestProductDTO.getTitle() == null || requestProductDTO.getTitle().isEmpty()){
            throw new RuntimeException("Title cannot be null or empty");
        }

        if(requestProductDTO.getDescription() == null || requestProductDTO.getDescription().isEmpty()){
            throw new RuntimeException("Description cannot be null or empty");
        }

        if(requestProductDTO.getPrice() == null){
            throw new RuntimeException("Price cannot be null");
        }

        if(requestProductDTO.getCategory_id() == null){
            throw new RuntimeException("Category cannot be null");
        }

        if(requestProductDTO.getImages() == null || requestProductDTO.getImages().length == 0){
            throw new RuntimeException("Images cannot be null or empty");
        }

        Optional<Category> category = categoryRepository.findById(requestProductDTO.getCategory_id());

        if(category.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        Product product = ProductMapper.mapToProduct(requestProductDTO);
        product.setCategory(category.get());
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToResponseProductDTO(savedProduct);
    }

}
