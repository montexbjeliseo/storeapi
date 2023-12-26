package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;

import java.util.List;

public interface IProductService {
    public ResponseProductDTO createProduct(RequestProductDTO requestProductDTO);

    public List<ResponseProductDTO> getAllProducts(int offset, int limit);

    public ResponseProductDTO getProductById(Long id);

    public ResponseProductDTO updateProduct(Long id, RequestProductDTO requestProductDTO);

    public ResponseProductDTO deleteProduct(Long id);

    public List<ResponseProductDTO> getFilteredProducts(
            String title,
            Long category_id,
            Double price_min,
            Double price_max,
            int offset,
            int limit
    );
}
