package com.example.Cinenew_backend.exception;

public class CancelTicketException extends RuntimeException{
    
    public CancelTicketException(String msg){
        super(msg);
    }
}
