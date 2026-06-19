package com.example.Cinenew_backend.exception;

public class SeatUnavailableException extends RuntimeException{

    public SeatUnavailableException(String msg){
        super(msg);
    }
}
