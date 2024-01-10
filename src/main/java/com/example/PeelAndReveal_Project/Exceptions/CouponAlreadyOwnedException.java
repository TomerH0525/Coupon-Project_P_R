package com.example.PeelAndReveal_Project.Exceptions;

public class CouponAlreadyOwnedException extends Exception{
    public CouponAlreadyOwnedException() {
        super("Coupon is already owned!");
    }
}
