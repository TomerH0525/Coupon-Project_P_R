package com.example.PeelAndReveal_Project.Exceptions;

public class CouponDescriptionException extends Exception{
    public CouponDescriptionException() {
        super("Description needs to be atleast 20 characters!");
    }
}
