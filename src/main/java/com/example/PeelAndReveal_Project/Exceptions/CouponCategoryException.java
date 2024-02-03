package com.example.PeelAndReveal_Project.Exceptions;

public class CouponCategoryException extends Exception{
    public CouponCategoryException() {
        super("Category cannot be empty!");
    }
}
