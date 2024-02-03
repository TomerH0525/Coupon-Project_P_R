package com.example.PeelAndReveal_Project.Exceptions;

public class LoginFailedException extends Exception{
    public LoginFailedException() {
        super("Login failed...");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
