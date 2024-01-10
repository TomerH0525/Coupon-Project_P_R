package com.example.PeelAndReveal_Project.Exceptions;

public class IdNotFoundException extends Exception{
    public IdNotFoundException() {
        super("Error ID not found...");
    }

    public IdNotFoundException(String message) {
        super(message);
    }
}
