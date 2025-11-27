package com.example.womenshop.model;

import javafx.scene.control.Alert;

public abstract class Product implements Discount, Comparable<Product> {

    private static int number = 0;

    private final int id;    //final so we can not change it once initialized
    private final String name;
    private double purchasePrice;
    private double sellPrice;
    private double discountPrice;
    private int stock;

    private final StoreFinance storeFinance;

    public Product(int id, String name, double purchasePrice, double sellPrice, StoreFinance storeFinance) {

        validatePrice(purchasePrice);
        validatePrice(sellPrice);

        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.discountPrice = 0;
        this.stock = 0;

        this.storeFinance = storeFinance;

        this.id = id;
        number=id;
    }

    public Product(int id, String name, double purchasePrice, double sellPrice, StoreFinance storeFinance, int stock) {

        validatePrice(purchasePrice);
        validatePrice(sellPrice);

        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.discountPrice = 0;
        this.stock = stock;

        this.storeFinance = storeFinance;

        this.id = id;
        number=id;
    }

    public Product(String name, double purchasePrice, double sellPrice, StoreFinance storeFinance) {

        validatePrice(purchasePrice);
        validatePrice(sellPrice);

        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.discountPrice = 0;
        this.stock = 0;

        this.storeFinance = storeFinance;
        number=number+1;
        this.id = number;

    }


    private void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Negative price!");
        }
    }


    public void purchase(int quantity) {
        double total = quantity * purchasePrice;

        if (!storeFinance.canBuy(total)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Not enough capital to buy products.");
            alert.showAndWait();
        }
        if(quantity <0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Can not buy a negative quantity.");
            alert.showAndWait();
        }

        stock += quantity;
        storeFinance.registerPurchase(total);
    }


    public void sell(int quantity) {
        if (quantity > stock) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Not enough stock to sell products.");
            alert.showAndWait();


        }
        else{
            stock -= quantity;
            double gain = quantity * getEffectiveSellPrice();

            storeFinance.registerSale(gain);
        }


    }



    public void unApplyDiscount() {}

    @Override
    public void applyDiscount() {}

    public double getEffectiveSellPrice() {
        return discountPrice > 0 ? discountPrice : sellPrice;
    }


    @Override
    public int compareTo(Product o) {
        return Double.compare(this.sellPrice, o.sellPrice);
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public int getStock() {
        return stock;
    }

    public StoreFinance getStoreFinance() {
        return storeFinance;
    }


    public void setPurchasePrice(double purchasePrice) {
        validatePrice(purchasePrice);
        this.purchasePrice = purchasePrice;
    }

    public void setSellPrice(double sellPrice) {
        validatePrice(sellPrice);
        this.sellPrice = sellPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        validatePrice(discountPrice);
        this.discountPrice = discountPrice;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product #" + id +
                " | " + name +
                " | purchase: " + purchasePrice +
                " | sell: " + sellPrice +
                " | discount: " + discountPrice +
                " | stock: " + stock;
    }
}
