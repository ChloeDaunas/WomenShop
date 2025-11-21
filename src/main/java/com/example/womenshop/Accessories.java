package com.example.womenshop;

public class Accessories extends Product{

    public Accessories(int id, String name, double purchase_price, double sell_price,StoreFinance storeFinance){
        super(id, name, purchase_price, sell_price,storeFinance);
        setDiscountPrice(purchase_price*ACCESSORY_DISCOUNT);
    }

    public Accessories(int id, String name, double purchase_price, double sell_price,StoreFinance storeFinance,int stock){
        super(id, name, purchase_price, sell_price,storeFinance);
        setStock(stock);
        setDiscountPrice(purchase_price*ACCESSORY_DISCOUNT);
    }

    public Accessories(String name, double purchase_price, double sell_price,StoreFinance storeFinance){
        super(name, purchase_price, sell_price,storeFinance);
        setDiscountPrice(purchase_price*ACCESSORY_DISCOUNT);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public void applyDiscount(){
        setSellPrice(getSellPrice()*ACCESSORY_DISCOUNT);
    }

    @Override
    public void unApplyDiscount(){setSellPrice(getDiscountPrice()/ACCESSORY_DISCOUNT);}
}

