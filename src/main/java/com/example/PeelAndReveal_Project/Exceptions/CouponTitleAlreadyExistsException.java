package com.example.PeelAndReveal_Project.Exceptions;

public class CouponTitleAlreadyExistsException extends Exception{
    public CouponTitleAlreadyExistsException(String couponTitle) {
        super("Coupon with the title "+"'"+couponTitle+"'"+" already exists in your company");
    }

    public CouponTitleAlreadyExistsException() {
        super("Coupon with this title already exists.");
    }
}
