package com.example.PeelAndReveal_Project.Exceptions;

public class CouponImageException extends Exception{
    public CouponImageException() {
        super("Encountered an error while trying to save image...");
    }

    public CouponImageException(String message) {
        super(message);
    }
}
