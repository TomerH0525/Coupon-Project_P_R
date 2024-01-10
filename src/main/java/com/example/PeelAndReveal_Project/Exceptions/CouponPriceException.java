package com.example.PeelAndReveal_Project.Exceptions;

public class CouponPriceException extends Exception {
    public CouponPriceException() {
        super("Price cannot be below 0!");
    }

    public CouponPriceException(String message) {
        super(message);
    }
}
