package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;

import java.util.List;

public interface IProductService {
    ResponseProductDTO createProduct(RequestProductDTO requestProductDTO);

    List<ResponseProductDTO> getAllProducts(int offset, int limit);

    ResponseProductDTO getProductById(Long id);

    ResponseProductDTO updateProduct(Long id, RequestProductDTO requestProductDTO);

    ResponseProductDTO deleteProduct(Long id);

    List<ResponseProductDTO> getFilteredProducts(
            String title,
            Long category_id,
            Double price_min,
            Double price_max,
            int offset,
            int limit
    );
}
