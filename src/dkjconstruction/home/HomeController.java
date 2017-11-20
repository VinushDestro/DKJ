/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.home;

import dkjconstruction.DbConnection;
import dkjconstruction.equip.Equipment;
import dkjconstruction.rawmaterial.RawMaterial;
import dkjconstruction.vehicle.VehicleManagement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HomeController implements Initializable {

    @FXML
    private ComboBox category;
    @FXML
    private TextField search;
    @FXML
    private TableView homeTab;
    @FXML
    private TableColumn c1;
    @FXML
    private TableColumn c2;
    @FXML
    private TableColumn c3;
    @FXML
    private TableColumn c4;
    @FXML
    private TableColumn c5;

    private ObservableList<Detail> tableS; 
        //private ObservableList<Detail> sea; 


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        category.getItems().addAll("Tender","Employee","Asset","Equipment","Raw Material");
        category.getSelectionModel().select("Tender");
        c1.setText("Tender Name");
                c2.setText("Work Type");
                c3.setText("Working Place");
                c4.setText("Company Name");
                c5.setText("Status");
        try {
            loadTenTable();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTable();
        
        doSearch();
    }    

    public void setTable(){

        category.setOnHidden(e -> {
            search.clear();
        switch(category.getValue().toString()){
            case "Tender":
                c1.setText("Tender Name");
                c2.setText("Work Type");
                c3.setText("Working Place");
                c4.setText("Company Name");
                c5.setText("Status");
        {
            try {
                loadTenTable();
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
                
                case "Employee":
                c1.setText("Name");
                c2.setText("Address");
                c3.setText("Contact No");
                c4.setText("Position");
                c5.setText("Emp Type");
                {
                    try {
                        loadEmpTable();
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                break;
                
                case "Asset":
                c1.setText("Reg No");
                c2.setText("Name");
                c3.setText("Type");
                c4.setText("Condition");
                c5.setText("Current Value");
                loadAssTable();
                break;
                
                case "Equipment":
                c1.setText("Name");
                c2.setText("Quantity");
                c3.setText("Price");
                c4.setText(null);
                c5.setText(null);
                loadEquipTable();
                break;
                
                case "Raw Material":
                c1.setText("Type");
                c2.setText("Quantity");
                c3.setText("Unit Price");
                c4.setText(null);
                c5.setText(null);
                loadMatTable();
                break;
                
                default : 
                c1.setText(null);
                c2.setText(null);
                c3.setText(null);
                c4.setText(null);
                c5.setText(null);
        }
        });
        
    }
    private void loadAssTable() {
        c1.setCellValueFactory(new PropertyValueFactory<>("RegNo"));
        c2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        c3.setCellValueFactory(new PropertyValueFactory<>("Type"));
        c4.setCellValueFactory(new PropertyValueFactory<>("Condition"));
        c5.setCellValueFactory(new PropertyValueFactory<>("CurrentValue"));

        try {
            homeTab.setItems(VehicleManagement.getVehicle());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
    
    private void loadMatTable() {
        c1.setCellValueFactory(new PropertyValueFactory<>("Type"));
        c2.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        c3.setCellValueFactory(new PropertyValueFactory<>("Price"));
    
        try {
            homeTab.setItems(RawMaterial.getRawmaterial());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void loadEquipTable() {
        c1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        c2.setCellValueFactory(new PropertyValueFactory<>("Count"));
        c3.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        
        try {
            homeTab.setItems(Equipment.getEquipment());
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadTenTable() throws IOException {
        c1.setCellValueFactory(new PropertyValueFactory<>("col1"));
        c2.setCellValueFactory(new PropertyValueFactory<>("col2"));
        c3.setCellValueFactory(new PropertyValueFactory<>("col3"));
        c4.setCellValueFactory(new PropertyValueFactory<>("col4"));
        c5.setCellValueFactory(new PropertyValueFactory<>("col5"));
        
        try {
            homeTab.setItems(getTender());
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("c1 : "+c1.getText());
        }
    }
    
    public void loadEmpTable() throws IOException {
        c1.setCellValueFactory(new PropertyValueFactory<>("col1"));
        c2.setCellValueFactory(new PropertyValueFactory<>("col2"));
        c3.setCellValueFactory(new PropertyValueFactory<>("col3"));
        c4.setCellValueFactory(new PropertyValueFactory<>("col4"));
        c5.setCellValueFactory(new PropertyValueFactory<>("col5"));
        
        
        try {
            homeTab.setItems(getEmp());
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ObservableList<Detail> getTender() throws IOException, ClassNotFoundException, SQLException {

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select * from tender");
        tableS= FXCollections.observableArrayList();

        while (rs.next()) {

            String n = rs.getString("tendername");
            String w = rs.getString("worktype");
            String wp = rs.getString("workingplace");
            String c = rs.getString("companyname");
            String s = rs.getString("status");
           
            System.out.println("name :"+n);
            
            tableS.add(new Detail(n,w,wp,c,s));
        }
        return tableS;
    }
    
    public ObservableList<Detail> getEmp() throws IOException, ClassNotFoundException, SQLException {

        DbConnection.openConnection();
        Connection con = DbConnection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("select * from employee");
        tableS= FXCollections.observableArrayList();

        while (rs.next()) {

            String n = rs.getString("name");
            String w = rs.getString("address");
            String wp = rs.getString("contactno");
            String c = rs.getString("position");
            String s = rs.getString("emptype");
            
            tableS.add(new Detail(n,w,wp,c,s));
        }
        return tableS;
    }
    
    private void doSearch() {
        search.setOnKeyReleased(e -> {
            if (search.getText().equals("")) {
                switch(category.getValue().toString()){
                    case "Asset": loadAssTable();break;
                    case "Equipment": loadEquipTable();break;
                    case "Raw Material": loadMatTable();break;
                    case "Tender": {
                        try {
                            loadTenTable();
                        } catch (IOException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    case "Employee": {
                        try {
                            loadEmpTable();
                        } catch (IOException ex) {
                            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
            }
            else{
                switch(category.getValue().toString()){
                    
                case "Employee": 
                try {

                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                    //("SELECT * FROM employee where name LIKE '%" + search.getText() + "%' or address LIKE '%" + search.getText() + "%' or contactno LIKE '%" + search.getText() + "%'");
                 ("SELECT * FROM employee where name LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM employee where address LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM employee where contactno LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM employee where position LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM employee where emptype LIKE '%" + search.getText() + "%' ");
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String v1=rs.getString("name");
                        String v2=rs.getString("address");
                        String v3=rs.getString("contactno");
                        String v4=rs.getString("position");
                        String v5=rs.getString("emptype");
                        System.out.println("------------------------");

                        System.out.println(v1);
                        System.out.println(v2);
                        System.out.println(v3);
                        System.out.println(v4);
                        System.out.println(v5);
                        
                        tableS.add(new Detail(v1,v2,v3,v4,v5)); 
                        }
                    homeTab.setItems(tableS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                break;
                case "Tender": 
                try {

                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                            ("SELECT * FROM tender where tendername LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM tender where worktype LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM tender where workingplace LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM tender where companyname LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM tender where status LIKE '%" + search.getText() + "%' ");
                    
                    //("SELECT * FROM tender where tendername LIKE '%" + search.getText() + /*"%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() +  */"%'");
                 
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String v1=rs.getString("tendername");
                        String v2=rs.getString("worktype");
                        String v3=rs.getString("workingplace");
                        String v4=rs.getString("companyname");
                        String v5=rs.getString("status");
                        System.out.println("------------------------");

                        System.out.println(v1);
                        System.out.println(v2);
                        System.out.println(v3);
                        System.out.println(v4);
                        System.out.println(v5);
                        
                        tableS.add(new Detail(v1,v2,v3,v4,v5)); 
                        }
                    homeTab.setItems(tableS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                break;
                case "Asset": 
                try {

                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                    //("SELECT * FROM asset where name LIKE '%" + search.getText() + /*"%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() +  */"%'");
                 ("SELECT * FROM asset where name LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM asset where regno LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM asset where type LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM asset where asset.condition LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM asset where currentvalue LIKE '%" + search.getText() + "%' ");
                    
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String v1=rs.getString(1);
                        String v2=rs.getString(2);
                        String v3=rs.getString(3);
                        String v4=rs.getString(7);
                        String v5=rs.getString(12);
                        System.out.println("------------------------");

                        System.out.println(v1);
                        System.out.println(v2);
                        System.out.println(v3);
                        System.out.println(v4);
                        System.out.println(v5);
                        
                        tableS.add(new Detail(v1,v2,v3,v4,v5)); 
                        }
                    homeTab.setItems(tableS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                break;
                case "Equipment": 
                try {

                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                    //("SELECT * FROM equipment where name LIKE '%" + search.getText() + /*"%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() +  */"%'");
                 ("SELECT * FROM Equipment where name LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM Equipment where count LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM Equipment where cost LIKE '%" + search.getText() + "%' ");
                    
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String v1=rs.getString("name");
                        String v2=rs.getString("count");
                        String v3=rs.getString("cost");
                        
                        System.out.println("------------------------");

                        System.out.println(v1);
                        System.out.println(v2);
                        System.out.println(v3);
                        
                        
                        tableS.add(new Detail(v1,v2,v3)); 
                        }
                    homeTab.setItems(tableS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                break;
                case "Raw Material": 
                try {

                    Connection con = DbConnection.getConnection();
                    
                    PreparedStatement pst = con.prepareStatement
                    //("SELECT * FROM rawmaterial where type LIKE '%" + search.getText() + /*"%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() +  */"%'");
                 ("SELECT * FROM RawMaterial where type LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM RawMaterial where quantity LIKE '%" + search.getText() + "%'"
                            + "UNION SELECT * FROM RawMaterial where price LIKE '%" + search.getText() + "%' ");
                    
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        String v1=rs.getString("type");
                        String v2=rs.getString("quantity");
                        String v3=rs.getString("price");
                        
                        System.out.println("------------------------");

                        System.out.println(v1);
                        System.out.println(v2);
                        System.out.println(v3);
                        
                        
                        tableS.add(new Detail(v1,v2,v3)); 
                        }
                    homeTab.setItems(tableS);

                } catch (SQLException ex) {
                    System.err.println("Error loading table data " + ex);

                }
                break;
                default : ;
                }
            }
        });
    }
}





// ("SELECT ?,?,?,?,? FROM ? where ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%'");

  //                  pst.setString(1, c1.getText().replaceAll("\\s", ""));
//                    pst.setString(2, c2.getText().replaceAll("\\s", ""));
//                    pst.setString(3, c3.getText().replaceAll("\\s", ""));
//                    pst.setString(4, c4.getText().replaceAll("\\s", ""));
//                    pst.setString(5, c5.getText().replaceAll("\\s", ""));
                    //pst.setString(6, category.getValue().toString().replaceAll("\\s", ""));
                    //pst.setString(7, c1.getText().replaceAll("\\s", ""));
//                    pst.setString(8, c2.getText());
//                    pst.setString(9, c3.getText());
                    