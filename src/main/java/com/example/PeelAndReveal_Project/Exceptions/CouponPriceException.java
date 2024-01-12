package com.example.PeelAndReveal_Project.Exceptions;

public class CouponPriceException extends Exception {
    public CouponPriceException() {
        super("Price cannot be 0 or below!!");
    }

    public CouponPriceException(String message) {
        super(message);
    }
}
