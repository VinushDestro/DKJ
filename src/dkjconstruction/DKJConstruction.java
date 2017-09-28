/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class DKJConstruction extends Application {
    private static Stage primaryStage;
    private static GridPane mainLayout;
    private static Stage stage;
    private static AnchorPane layout;
        
    /*@FXML
    private static AnchorPane mainPage;*/

    //private static BorderPane page;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DKJ Constuction Management System");

        FXMLLoader root = new FXMLLoader();

        root.setLocation(DKJConstruction.class.getResource("Home/Login.fxml"));
        layout = root.load();
        Scene scene = new Scene(layout);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        System.out.println("start method");
    }
    
    public static void showHomePage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("Home/Home.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("homepage method");
    }

    public static void showTransport() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("Transport/Transport.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showAdmin() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("Admin/Admin.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showJobAllocation() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/JobAllocation.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showAccounts() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("accounts/AccountsFXMLDocument.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showUtilities() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("utilities/UtilitiesFXMLDocument.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }

    public static void showHr() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("hr/HRmanagement.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showPayroll() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("payroll/Payroll.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showTender() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tendermanagement/Tender.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showAsset() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("asset/FXML1.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showEquip() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("asset/Equip.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showMaterial() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("RawMaterial/Rawmaterial.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showSupplier() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("Supplier/Supplier.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showErrorPage() throws IOException {
        mainLayout.getChildren().clear();
        showHomePage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("Home/Error.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }

    public static void logout() {
        primaryStage.hide();
        stage.show();
    }
}
