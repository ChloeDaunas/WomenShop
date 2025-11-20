package com.example.womenshop;

public class Accessories extends Product{

    public Accessories(String name, double purchase_price, double sell_price,StoreFinance storeFinance){
        super(name, purchase_price, sell_price,storeFinance);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public void applyDiscount(){
        setSellPrice(getSellPrice()*ACCESSORY_DISCOUNT);
    }
}

