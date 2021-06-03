package com.example.writeo.exception;

public class JPAException extends Exception{
    @Override
    public String getMessage() {
        return "JPA Method couldn't be executed!";
    }
}
