package com.example.womenshop.DAO;


import com.example.womenshop.model.*;
import com.example.womenshop.util.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesDAO {
    private DBManager dbManager;
    private StoreFinance storeFinance;

    public AccessoriesDAO(DBManager dbManager, StoreFinance storeFinance) {
        this.dbManager = dbManager;
        this.storeFinance = storeFinance;
    }

    public List<Accessories> loadAccessories() {
        List<Accessories> allAccessories = new ArrayList<>();
        try (Connection conn = dbManager.connector();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM accessories")) {

            while (rs.next()) {
                Accessories a = new Accessories(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("purchase_price"),
                        rs.getDouble("sell_price"),
                        storeFinance,
                        rs.getInt("stock")
                );
                allAccessories.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAccessories;
    }

    //methode to add a product
    public void addProduct(Product p) {
        try (Connection conn = dbManager.connector()) {
            String sql ="INSERT INTO accessories (name, purchase_price, sell_price) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());

            ps.setDouble(2, p.getPurchasePrice());
            ps.setDouble(3, p.getSellPrice());


            ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierProduct(int id, String newName, double newPurchasePrice, double newSellPrice, Integer size) {
        String sql = "UPDATE accessories SET name = ?, purchase_price = ?, sell_price = ? WHERE id = ?";
        try (Connection conn = dbManager.connector()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setDouble(2, newPurchasePrice);
            stmt.setDouble(3, newSellPrice);
            stmt.setInt(4, id);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM accessories WHERE id = ?";

        try (Connection conn = dbManager.connector()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateStock(int id, int stock) {
        String sql = "UPDATE accessories SET stock = ? WHERE id = ?";


        try (Connection conn = dbManager.connector()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, stock);
            stmt.setInt(2, id);

            stmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

