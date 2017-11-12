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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
public class VehicleManagement extends Application {
    
    
    private static Stage primaryStage;
    private static GridPane mainLayout;
    private static Stage stage;
    private static AnchorPane layout;

    public static void main(String[] args) {
        launch(args);
    }

      
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
        rootLoader.setLocation(VehicleManagement.class.getResource("Vehicle.fxml"));
       
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
             String cost=rs.getString("cost");
             String lifeTime=rs.getString("lifeTime");
             String boughtDate =rs.getString("boughtDate");
             String condition=rs.getString("condition");
             String depPercent=rs.getString("depPercent");
             String currentDep=rs.getString("currentDep");
             String totalDep=rs.getString("totalDep");
             String currentValue=rs.getString("currentValue");
             Vehicle.add(new VehicleDetail(regNo,name,type,cost,lifeTime,boughtDate,condition,depPercent,currentDep,totalDep,currentValue));
// creating objects and load it in
        }
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
    public static int addVehicle(String regNo,String name,String type,double cost,Date boughtDate,int lifeTime,String condition,float dep) throws SQLException, ClassNotFoundException{
        DbConnection DbConnection= new DbConnection();
        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        double calcDep=Math.round((dep/100)*cost);
        double totalDeps=lifeTime*calcDep;
        double finalValue=cost-totalDeps;
        
       
//     PreparedStatement pst = con.prepareStatement("SELECT DATEDIFF(CURDATE(),boughtDate) from asset ");
//     System.out.println(pst);
        
        PreparedStatement stmt = con.prepareStatement("insert into asset (`regNo`,`name`,`type`,`cost`,`boughtDate`,`lifeTime`,`condition`,`depPercent`,`currentDep`,`totalDep`,`currentValue`) values (?,?,?,?,?,?,?,?,?,?,?)");
        
        stmt.setString(1,regNo);
        stmt.setString(2,name);
        stmt.setString(3,type);
        stmt.setDouble(4,cost);
        stmt.setDate(5,boughtDate);
        stmt.setInt(6,lifeTime);
        stmt.setString(7,condition);
        stmt.setFloat(8,dep);
        stmt.setDouble(9,calcDep);
        stmt.setDouble(10,totalDeps);
        stmt.setDouble(11,finalValue);
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

        PreparedStatement stmt = con.prepareStatement("update asset set lifeTime=?,totalDep=currentDep*lifeTime,currentValue=cost-totalDep,asset.condition=? where regNo=?");
       
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
         
        PreparedStatement stmt = con.prepareStatement("update asset set lifeTime=?,totalDep=currentDep*lifeTime,currentValue=cost-totalDep where regNo=?");
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
             String cost=rs.getString("cost");
             String lifeTime=rs.getString("lifeTime");
             String boughtDate =rs.getString("boughtDate");
             String condition=rs.getString("condition");
             String depPercent=rs.getString("depPercent");
             String currentDep=rs.getString("currentDep");
             String totalDep=rs.getString("totalDep");
             String currentValue=rs.getString("currentValue");
             Vehicle.add(new VehicleDetail(regNo,name,type,cost,lifeTime,boughtDate,condition,depPercent,currentDep,totalDep,currentValue));
// creating objects and load it in
        }
       // DbConnection.closeConnection();
        return Vehicle;
    }
      
public static int updateRepair(String regNo) throws SQLException, ClassNotFoundException {

        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset set availability='repair' where regNo=?");
         stmt.setString(1,regNo);
        
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
    
    public  static ObservableList<VehicleDetail> getRepairVehicle() throws IOException, ClassNotFoundException, SQLException {
        ObservableList<VehicleDetail>  Vehicle= FXCollections.observableArrayList();

        DbConnection.openConnection();
        Connection con=DbConnection.getConnection();
        Statement stmt =con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from asset where availability = 'repair'");
        
        while(rs.next()) {

             String regNo =rs.getString("regNo");
             String name=rs.getString("name");
             String type =rs.getString("type");
             String cost=rs.getString("cost");
             String lifeTime=rs.getString("lifeTime");
             String boughtDate =rs.getString("boughtDate");
             String condition=rs.getString("condition");
             String depPercent=rs.getString("depPercent");
             String currentDep=rs.getString("currentDep");
             String totalDep=rs.getString("totalDep");
             String currentValue=rs.getString("currentValue");
             Vehicle.add(new VehicleDetail(regNo,name,type,cost,lifeTime,boughtDate,condition,depPercent,currentDep,totalDep,currentValue));
// creating objects and load it in
        }
     //   DbConnection.closeConnection();
        return Vehicle;
    }



    public static int updateAvailable(String regNo) throws SQLException, ClassNotFoundException {

        int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

        PreparedStatement stmt = con.prepareStatement("update asset set availability='available' where regNo=?");
         stmt.setString(1,regNo);
        
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

    
    public static boolean doCheckAvailble(String regNo) throws ClassNotFoundException, SQLException{
      int result = -1;
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        DbConnection DbConnection= new DbConnection();

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();

          boolean bool =false;
          
          try {

            PreparedStatement stmt = con.prepareStatement("Select availability from asset where regNo =?");
            stmt.setString(1,regNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                if (rs.getString("availability").equals("assigned")) {
                     
                   // rs.getString("availability").equals("assigned");
                    bool = true;     
                }
                
            }

        } catch (Exception e) {
            System.err.println("error " + e);

        }
        DbConnection.closeConnection();
        return bool;
    
    }


    

}

