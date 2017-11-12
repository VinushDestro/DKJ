/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

import dkjconstruction.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author VINUSH
 */
public class Equipment {
    
    
    //-------------------------------------------------------------------------------------------------------
    // Equipments
    
//    public static void main(String[] args) {
//        launch(args);
//    }
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
//       FXMLLoader rootLoader = new FXMLLoader();
//        rootLoader.setLocation(VehicleManagement.class.getResource("Equip.fxml"));
//       
//      GridPane root = new GridPane();
//        root = rootLoader.load();
//        
//        Scene scene = new Scene(root);
//        
//        primaryStage.setTitle("DKJ Asset Management");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
    
     public static  ObservableList<EquipmentDetail> getEquipment() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<EquipmentDetail> equipment = FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from equipment");

        while(rs.next()) {

            String name=rs.getString("name");
            String cost=rs.getString("cost");
            String count=rs.getString("Count");
            double totalCost=rs.getDouble("totalCost");
            equipment.add(new EquipmentDetail(name,count,cost,totalCost));
// creating objects and load it in
        }
       
        return equipment;
    }
      
    
    
    
    
    
    
    
    
    public static int addEquipment(String name,int count,double cost) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        double totalCost=count*cost;
        PreparedStatement stmt = con.prepareStatement("insert into equipment (`name`,`count`,`cost`,`totalCost`) values (?,?,?,?)");
        
        
        
       
        stmt.setString(1,name);
        stmt.setInt(2,count);
        stmt.setDouble(3,cost);
        stmt.setDouble(4,totalCost);
                
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();
  
        return result;
    }
    
    
     public static int updateEquipment(String name,int count) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection = new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update equipment set count=count + ?, totalCost=cost*count where name=?");
        stmt.setInt(1,count);
        stmt.setString(2,name);
        
        
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
    
     
     
      public static int deleteEquipment(String name,int count) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update equipment set count=count-? ,totalCost=cost*count where name=?");
        stmt.setInt(1,count);
        stmt.setString(2,name);
        
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
      
      
      
       public static int deleteEquipment(String name) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from equipment where name=?");
        
        stmt.setString(1,name);
        
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

        
            String name=rs.getString("name");
           String count=rs.getString("Count");
            String cost=rs.getString("cost");
            double totalCost=rs.getDouble("totalCost");
           AvailableEquipmentD.add(new EquipmentDetail(name,count,cost,totalCost));
           
// creating objects and load it in
        }

        return AvailableEquipmentD;
    }
      
      
        
        
        
        
} 
        
        
      

      
      
      
      
      
      
      
      
      
      
      

