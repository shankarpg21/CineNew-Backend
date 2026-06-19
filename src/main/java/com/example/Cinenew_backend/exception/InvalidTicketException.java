package com.example.Cinenew_backend.exception;

public class InvalidTicketException extends RuntimeException{
    
    public InvalidTicketException(String msg){
        super(msg);
    }
}
