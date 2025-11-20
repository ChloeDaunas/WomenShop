package com.example.womenshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ProductController implements Initializable{
    @FXML
    private Tab AccessoriesTab;

    @FXML
    private Button AddBtn;

    @FXML
    private Button ApplyBtn;

    @FXML
    private Tab ClothesTab;

    @FXML
    private TextField CostsTF;

    @FXML
    private Button DeleteBtn;

    @FXML
    private TextField IncomesTF;

    @FXML
    private Button ModifyBtn;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField PourchasePriseTF;

    @FXML
    private Button PurchaseBtn;

    @FXML
    private Button SellBtn;

    @FXML
    private TextField SellPriceTF;

    @FXML
    private Tab ShoesTab;

    @FXML
    private TextField SizeTF;

    @FXML
    private Button SortBtn;

    @FXML
    private Button StopBtn;

    @FXML
    private ComboBox<String > TypeCB;

    @FXML
    private TextField capitalTF;

    @FXML
    private ListView<String> AccessoriesLV;

    @FXML
    private ListView<String> ShoesLV;

    @FXML
    private ListView<String> ClothesLV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //filling the combobox
        List<String> gvalues = new ArrayList<>();
        gvalues.add("Clothes");
        gvalues.add("Shoes");
        gvalues.add("Accessories");
        ObservableList<String> type = FXCollections.observableArrayList(gvalues);
        TypeCB.setItems(type);
    }

    private DBManager dbManager = new DBManager();

    //putting clothes in the tab
    public void loadClothesInListView() {
        List<Clothes> allClothes = dbManager.loadClothes();

        for (Clothes c : allClothes) {
            String item = c.getName() + " — stock: " + c.getStock();
            ClothesLV.getItems().add(item);
        }
    }

    //putting shoes in the tab
    public void loadShoesInListView() {
        List<Shoes> allShoes = dbManager.loadShoes();

        for (Shoes s : allShoes) {
            String item = s.getName() + " — stock: " + s.getStock();
            ShoesLV.getItems().add(item);
        }
    }

    //putting accessories in the tab
    public void loadAccessoriesInListView() {
        List<Accessories> allAccessories = dbManager.loadAccessories();

        for (Accessories a : allAccessories) {
            String item = a.getName() + " — stock: " + a.getStock();
            AccessoriesLV.getItems().add(item);
        }
    }

}
