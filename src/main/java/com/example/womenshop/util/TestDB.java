package com.example.womenshop.util;

import com.example.womenshop.DAO.AccessoriesDAO;
import com.example.womenshop.DAO.ClothesDAO;
import com.example.womenshop.DAO.ShoesDAO;
import com.example.womenshop.model.Accessories;
import com.example.womenshop.model.StoreFinance;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        DBManager db = new DBManager();

        System.out.println("➡️ Trying to connect to the database...");

        try (Connection conn = db.connector()) {

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connection successful!");
            } else {
                System.out.println("❌ Connection failed: connection is null or closed.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error while connecting:");
            e.printStackTrace();
        }

        System.out.println("\n--- DAO Tests ---\n");

        TestAccessoriesDAO.run();
        TestClothesDAO.run();
        TestShoesDAO.run();
    }

    // ------------------------- TEST ACCESSORIES DAO -------------------------
    public static class TestAccessoriesDAO {

        public static void run() {
            DBManager db = new DBManager();
            StoreFinance storeFinance = new StoreFinance();

            AccessoriesDAO dao = new AccessoriesDAO(db, storeFinance);

            Accessories acc = new Accessories(
                    1,
                    "Handbag",
                    12.5,
                    25.0,
                    storeFinance
            );

            System.out.println("➡️ Testing: adding an accessory...");

            dao.addProduct(acc);

            System.out.println("✅ Accessory added!");
        }
    }

    // ------------------------- TEST CLOTHES DAO -------------------------
    public static class TestClothesDAO {

        public static void run() {
            DBManager db = new DBManager();
            StoreFinance storeFinance = new StoreFinance();

            ClothesDAO dao = new ClothesDAO(db, storeFinance);

            System.out.println("➡️ Testing: updating a clothing item...");

            dao.modifierProduct(
                    1,              // ID of the clothing item to update
                    "Blue T-Shirt", // new name
                    8.99,           // new purchase price
                    19.99,          // new sell price
                    42              // new size
            );

            System.out.println("✅ Clothing item updated!");
        }
    }

    // ------------------------- TEST SHOES DAO -------------------------
    public static class TestShoesDAO {

        public static void run() {

            DBManager db = new DBManager();
            StoreFinance storeFinance = new StoreFinance();

            ShoesDAO dao = new ShoesDAO(db, storeFinance);

            System.out.println("➡️ Testing: updating stock...");
            dao.UpdateStock(1, 15);
            System.out.println("✅ Stock updated!");

            System.out.println("➡️ Testing: deleting a shoe...");
            dao.deleteProduct(1);
            System.out.println("✅ Shoe deleted!");
        }
    }
}
