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
    private ListView<Accessories> AccessoriesLV;

    @FXML
    private ListView<Shoes> ShoesLV;

    @FXML
    private ListView<Clothes> ClothesLV;

    @FXML
    private Button ClearBtn;

    private StoreFinance storeFinance;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //filling the combobox
        List<String> gvalues = new ArrayList<>();
        gvalues.add("Clothes");
        gvalues.add("Shoes");
        gvalues.add("Accessories");
        ObservableList<String> type = FXCollections.observableArrayList(gvalues);
        TypeCB.setItems(type);

        loadClothesInListView();
        loadShoesInListView();
        loadAccessoriesInListView();



        ClothesLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> fillProductFields(newValue)
        );

        ShoesLV.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> fillProductFields(newValue)
        );

        AccessoriesLV.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> fillProductFields(newValue)
        );

        storeFinance = dbManager.getStoreFinance();
        displayFinance();

        AddBtn.setOnAction(event -> addProduct());

        ClearBtn.setOnAction(event -> clearFields());

        ModifyBtn.setOnAction(event -> modifyProduct());

        DeleteBtn.setOnAction(event -> deleteProduct());
    }

    private DBManager dbManager = new DBManager();

    //putting clothes in the tab
    public void loadClothesInListView() {
        List<Clothes> allClothes = dbManager.loadClothes();

        ClothesLV.getItems().addAll(allClothes);

        ClothesLV.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Clothes item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.getName() + " — stock: " + item.getStock());
            }
        });
    }

    //putting shoes in the tab
    public void loadShoesInListView() {
        List<Shoes> allShoes = dbManager.loadShoes();

        ShoesLV.getItems().addAll(allShoes);

        ShoesLV.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Shoes item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.getName() + " — stock: " + item.getStock());
            }
        });
    }

    //putting accessories in the tab
    public void loadAccessoriesInListView() {
        List<Accessories> allAccessories = dbManager.loadAccessories();

        AccessoriesLV.getItems().addAll(allAccessories);

        AccessoriesLV.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Accessories item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.getName() + " — stock: " + item.getStock());
            }
        });
    }

    //putting the infos in the boxes
    private void fillProductFields(Product p) {
        if (p == null) return;

        NameTF.setText(p.getName());
        PourchasePriseTF.setText(String.valueOf(p.getPurchasePrice()));
        SellPriceTF.setText(String.valueOf(p.getSellPrice()));


        if (p instanceof Clothes c) {
            SizeTF.setText(String.valueOf(c.getSize()));
            TypeCB.getSelectionModel().select("Clothes");
        }
        else if (p instanceof Shoes s) {
            SizeTF.setText(String.valueOf(s.getShoeSize()));
            TypeCB.getSelectionModel().select("Shoes");
        }
        else if (p instanceof Accessories a) {
            SizeTF.clear(); // Pas de taille
            TypeCB.getSelectionModel().select("Accessories");
        }
    }

    private void displayFinance() {
        if (storeFinance == null) return;

        capitalTF.setText(String.valueOf(storeFinance.getCapital()));
        IncomesTF.setText(String.valueOf(storeFinance.getIncome()));
        CostsTF.setText(String.valueOf(storeFinance.getCost()));
    }

    public void addProduct() {

        String type = TypeCB.getValue();
        if (type == null) return;

        String name = NameTF.getText();
        int size = Integer.parseInt(SizeTF.getText());
        double purchase = Double.parseDouble(PourchasePriseTF.getText());
        double sell = Double.parseDouble(SellPriceTF.getText());

        Product newProduct = null;

        switch (type) {
            case "Clothes":
                newProduct = new Clothes(name, purchase, sell, size, storeFinance);
                break;

            case "Shoes":
                newProduct = new Shoes(name, purchase, sell, size, storeFinance);
                break;

            case "Accessories":
                newProduct = new Accessories(name, purchase, sell, storeFinance);
                break;
        }

        if (newProduct == null) return;

        //Save in DB
        dbManager.addProduct(newProduct);

        refreshAllTabs();

        clearFields();
    }

    public void modifyProduct() {
        Product selectedProduct;

        String type = TypeCB.getValue();

        if (type == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setHeaderText(null);
            alert.setContentText("Select a product to modify");
            alert.showAndWait();
        }

        if(type.equals("Clothes")){
            selectedProduct =ClothesLV.getSelectionModel().getSelectedItem();
        }
        else if(type.equals("Shoes")){
            selectedProduct =ShoesLV.getSelectionModel().getSelectedItem();
        }
        else {
            selectedProduct =AccessoriesLV.getSelectionModel().getSelectedItem();
        }



        String name = NameTF.getText();
        double purchaseprice = Double.parseDouble(PourchasePriseTF.getText());
        double sellprice = Double.parseDouble(SellPriceTF.getText());

        Integer size = null;
        if (!type.equals("Accessories")) {
            size = Integer.parseInt(SizeTF.getText());
        }

        dbManager.modifierProduct(selectedProduct.getId(),name, purchaseprice, sellprice, size, type);

        refreshAllTabs();
        clearFields();
    }

    public void deleteProduct() {
        Product selectedProduct;

        String type = TypeCB.getValue();

        if (type == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setHeaderText(null);
            alert.setContentText("Select a product to delete");
            alert.showAndWait();
        }

        if(type.equals("Clothes")){
            selectedProduct =ClothesLV.getSelectionModel().getSelectedItem();
        }
        else if(type.equals("Shoes")){
            selectedProduct =ShoesLV.getSelectionModel().getSelectedItem();
        }
        else {
            selectedProduct =AccessoriesLV.getSelectionModel().getSelectedItem();
        }

        dbManager.deleteProduct(selectedProduct.getId(), type);

        refreshAllTabs();
        clearFields();
    }

    private void refreshAllTabs() {
        ClothesLV.getItems().clear();
        ShoesLV.getItems().clear();
        AccessoriesLV.getItems().clear();

        loadClothesInListView();
        loadShoesInListView();
        loadAccessoriesInListView();
    }

    private void clearFields() {
        NameTF.clear();
        SizeTF.clear();
        SellPriceTF.clear();
        PourchasePriseTF.clear();
        TypeCB.getSelectionModel().clearSelection();
    }


}
