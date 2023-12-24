package com.mtxbjls.storeapi.services;

import com.mtxbjls.storeapi.dtos.RequestProductDTO;
import com.mtxbjls.storeapi.dtos.ResponseProductDTO;

public interface IProductService {
    public ResponseProductDTO createProduct(RequestProductDTO requestProductDTO);
}
