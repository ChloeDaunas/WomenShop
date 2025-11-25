package com.example.womenshop.Controller;

import com.example.womenshop.DAO.AccessoriesDAO;
import com.example.womenshop.DAO.ClothesDAO;
import com.example.womenshop.DAO.ShoesDAO;
import com.example.womenshop.model.*;
import com.example.womenshop.util.DBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

    @FXML
    private TextField PurchaseTF;

    @FXML
    private TextField SellTF;

    private StoreFinance storeFinance;

    private DBManager dbManager = new DBManager();

    private AccessoriesDAO Adao;
    private ShoesDAO Sdao;
    private ClothesDAO Cdao;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //filling the combobox
        List<String> gvalues = new ArrayList<>();
        gvalues.add("Clothes");
        gvalues.add("Shoes");
        gvalues.add("Accessories");
        ObservableList<String> type = FXCollections.observableArrayList(gvalues);
        TypeCB.setItems(type);

        storeFinance = dbManager.getStoreFinance();
        displayFinance();

        Adao=new AccessoriesDAO(dbManager, storeFinance);
        Sdao=new ShoesDAO(dbManager, storeFinance);
        Cdao=new ClothesDAO(dbManager, storeFinance);

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



        AddBtn.setOnAction(event -> addProduct());

        ClearBtn.setOnAction(event -> clearFields());

        ModifyBtn.setOnAction(event -> modifyProduct());

        DeleteBtn.setOnAction(event -> deleteProduct());

        SellBtn.setOnAction(event -> sellProduct());

        PurchaseBtn.setOnAction(event -> purchaseProduct());

        ApplyBtn.setOnAction(event -> ChangePrices(1));

        StopBtn.setOnAction(event -> ChangePrices(2));

        SortBtn.setOnAction(event -> SortProducts());
    }




    //putting clothes in the tab
    public void loadClothesInListView() {
        List<Clothes> allClothes = Cdao.loadClothes();

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
        List<Shoes> allShoes = Sdao.loadShoes();

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
        List<Accessories> allAccessories = Adao.loadAccessories();

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
                Cdao.addProduct(newProduct);
                break;

            case "Shoes":
                newProduct = new Shoes(name, purchase, sell, size, storeFinance);
                Sdao.addProduct(newProduct);
                break;

            case "Accessories":
                newProduct = new Accessories(name, purchase, sell, storeFinance);
                Adao.addProduct(newProduct);
                break;
        }

        if (newProduct == null) return;

        refreshAllTabs();

        clearFields();
    }

    public void modifyProduct() {
        String type = TypeCB.getValue();
        Product selectedProduct=Select(type, "Select a product to modify");

        String name = NameTF.getText();
        double purchaseprice = Double.parseDouble(PourchasePriseTF.getText());
        double sellprice = Double.parseDouble(SellPriceTF.getText());

        Integer size = null;
        if (!type.equals("Accessories")) {
            size = Integer.parseInt(SizeTF.getText());
        }

        switch (type) {
            case "Clothes":
                Cdao.modifierProduct(selectedProduct.getId(),name, purchaseprice, sellprice, size);
                break;

            case "Shoes":
                Sdao.modifierProduct(selectedProduct.getId(),name, purchaseprice, sellprice, size);
                break;

            case "Accessories":
                Adao.modifierProduct(selectedProduct.getId(),name, purchaseprice, sellprice, size);
                break;
        }

        refreshAllTabs();
        clearFields();
    }

    public void deleteProduct() {
        String type = TypeCB.getValue();
        Product selectedProduct=Select(type, "Select a product to delete");

        switch (type) {
            case "Clothes":
                Cdao.deleteProduct(selectedProduct.getId());
                break;

            case "Shoes":
                Sdao.deleteProduct(selectedProduct.getId());
                break;

            case "Accessories":
                Adao.deleteProduct(selectedProduct.getId());
                break;
        }


        refreshAllTabs();
        clearFields();
    }

    public void sellProduct() {
        String type = TypeCB.getValue();
        Product selectedProduct=Select(type, "Select a product to Sell");

        if (SellTF.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText(null);
            alert.setContentText("Enter a quantity");
            alert.showAndWait();
        }

        int quantity=Integer.parseInt(SellTF.getText());



        selectedProduct.sell(quantity);

        switch (type) {
            case "Clothes":

                Cdao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;

            case "Shoes":
                Sdao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;

            case "Accessories":
                Adao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;
        }

        refreshAllTabs();
        clearFields();
        displayFinance();

    }

    public void purchaseProduct() {
        String type = TypeCB.getValue();
        Product selectedProduct=Select(type, "Select a product to Purchase");

        if (PurchaseTF.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText(null);
            alert.setContentText("Enter a quantity");
            alert.showAndWait();
        }

        int quantity=Integer.parseInt(PurchaseTF.getText());



        selectedProduct.purchase(quantity);



        switch (type) {
            case "Clothes":
                Cdao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;

            case "Shoes":
                Sdao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;

            case "Accessories":
                Adao.UpdateStock(selectedProduct.getId(), selectedProduct.getStock());
                break;
        }



        refreshAllTabs();
        clearFields();
        displayFinance();

    }

    public void ChangePrices(int choix) {
        ClothesLV.getItems().clear();
        ShoesLV.getItems().clear();
        AccessoriesLV.getItems().clear();

        List<Accessories> allaccessories=Adao.loadAccessories();
        for(Accessories accessories:allaccessories){
            if(choix==1){
                accessories.applyDiscount();
            }
            else{
                accessories.unApplyDiscount();
            }

        }

        AccessoriesLV.getItems().addAll(allaccessories);

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

        List<Shoes> allShoes=Sdao.loadShoes();
        for(Shoes shoes:allShoes){
            if(choix==1){
                shoes.applyDiscount();
            }
            else{
                shoes.unApplyDiscount();
            }
        }

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

        List<Clothes> allClothes=Cdao.loadClothes();

        for(Clothes clothes:allClothes){
            if(choix==1){
                clothes.applyDiscount();
            }
            else{
                clothes.unApplyDiscount();
            }
        }

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
        clearFields();
    }

    public void SortProducts() {
        ClothesLV.getItems().clear();
        ShoesLV.getItems().clear();
        AccessoriesLV.getItems().clear();

        List<Clothes> allClothes = Cdao.loadClothes();
        Collections.sort(allClothes);
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

        List<Shoes> allShoes = Sdao.loadShoes();
        Collections.sort(allShoes);
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

        List<Accessories> allAccessories = Adao.loadAccessories();
        Collections.sort(allAccessories);
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



    public Product Select(String type, String Message){
        Product selectedProduct;


        if (type == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setHeaderText(null);
            alert.setContentText(Message);
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

        return selectedProduct;
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
        SellTF.clear();
        PurchaseTF.clear();
    }



}
