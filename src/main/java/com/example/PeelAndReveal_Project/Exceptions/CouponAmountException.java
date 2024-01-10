package com.example.PeelAndReveal_Project.Exceptions;

public class CouponAmountException extends Exception{
    public CouponAmountException() {
        super("Coupon : out of stock");
    }

    public CouponAmountException(String message) {
        super(message);
    }
}
