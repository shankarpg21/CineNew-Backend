package com.example.Cinenew_backend.exception;

public class InvalidSeatException extends RuntimeException{    
    public InvalidSeatException(String msg){
        super(msg);
    }
}
