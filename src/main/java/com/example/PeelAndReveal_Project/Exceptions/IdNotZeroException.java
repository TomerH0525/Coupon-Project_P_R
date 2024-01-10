package com.example.PeelAndReveal_Project.Exceptions;

public class IdNotZeroException extends Exception {
    public IdNotZeroException() {
        super("Cannot create customer (Id has to be 0)");
    }
}
