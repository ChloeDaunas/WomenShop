package com.example.womenshop;

public class Shoes extends Product{
    private int shoeSize;

    public Shoes(int id, String name, double purchase_price, double sell_price, int shoeSize, StoreFinance storeFinance) {
        super(id, name, purchase_price, sell_price, storeFinance);
        setShoeSize(shoeSize);
        setDiscountPrice(sell_price*SHOES_DISCOUNT);
    }

    public Shoes(int id, String name, double purchase_price, double sell_price, int shoeSize, StoreFinance storeFinance, int stock) {
        super(id,name, purchase_price, sell_price, storeFinance);
        setShoeSize(shoeSize);
        setStock(stock);
        setDiscountPrice(sell_price*SHOES_DISCOUNT);
    }

    public Shoes(String name, double purchase_price, double sell_price, int shoeSize, StoreFinance storeFinance) {
        super(name, purchase_price, sell_price, storeFinance);
        setShoeSize(shoeSize);
        setDiscountPrice(sell_price*SHOES_DISCOUNT);
    }

    private void validateSize(int size) {
        if (size < 36 || size > 50) {
            throw new IllegalArgumentException("Wrong shoe size !");
        }
    }

    @Override
    public void applyDiscount(){
        double discounted = getSellPrice() * SHOES_DISCOUNT;
        discounted = Math.round(discounted * 100.0) / 100.0; // 2 decimals
        setSellPrice(discounted);;;
    }

    @Override
    public void unApplyDiscount(){
        double discounted = getDiscountPrice()/SHOES_DISCOUNT;
        discounted = Math.round(discounted * 100.0) / 100.0; // 2 decimals
        setSellPrice(discounted);;
    }

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        if (shoeSize < 36 || shoeSize > 50) {
            throw new IllegalArgumentException("Wrong shoe size !");
        }
        this.shoeSize = shoeSize;
    }

    @Override
    public String toString(){
        return super.toString() + " shoe size:" + shoeSize;
    }
}

