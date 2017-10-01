/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.vehicle;

import dkjconstruction.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author VINUSH
 */
public class DKJASSETSMANAGEMENT extends Application {
    
    
    private static Stage primaryStage;
    private static GridPane mainLayout;
    private static Stage stage;
    private static AnchorPane layout;

//    public static void main(String[] args) {
//        launch(args);
//    }

    
//    @Override
//    public void start(Stage primaryStage) throws IOException  {
//        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("DKJ Constuction Management System");
//try{
//        FXMLLoader root = new FXMLLoader();
//        FXMLLoader rootLoader = new FXMLLoader();
//        rootLoader.setLocation(DKJASSETSMANAGEMENT.class.getResource("FXML1.fxml"));
//        //rootLoader.setLocation(DKJASSETSMANAGEMENT.class.getResource("FXML1.fxml"));
//        Parent content = root.load();
//       // layout = root.load();
//        Scene scene = new Scene(layout);
//        stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("DKJ");
//        stage.show();
//        System.out.println("start method");
//    }
//    
//    catch (Exception e){
//    System.out.print(e);
//}
//}
    //----------------------------------
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
        rootLoader.setLocation(DKJASSETSMANAGEMENT.class.getResource("FXML1.fxml"));
       
      GridPane root = new GridPane();
        root = rootLoader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("DKJ Asset Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
          public static ObservableList<VehicleDetail> getVehicle() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<VehicleDetail>  Vehicle= FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from asset");

        while(rs.next()) {

             String regNo =rs.getString("regNo");
             String name=rs.getString("name");
             String type =rs.getString("type");
             double cost=rs.getDouble("cost");
             int lifeTime=rs.getInt("lifeTime");
             String boughtDate =rs.getString("boughtDate");
             String condition=rs.getString("condition");
             Vehicle.add(new VehicleDetail(regNo,name,type,cost,lifeTime,boughtDate,condition));
// creating objects and load it in
        }
        DbConnection.closeConnection();
        return Vehicle;
    
    
    
    
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        Button btn = new Button();
//        btn.setText("Say 'DKJ Asset Management'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("DKJ Asset Management");
//            }
//        });
//        
//        FXMLLoader rootLoader = new FXMLLoader();
//        rootLoader.setLocation(DKJASSETSMANAGEMENT.class.getResource("FXML1.fxml"));
//       
//        GridPane root = new GridPane();
//        root = rootLoader.load();
//        
//        Scene scene = new Scene(root);
//        
//        primaryStage.setTitle("DKJ Asset Management");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//   }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
          }
  // add vehicle  
    public static int addVehicle(String regNo,String name,String type,double cost,String boughtDate,int lifeTime,String condition) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("insert into asset (`regNo`,`name`,`type`,`cost`,`boughtDate`,`lifeTime`,`condition`) values (?,?,?,?,?,?,?)");
        
        stmt.setString(1,regNo);
        stmt.setString(2,name);
        stmt.setString(3,type);
        stmt.setDouble(4,cost);
        stmt.setString(5,boughtDate);
        stmt.setInt(6,lifeTime);
        stmt.setString(7,condition);
        
        int result = stmt.executeUpdate();
        DbConnection.closeConnection();

        return result;
    }
    
    //update vehicle
    public static int updateVehicle(String regNo,int lifeTime,String condition) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset set lifeTime=?,asset.condition=? where regNo=?");
       
        stmt.setInt(1,lifeTime);
        stmt.setString(2,condition);
        stmt.setString(3,regNo);
        
        
        try{
            result = stmt.executeUpdate();
            
            //System.out.println(stmt);
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
    
    public static int updateVehicle(String regNo,String condition) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset set asset.condition=? where regNo= ?");
        
        stmt.setString(1,condition);
        stmt.setString(2,regNo);
        
        
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
    
     
    
    
    public static int updateVehicle(String regNo,int lifeTime) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset set lifeTime=? where regNo=?");
        stmt.setInt(1,lifeTime);
        stmt.setString(2,regNo);
        
        
        try{
           result = stmt.executeUpdate();
         //  System.out.println(stmt);
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
    
    public static int updateVehicle(String regNo,String condition,int lifeTime) throws SQLException, ClassNotFoundException {

        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset asset.condition=?, lifeTime=? where regNo=?");
        stmt.setString(1,condition);
        stmt.setInt(2,lifeTime);
        stmt.setString(3,regNo);
        
        try{
            result = stmt.executeUpdate();
        }
        catch (SQLException e){
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Entry\n"+e.getMessage());
            alert.show();

        }
        DbConnection.closeConnection();
        return result;
    }
    
    //delete vehcle
    public static int deleteVehicle(String regNo) throws SQLException, ClassNotFoundException {
        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("delete from asset where regNo=?");
        stmt.setString(1,regNo);
        
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
  
   
       public  static ObservableList<VehicleDetail> getAvailableVehicle() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<VehicleDetail>  Vehicle= FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from asset where availability = 'available'");

        while(rs.next()) {

             String regNo =rs.getString("regNo");
             String name=rs.getString("name");
             String type =rs.getString("type");
             double cost=rs.getDouble("cost");
             int lifeTime=rs.getInt("lifeTime");
             String boughtDate =rs.getString("boughtDate");
             String condition=rs.getString("condition");
             Vehicle.add(new VehicleDetail(regNo,name,type,cost,lifeTime,boughtDate,condition));
// creating objects and load it in
        }
        DbConnection.closeConnection();
        return Vehicle;
    }
      
  
      
      
      


      

      
      
      
      
      
      
      
      
      
      }
      
      

