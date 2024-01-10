package com.example.PeelAndReveal_Project.Exceptions;

public class CouponDateException extends Exception{
    public CouponDateException() {
        super("Coupon expired...");
    }

    public CouponDateException(String message) {
        super(message);
    }
}
