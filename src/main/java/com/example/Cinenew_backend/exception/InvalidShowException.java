package com.example.Cinenew_backend.exception;

public class InvalidShowException extends RuntimeException{
    
    public InvalidShowException(String msg){
        super(msg);
    }
}
