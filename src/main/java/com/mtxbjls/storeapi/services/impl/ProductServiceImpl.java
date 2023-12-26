package com.mtxbjls.storeapi.services.impl;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.exceptions.MandatoryFieldException;
import com.mtxbjls.storeapi.exceptions.ResourceNotFoundException;
import com.mtxbjls.storeapi.mappers.ProductMapper;
import com.mtxbjls.storeapi.models.Category;
import com.mtxbjls.storeapi.models.Product;
import com.mtxbjls.storeapi.repositories.CategoryRepository;
import com.mtxbjls.storeapi.repositories.ProductRepository;
import com.mtxbjls.storeapi.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseProductDTO createProduct(RequestProductDTO requestProductDTO) {

        if (requestProductDTO.getTitle() == null || requestProductDTO.getTitle().isEmpty()) {
            throw new MandatoryFieldException("Title cannot be null or empty");
        }

        if (requestProductDTO.getDescription() == null || requestProductDTO.getDescription().isEmpty()) {
            throw new MandatoryFieldException("Description cannot be null or empty");
        }

        if (requestProductDTO.getPrice() == null) {
            throw new MandatoryFieldException("Price cannot be null");
        }

        if (requestProductDTO.getCategory_id() == null) {
            throw new MandatoryFieldException("Category cannot be null");
        }

        if (requestProductDTO.getImages() == null || requestProductDTO.getImages().length == 0) {
            throw new MandatoryFieldException("Images cannot be null or empty");
        }

        Optional<Category> category = categoryRepository.findById(requestProductDTO.getCategory_id());

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }

        Product product = ProductMapper.mapToProduct(requestProductDTO);
        product.setCategory(category.get());
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToResponseProductDTO(savedProduct);
    }

    @Override
    public List<ResponseProductDTO> getAllProducts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::mapToResponseProductDTO).toList();
    }

    @Override
    public ResponseProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        return ProductMapper.mapToResponseProductDTO(product.get());
    }

    @Override
    public ResponseProductDTO updateProduct(Long id, RequestProductDTO requestProductDTO) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        if (requestProductDTO == null) {
            throw new RuntimeException("No data provided");
        }

        Product updatedProduct = ProductMapper.updateProduct(product.get(), requestProductDTO);
        if (requestProductDTO.getCategory_id() != null) {
            Optional<Category> category = categoryRepository.findById(requestProductDTO.getCategory_id());
            if (category.isEmpty()) {
                throw new ResourceNotFoundException("Category not found");
            }
            updatedProduct.setCategory(category.get());
        }
        return ProductMapper.mapToResponseProductDTO(productRepository.save(updatedProduct));
    }

    @Override
    @Transactional
    public ResponseProductDTO deleteProduct(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ResponseProductDTO responseProductDTO = ProductMapper.mapToResponseProductDTO(product);
        productRepository.deleteById(id);
        return responseProductDTO;
    }

    @Override
    public List<ResponseProductDTO> getFilteredProducts(
            String title,
            Long category_id,
            Double price_min,
            Double price_max,
            int offset,
            int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Product> products = productRepository.filterProducts(title, category_id, price_min, price_max, pageable);
        return products.map(ProductMapper::mapToResponseProductDTO).toList();
    }

}
