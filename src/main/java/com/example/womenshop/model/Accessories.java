package com.example.womenshop.model;

public class Accessories extends Product {

    public Accessories(int id, String name, double purchase_price, double sell_price, StoreFinance storeFinance){
        super(id, name, purchase_price, sell_price,storeFinance);
        setDiscountPrice(sell_price*ACCESSORY_DISCOUNT);
    }

    public Accessories(int id, String name, double purchase_price, double sell_price,StoreFinance storeFinance,int stock){
        super(id, name, purchase_price, sell_price,storeFinance);
        setStock(stock);
        setDiscountPrice(sell_price*ACCESSORY_DISCOUNT);
    }

    public Accessories(String name, double purchase_price, double sell_price,StoreFinance storeFinance){
        super(name, purchase_price, sell_price,storeFinance);
        setDiscountPrice(sell_price*ACCESSORY_DISCOUNT);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public void applyDiscount(){
        double discounted = getSellPrice() * ACCESSORY_DISCOUNT;
        discounted = Math.round(discounted * 100.0) / 100.0; // 2 decimals
        setSellPrice(discounted);;;
    }

    @Override
    public void unApplyDiscount(){
        double discounted = getDiscountPrice()/ACCESSORY_DISCOUNT;
        discounted = Math.round(discounted * 100.0) / 100.0; // 2 decimals
        setSellPrice(discounted);;
    }
}

