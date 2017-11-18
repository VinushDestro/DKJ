/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.home;

import dkjconstruction.DbConnection;
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        category.getItems().addAll("Tender","Employee","Asset","Equipment","Raw Material");
        
        setTable();
        System.out.println("main");
        doSearch();
    }    
    public static ObservableList<Detail> getTable(String tname,String c1,String c2,String c3,String c4,String c5) throws IOException, ClassNotFoundException, SQLException {
            ObservableList<Detail>  table= FXCollections.observableArrayList();

            DbConnection.openConnection();
            Connection con=DbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select ?,?,?,?,? from ?");
            pst.setString(1,c1);
            pst.setString(2,c2);
            pst.setString(3,c3);
            pst.setString(4,c4);
            pst.setString(5,c5);
            pst.setString(6,tname);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                
                table.add(new Detail(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
                return table;
        }
    
    public void setTable(){
        System.out.println("set");

        category.setOnHidden(e -> {
            
        switch(category.getValue().toString()){
            case "Tender":
                c1.setText("Name");
                c2.setText("Work Type");
                c3.setText("Working Place");
                c4.setText("Company Name");
                c5.setText("Status");
                break;
                
                case "Employee":
                c1.setText("Name");
                c2.setText("Position");
                c3.setText("Address");
                c4.setText("Contact No");
                c5.setText("Availability");
                break;
                
                case "Asset":
                c1.setText("Reg No");
                c2.setText("Name");
                c3.setText("Type");
                c4.setText("Condition");
                c5.setText("Current Value");
                loadTable();
                break;
                
                case "Equipment":
                c1.setText("Name");
                c2.setText("Quantity");
                c3.setText("Price");
                c4.setText(null);
                c5.setText(null);
                break;
                
                case "Raw Material":
                c1.setText("Type");
                c2.setText("Price");
                c3.setText("Quantity");
                c4.setText("Supplier Name");
                c5.setText(null);
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
    private void loadTable(){
                //String n = c1.getText().replaceAll("\\s","");
                //System.out.println(n);
                c1.setCellValueFactory(new PropertyValueFactory<>(c1.getText().replaceAll("\\s","")));
                c2.setCellValueFactory(new PropertyValueFactory<>(c2.getText().replaceAll("\\s","")));
                c3.setCellValueFactory(new PropertyValueFactory<>(c3.getText().replaceAll("\\s","")));
                c4.setCellValueFactory(new PropertyValueFactory<>(c4.getText().replaceAll("\\s","")));
                c5.setCellValueFactory(new PropertyValueFactory<>(c5.getText().replaceAll("\\s","")));
                
                String val1=c1.getText().replaceAll("\\s","");
                String val2=c2.getText().replaceAll("\\s","");
                String val3=c3.getText().replaceAll("\\s","");
                String val4=c4.getText().replaceAll("\\s","");
                String val5=c5.getText().replaceAll("\\s","");
                String cat=category.getValue().toString().replaceAll("\\s","");
                
                try {
                    homeTab.setItems(getTable(cat,val1,val2,val3,val4,val5));
                    
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    private void doSearch() {
        search.setOnKeyReleased(e -> {
            if (search.getText().equals("")) {
                loadTable();
        

            }
            else{
                String val1=c1.getText().replaceAll("\\s","");
                String val2=c2.getText().replaceAll("\\s","");
                String val3=c3.getText().replaceAll("\\s","");
                String val4=c4.getText().replaceAll("\\s","");
                String val5=c5.getText().replaceAll("\\s","");
                Connection con = DbConnection.getConnection();
                PreparedStatement pst;
                try {
                    pst = con.prepareStatement
                                   ("SELECT ?,?,?,?,? FROM ? where ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%' or ? LIKE '%" + search.getText() + "%'");
                    pst.setString(1,val1);
                    pst.setString(2,val2);
                    pst.setString(3,val3);
                    pst.setString(4,val4);
                    pst.setString(5,val5);
                    pst.setString(6,category.getValue().toString());
                    pst.setString(7,val1);
                    pst.setString(8,val2);
                    pst.setString(9,val3);
                    pst.setString(10,val4);
                    pst.setString(11,val5);
                    
                    ResultSet rs = pst.executeQuery();
                    tableS= FXCollections.observableArrayList();
                    while (rs.next()) {
                        
                        tableS.add(new Detail(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5))); 
                        
                    }
                    homeTab.setItems(tableS);
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
