package com.mtxbjls.storeapi.mappers;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;
import com.mtxbjls.storeapi.models.Product;

import java.util.List;

public class ProductMapper {

    public static Product mapToProduct(RequestProductDTO requestProductDTO){
        return Product
                .builder()
                .title(requestProductDTO.getTitle())
                .description(requestProductDTO.getDescription())
                .price(requestProductDTO.getPrice())
                .category(null)
                .images(List.of(requestProductDTO.getImages()))
                .build();
    }
    public static ResponseProductDTO mapToResponseProductDTO(Product product){
        return ResponseProductDTO
                .builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(CategoryMapper.mapToResponseCategoryDTO(product.getCategory()))
                .images(product.getImages().toArray(new String[0]))
                .build();
    }
}
