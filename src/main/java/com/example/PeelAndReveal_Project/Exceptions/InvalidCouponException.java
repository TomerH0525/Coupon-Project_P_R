package com.example.PeelAndReveal_Project.Exceptions;

public class InvalidCouponException extends Exception{
    public InvalidCouponException() {
        super("Invalid coupon....");
    }

    public InvalidCouponException(String message) {
        super(message);
    }
}
