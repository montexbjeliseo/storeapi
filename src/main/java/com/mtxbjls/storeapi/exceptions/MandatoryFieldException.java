package com.mtxbjls.storeapi.exceptions;

public class MandatoryFieldException extends RuntimeException{
    public MandatoryFieldException(String message){
        super(message);
    }
}
