package com.example.PeelAndReveal_Project.Exceptions;

public class NameExistsException extends Exception{
    public NameExistsException() {
        super("Name already exists...");
    }

    public NameExistsException(String message) {
        super(message);
    }
}
