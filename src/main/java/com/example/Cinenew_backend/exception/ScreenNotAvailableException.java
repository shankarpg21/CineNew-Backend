package com.example.Cinenew_backend.exception;

public class ScreenNotAvailableException extends RuntimeException{
 
    public ScreenNotAvailableException(String msg){
        super(msg);
    }
}
