/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.utilities;

import dkjconstruction.DbConnection;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author h3
 */
public class UtilitiesFXMLDocumentController implements Initializable {

    @FXML
    private JFXDatePicker udate;
    @FXML
    private JFXTextField uamount;
    @FXML
    private JFXDatePicker upddate;
    @FXML
    private JFXTextField uid;
    @FXML
    private JFXComboBox utype;
    @FXML
    private JFXComboBox umethod;

    @FXML
    private TableView<UtilityDetail> utiltab;

    @FXML
    private TableColumn<?, ?> typecol;
    @FXML
    private TableColumn<?, ?> idcol;
    @FXML
    private TableColumn<?, ?> amountcol;
    @FXML
    private TableColumn<?, ?> bdatecol;
    @FXML
    private TableColumn<?, ?> pdatecol;
    @FXML
    private TableColumn<?, ?> methodcol;

    private ObservableList<UtilityDetail> data;
    @FXML
    private TextField tsearch;

    
    
    

        
    // private DbConnection dc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utype.getItems().addAll("Electricity", "Water", "Phone", "Internet");
        umethod.getItems().addAll("Cash", "Card");
        try {
            // TODO
            // dc = new DbConnection();
            DbConnection.openConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilitiesFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UtilitiesFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection con = DbConnection.getConnection();

        data = FXCollections.observableArrayList();
        tableld();
        ldtblutil();
        RowclickEvent();
        searchUtil();
        
    }

    
    
    
    private void tableld() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            typecol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            idcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            amountcol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            bdatecol.setCellValueFactory(new PropertyValueFactory<>("BillDate"));
            pdatecol.setCellValueFactory(new PropertyValueFactory<>("PaidDate"));
            methodcol.setCellValueFactory(new PropertyValueFactory<>("PaymentMethod"));
        } catch (Exception e) {
            System.err.println("tbl error" + e);

        }

    }

    private void ldtblutil() {
        data.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select * from utilities");
            while (rs.next()) {

                data.add(new UtilityDetail(rs.getString(2), rs.getString(1), "" + rs.getDouble(3), "" + rs.getDate(4), "" + rs.getDate(5), rs.getString(6)));
                //data.add(new UtilityDetail(Type, ID, Amount, BillDate, PaidDate, PaymentMethod));

            }

        } catch (Exception e) {
            System.err.println("err ld tableeeeee" + e);
        }
        utiltab.setItems(data);

    }

    private void RowclickEvent() {
        utiltab.setOnMouseClicked((e) -> {
            UtilityDetail t1 = utiltab.getItems().get(utiltab.getSelectionModel().getSelectedIndex());

            uid.setText(t1.getID());
            utype.setValue(t1.getType());
            uamount.setText(t1.getAmount());
             udate.setValue(LocalDate.parse(t1.getBillDate()));
            upddate.setValue(LocalDate.parse(t1.getPaidDate()));
            umethod.setValue(t1.getPaymentMethod());

        });

    }
    
    
    
     @FXML
    private void ReportClicked() throws SQLException, ClassNotFoundException {
        
    }

    @FXML
    private void AddUtilities(ActionEvent event) throws SQLException {

        if (validatef()) {
            try {
                String addUtype = utype.getValue().toString();
                String addUid = uid.getText();
                //String addUdate = udate.getValue().toString();
                Double addUamount = Double.parseDouble(uamount.getText()); //er
                // LocalDate addUpdate = upddate.getValue();
                String addUmethod = umethod.getValue().toString();

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement("insert into utilities (billNo,billType,paidAmount,paymentMethod,billDate,paidDate) values (?,?,?,?,?,?)");

                stmt.setString(1, addUid);
                stmt.setString(2, addUtype);
                stmt.setDouble(3, addUamount);
                //  stmt.setString(4,addUdate);
                //stmt.setDate(5,addUpdate);
                stmt.setString(4, addUmethod);
                stmt.setDate(5, java.sql.Date.valueOf(udate.getValue()));
                stmt.setDate(6, java.sql.Date.valueOf(upddate.getValue()));

                stmt.executeUpdate();
                System.out.println("sucess");
            } catch (SQLIntegrityConstraintViolationException r) {

                alertboxWarn("Add Utilities", "This Utility record had been already has been taken");
            } catch (Exception e) {
                System.out.println(" Error");
                System.err.println(e);

            }

        }//if-else
        tableld();
        ldtblutil();
    }

    @FXML
    private void updateClicked(ActionEvent event) throws SQLException{
       if (validatef()) {

            if (alertboxConfirm("Update utility details !", "Do you really want to update")) {

                try {
                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();
                 PreparedStatement stmt = con.prepareStatement  ("UPDATE utilities set billType= '" + utype.getValue()+ "',paidAmount= '" + Double.parseDouble(uamount.getText()) + "',billDate = '" + udate.getValue() + "',paidDate = '" + upddate.getValue() + "',paymentMethod= '" + umethod.getValue()+ "' where billNo = '" + uid.getText() + "' ");
                   
                 stmt.executeUpdate();
                    
                    
                    System.out.println("sucessfully updated");

                } catch (Exception e) {
                     // handle your exception
                        

                    System.out.println("Update  error " + e);

                }

               
            tableld();
                ldtblutil();
   }
            }
        }
        
         
    

    @FXML
    private void deleteClicked(ActionEvent event) {

        if (alertboxConfirm("Delete tender details !", "Do you really want to Delete ?")) {

            try {
                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement("DELETE from utilities where billNo = '" + uid.getText() + "' ");
                stmt.executeUpdate();

            } catch (Exception e) {
                System.out.println("del  error");

            }

            tableld();
            ldtblutil();

        }

    }

    private void searchUtil() {
        
        tsearch.setOnKeyReleased(e -> {
        
        if (tsearch.getText().equals("")) {
            
            tableld();
        ldtblutil();
            } else {
                data.clear();
                try {

                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(
                            "SELECT * FROM utilities where billNo LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM utilities where billType LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM utilities where paymentMethod LIKE '%"+tsearch.getText() +"%'"
                    );

                    while (rs.next()) {

                        data.add(new UtilityDetail(rs.getString(2), rs.getString(1), "" + rs.getDouble(3), "" + rs.getDate(4), "" + rs.getDate(5), rs.getString(6)));

                    }

                } catch (Exception ex) {
                    System.err.println("Error searching" + ex);

                }

                utiltab.setItems(data);

            }
            
            
        });
    
    
        
        
    }

    private boolean validatef() {
        if (utype.getValue() == null || uid.getText().isEmpty() || umethod.getValue() == null || udate.getValue() == null ||umethod.getValue() == null ||uamount.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("All fields shoud be filled");
            a.show();
            return false;
        }else if (!(Double.parseDouble(uamount.getText()) >= 0  ) )
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("All");
            a.show();
        return false;
        
        
        }
        return true;
    }

    public void alerboxInfo(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox warning
    public void alertboxWarn(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

    //alertbox Confirmation
    public boolean alertboxConfirm(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirmation");
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    @FXML
    private void p(MouseEvent event) {
        
        
    }



}
