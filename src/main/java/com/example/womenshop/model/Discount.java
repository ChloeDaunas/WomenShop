package com.example.womenshop.model;

public interface Discount {
    public void applyDiscount();
    public void unApplyDiscount();

    double CLOTHES_DISCOUNT =(1- 0.3);
    double SHOES_DISCOUNT =(1- 0.2);
    double ACCESSORY_DISCOUNT =0.5;
}

