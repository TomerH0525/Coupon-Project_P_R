package com.example.PeelAndReveal_Project.Exceptions;

public class CouponNotExistException extends Exception{
    public CouponNotExistException() {
        super("Coupon not found");
    }

    public CouponNotExistException(String message) {
        super(message);
    }
}
