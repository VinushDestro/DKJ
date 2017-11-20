/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author User
 */
public class DKJConstruction extends Application {
    private static Stage primaryStage;
    private static GridPane mainLayout;
    private static Stage stage;
    private static AnchorPane layout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        //this.primaryStage.setTitle("DKJ Constuction Management System");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader root = new FXMLLoader();

        root.setLocation(DKJConstruction.class.getResource("main/Login.fxml"));
        layout = root.load();
        Scene scene = new Scene(layout);
        stage = new Stage();
        stage.setScene(scene);
        //stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
        System.out.println("start method");
        
    }
    
    public static void showMainPage() throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("main/Main.fxml"));
        mainLayout = loader.load();
        
                final Delta dragDelta = new Delta();
        mainLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
            dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
          }
        });
        mainLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent mouseEvent) {
            primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
            primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
          }
        });

        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("homepage method");
//        
//        loader = new FXMLLoader();
//        loader.setLocation(DKJConstruction.class.getResource("home/Home.fxml"));
//        GridPane Pane = loader.load();
//        mainLayout.add(Pane, 1, 1);
//        
                
    }
    
    public static void showHome() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("home/Home.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }

    public static void showTransport() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("transport/Transport.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showAdmin() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("settings/Admin.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showJobAllocation() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/JobAllocation.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showAccounts() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("accounts/AccountsFXMLDocument.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showCAccounts() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("accounts/ten.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    
    public static void showPendingEmployee() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/pendingEmployee.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showPendingAsset() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/pendingAsset.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showPendingEquipment() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/pendingEquipment.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showPendingMaterial() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/pendingMaterial.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showEmployee() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/employee.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
     public static void showJobAsset() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/assetAllocation.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
     
      public static void showJobMaterial() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/materialAllocation.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
      
       public static void showEquipment() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("joballocation/equipmentAllocation.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
       
    
    public static void showUtilities() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("utilities/UtilitiesFXMLDocument.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }

    public static void showHr() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("hr/HRmanagement.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showPayroll() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("payroll/Payroll.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showTender() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/Tender.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
     public static void showChat() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("main/chat.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showTender_requirement() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/Requirement.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showTender_TenderHome() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/TenderHome.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showTender_viewTender() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/ViewTender.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showTender_closeTender() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/closeTender.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showTender_summery() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("tender/summery.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showAsset() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("vehicle/Vehicle.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showEquip() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("equip/Equip.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    public static void showMaterial() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("rawmaterial/Rawmaterial.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showSupplier() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("supplier/Supplier.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showErrorPage() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("main/Error.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    
    public static void showChangePw() throws IOException {
        mainLayout.getChildren().clear();
        showMainPage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DKJConstruction.class.getResource("settings/ChangePw.fxml"));
        GridPane Pane = loader.load();
        mainLayout.add(Pane, 1, 1);
    }
    

    public static void logout() {
        primaryStage.hide();
        stage.show();
    }
}
