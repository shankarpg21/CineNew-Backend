package com.example.Cinenew_backend.exception;


public class MovieNotAvailableException extends RuntimeException{
    
    public MovieNotAvailableException(String msg){
        super(msg);
    }
}
