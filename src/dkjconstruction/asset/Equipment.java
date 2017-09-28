/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.asset;

import java.sql.Connection;
import dkjconstruction.DbConnection;
//import asset.management.AvailableEquipmentDetails.AvailableEquipmentDetail;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author VINUSH
 */
public class Equipment extends Application {
    
    
    //-------------------------------------------------------------------------------------------------------
    // Equipments
    
//    public static void main(String[] args) {
//        launch(args);
//    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
       FXMLLoader rootLoader = new FXMLLoader();
        rootLoader.setLocation(DKJASSETSMANAGEMENT.class.getResource("Equip.fxml"));
       
      GridPane root = new GridPane();
        root = rootLoader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("DKJ Asset Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
     public static  ObservableList<EquipmentDetail> getEquipment() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<EquipmentDetail> equipment = FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from equipment");

        while(rs.next()) {

            String name=rs.getString("name");
            String equipID = rs.getString("equipID");
            double cost=rs.getDouble("cost");
            int count=rs.getInt("Count");
            equipment.add(new EquipmentDetail(equipID,name,count,cost));
// creating objects and load it in
        }
        DbConnection.closeConnection();
        return equipment;
    }
      
    
    
    
    
    
    
    
    
    public static int addEquipment(String equipID,String name,int count,double cost) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into equipment (`equipID`,`name`,`count`,`cost`) values (?,?,?,?)");
        
        stmt.setString(1,equipID);
        stmt.setString(2,name);
        stmt.setInt(3,count);
        stmt.setDouble(4,cost);
                
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();

        return result;
    }
    
    
     public static int updateEquipment(String equipID,int count) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection = new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update equipment set count=? where equipID=?");
        stmt.setInt(1,count);
        stmt.setString(2,equipID);
        
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
    
     
    
      public static int deleteEquipment(String equipID,int count) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        dkjconstruction.asset.DbConnection DbConnection= new dkjconstruction.asset.DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update equipment set count=count-? where equipID=?");
        stmt.setInt(1,count);
        stmt.setString(2,equipID);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
      
      
      
       public static int deleteEquipment(String equipID) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
         dkjconstruction.asset.DbConnection DbConnection= new dkjconstruction.asset.DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from equipment where equipID=?");
        
        stmt.setString(1,equipID);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (Exception e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such record found\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
  //--------------------------------------------------------------------------------------

      
      
 
    
      
 
   
 //--------------------------------------------------------------------------------------------------
     // Availableequipment class to connec db
      
      

      
        public static ObservableList<EquipmentDetail> getAvailableEquipment() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<EquipmentDetail> AvailableEquipmentD= FXCollections.observableArrayList();
//check here
        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from equipment where availability ='available'");

        while(rs.next()) {

             String equipID =rs.getString("equipID");
            String name=rs.getString("name");
           int count=rs.getInt("Count");
            double cost=rs.getDouble("cost");
            
           AvailableEquipmentD.add(new EquipmentDetail(equipID,name,count,cost));
           
// creating objects and load it in
        }
        DbConnection.closeConnection();
        return AvailableEquipmentD;
    }
      
      
        
        
        
        
} 
        
        
      

      
      
      
      
      
      
      
      
      
      
      

