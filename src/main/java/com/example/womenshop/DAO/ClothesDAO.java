package com.example.womenshop.DAO;


import com.example.womenshop.model.Clothes;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.StoreFinance;
import com.example.womenshop.util.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothesDAO {
    private DBManager dbManager;
    private StoreFinance storeFinance;

    public ClothesDAO(DBManager dbManager, StoreFinance storeFinance) {
        this.dbManager = dbManager;
        this.storeFinance = storeFinance;
    }

    public List<Clothes> loadClothes() {
        List<Clothes> allClothes = new ArrayList<>();
        try (Connection conn = dbManager.connector();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM clothes")) {

            while (rs.next()) {
                Clothes c = new Clothes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("purchase_price"),
                        rs.getDouble("sell_price"),
                        rs.getInt("size"),
                        storeFinance,
                        rs.getInt("stock")
                );
                allClothes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allClothes;
    }

    public void addProduct(Product p) {
        try (Connection conn = dbManager.connector();) {
            String sql = "INSERT INTO clothes (name, purchase_price, sell_price, size) VALUES (?, ?, ?, ?)";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPurchasePrice());
            ps.setDouble(3, p.getSellPrice());
            ps.setInt(4, ((Clothes) p).getSize());


            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierProduct(int id, String newName, double newPurchasePrice, double newSellPrice, Integer size) {
        String sql = "UPDATE clothes SET name = ?, purchase_price = ?, sell_price = ?, size = ? WHERE id = ?";

        try (Connection conn = dbManager.connector()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setDouble(2, newPurchasePrice);
            stmt.setDouble(3, newSellPrice);
            stmt.setInt(4, size);
            stmt.setInt(5, id);


            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM clothes WHERE id = ?";

        try (Connection conn = dbManager.connector()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateStock(int id, int stock) {
        String sql = "UPDATE clothes SET stock = ? WHERE id = ?";


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

