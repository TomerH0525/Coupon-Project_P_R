package com.example.PeelAndReveal_Project.Exceptions;

public class EmailExistsException extends Exception{
    public EmailExistsException() {
        super("Email already in use...");
    }
}
