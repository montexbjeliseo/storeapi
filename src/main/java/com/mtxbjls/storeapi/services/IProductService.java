package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;

import java.util.List;

public interface IProductService {
    public ResponseProductDTO createProduct(RequestProductDTO requestProductDTO);
    public List<ResponseProductDTO> getAllProducts();
    public ResponseProductDTO getProductById(Long id);
}
