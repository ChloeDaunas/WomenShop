package com.example.womenshop;

public class Clothes extends Product{
    private int size;

    public Clothes(int id, String name, double purchase_price, double sell_price, int size, StoreFinance storeFinance){
        super(id, name, purchase_price, sell_price,storeFinance);
        setSize(size);
    }

    public Clothes(int id, String name, double purchase_price, double sell_price, int size, StoreFinance storeFinance, int stock){
        super(id,name, purchase_price, sell_price,storeFinance);
        setSize(size);
        setStock(stock);
    }

    public Clothes(String name, double purchase_price, double sell_price, int size, StoreFinance storeFinance){
        super(name, purchase_price, sell_price,storeFinance);
        setSize(size);
    }

    @Override
    public void applyDiscount(){
        setSellPrice(getSellPrice()*CLOTHES_DISCOUNT);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size % 2 != 0 || size < 34 || size > 54) {
            throw new IllegalArgumentException("wrong size !");
        }
        this.size = size;
    }

    @Override
    public double getPurchasePrice() {
        return super.getPurchasePrice();
    }

    @Override
    public double getSellPrice() {
        return super.getSellPrice();
    }

    @Override
    public String toString() {
        return super.toString() + " Clothes{" + "size=" + size + '}';
    }
}

