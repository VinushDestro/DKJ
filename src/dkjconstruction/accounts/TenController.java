/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.accounts;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DKJConstruction;
import static dkjconstruction.DKJConstruction.showMainPage;
import dkjconstruction.DbConnection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author h3
 */
public class TenController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private TableView<Assdet> asstab;
    @FXML
    private TableColumn<?, ?> assdatecol;
    @FXML
    private TableColumn<?, ?> asstypecol;
    @FXML
    private TableColumn<?, ?> assamountcol;

    @FXML
    private JFXButton autilreport;
    @FXML
    private TableView<utdet> utiltabb;
    @FXML
    private TableColumn<?, ?> autildatecol;
    @FXML
    private TableColumn<?, ?> autiltypecol;
    @FXML
    private TableColumn<?, ?> autilamount;

    @FXML
    private TableView<eqdet> eqtab;

    @FXML
    private TableColumn<?, ?> eqdatecol;

    @FXML
    private TableColumn<?, ?> eqnamecol;

    @FXML
    private TableColumn<?, ?> eqamount;

    @FXML
    private TableView<matbuy> mbtab;

    @FXML
    private TableColumn<?, ?> mbdate;

    @FXML
    private TableColumn<?, ?> mbtype;

    @FXML
    private TableColumn<?, ?> mbamount;

    // private Label assum;
    //  private Label eqsum;
    private ObservableList<chrdet> chrdata;
    private ObservableList<Assdet> assdata;
    private ObservableList<utdet> utdata;
    private ObservableList<eqdet> eqdata;
    private ObservableList<matbuy> matbuy;
    private ObservableList<dwdet> dwdata;

    @FXML
    private Tab hracc1;
    @FXML
    private TableView<dwdet> dwtab;
    @FXML
    private TableColumn<?, ?> dwdate;
    @FXML
    private TableColumn<?, ?> dwtype;
    @FXML
    private TableColumn<?, ?> dwamount;
    @FXML
    private JFXTextField frmd;
    @FXML
    private Label utdt;
    @FXML
    private Label asdt;
    @FXML
    private Label eqdt;
    @FXML
    private Label matdt;
    @FXML
    private Label tdt;
    private ComboBox<?> ttdate;
    @FXML
    private JFXComboBox abc;
    @FXML
    private TableView<chrdet> chrtab;
    @FXML
    private TableColumn<?, ?> chrdatecol;
    @FXML
    private TableColumn<?, ?> chrtypecol;
    @FXML
    private TableColumn<?, ?> chramountcol;
    @FXML
    private Label hrdt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        chrdata = FXCollections.observableArrayList();
        utdata = FXCollections.observableArrayList();
        assdata = FXCollections.observableArrayList();
        eqdata = FXCollections.observableArrayList();
        matbuy = FXCollections.observableArrayList();
        dwdata = FXCollections.observableArrayList();

        setTableut();
        loadtableut();

        setTableass();
        loadtableass();

        setTableeq();
        loadtableeq();

        setTablemab();
        loadtablemab();

        setTabledw();
        loadtabledw();

        setTablehr();
        loadtablehr();

//        assum();
//        eqsum();
        abc.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

    }

//***********************************************************************************************************************************************
//***************************************** table ************************************************************************************************
//chr
    //hr
    private void setTablehr() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            chrdatecol.setCellValueFactory(new PropertyValueFactory<>("chdate"));
            chrtypecol.setCellValueFactory(new PropertyValueFactory<>("chtype"));
            chramountcol.setCellValueFactory(new PropertyValueFactory<>("chamount"));
            System.out.println("Succuss set ut");
        } catch (Exception e) {
            System.err.println("chr tbl set error" + e);

        }

    }
//chr
    private void loadtablehr() {
        chrdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select e.empId,p.finalSalary,p.`date` from employee e , payroll p where e.empId= p.empId AND e.availability =\"available\""
            );
            while (rs.next()) {

                chrdata.add(new chrdet("" + rs.getDate(3), rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));
//chrdata.add(new chrdet(chdate, chtype, chamount));
            }

        } catch (Exception e) {
            System.err.println("err chr table load" + e);
        }
        chrtab.setItems(chrdata);

    }

//utility table
    private void setTableut() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            autildatecol.setCellValueFactory(new PropertyValueFactory<>("uudate"));
            autiltypecol.setCellValueFactory(new PropertyValueFactory<>("uutype"));
            autilamount.setCellValueFactory(new PropertyValueFactory<>("uuamount"));
            System.out.println("Succuss load table");
        } catch (Exception e) {
            System.err.println("ut tbl set error" + e);

        }

    }

    private void loadtableut() {
        utdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select billType , paidAmount , paidDate from utilities ");
            while (rs.next()) {

                utdata.add(new utdet("" + rs.getDate(3), "" + rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }
            System.out.println("Succuss load ut");

        } catch (Exception e) {
            System.err.println("err util table load" + e);
        }
        utiltabb.setItems(utdata);

    }

    //vehicleaccounts table - companywise
    private void setTableass() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            assdatecol.setCellValueFactory(new PropertyValueFactory<>("adate"));
            asstypecol.setCellValueFactory(new PropertyValueFactory<>("atype"));
            assamountcol.setCellValueFactory(new PropertyValueFactory<>("aamount"));

        } catch (Exception e) {
            System.err.println("ass tbl set error" + e);

        }

    }

    private void loadtableass() {
        assdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select regNo , cost , boughtDate from vehicleaccounts");
            while (rs.next()) {

                assdata.add(new Assdet("" + rs.getDate(3), rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err ass table load" + e);
        }
        asstab.setItems(assdata);

    }

    //equipment table 
    private void setTableeq() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            eqdatecol.setCellValueFactory(new PropertyValueFactory<>("eqdate"));
            eqnamecol.setCellValueFactory(new PropertyValueFactory<>("eqtype"));
            eqamount.setCellValueFactory(new PropertyValueFactory<>("eqamount"));

        } catch (Exception e) {
            System.err.println("eq tbl set error" + e);

        }

    }

    private void loadtableeq() {
        eqdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select DATE_FORMAT(date,'%Y-%m-%d'),name,(count*cost) from eqaccounts");
            while (rs.next()) {

                eqdata.add(new eqdet(rs.getString(1), rs.getString(2), "" + rs.getDouble(3)));
                // eqdata.add(new eqdet(eqdate, eqtype, eqamount)));

            }

        } catch (Exception e) {
            System.err.println("err eq table load" + e);
        }
        eqtab.setItems(eqdata);

    }

    //rawmaterial
    private void setTablemab() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            mbdate.setCellValueFactory(new PropertyValueFactory<>("mbdate"));
            mbtype.setCellValueFactory(new PropertyValueFactory<>("mbtype"));
            mbamount.setCellValueFactory(new PropertyValueFactory<>("mbamount"));

        } catch (Exception e) {
            System.err.println("matbuy tbl set error" + e);

        }
    }

    private void loadtablemab() {
        matbuy.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select date,type,(price*quantity) from rawaccounts");
            while (rs.next()) {

                matbuy.add(new matbuy("" + rs.getDate(1), rs.getString(2), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err buy table load" + e);
        }
        mbtab.setItems(matbuy);

    }

    //datewise table
    private void setTabledw() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            dwdate.setCellValueFactory(new PropertyValueFactory<>("dwdate"));
            dwtype.setCellValueFactory(new PropertyValueFactory<>("dwtype"));
            dwamount.setCellValueFactory(new PropertyValueFactory<>("dwamount"));
            System.out.println("Succuss load table");
        } catch (Exception e) {
            System.err.println("ut tbl set error" + e);

        }

    }

    private void loadtabledw() {
        dwdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select u.billType , u.paidAmount , u.paidDate \n"
                    + "from utilities u\n"
                    + "\n"
                    + "UNION\n"
                    + "select a.regNo , a.cost , a.boughtDate \n"
                    + "from vehicleaccounts a\n"
                    + "\n"
                    + "UNION\n"
                    + "select e.name ,(e.count*e.cost),DATE_FORMAT(e.date,'%Y-%m-%d') \n"
                    + "from eqaccounts e\n"
                    + " \n"
                    + "UNION \n"
                    + "select r.type,(r.price*r.quantity),r.date \n"
                    + "from rawaccounts r UNION select e.empId,p.finalSalary,p.`date` from employee e , payroll p where e.empId= p.empId AND e.availability =\"available\""); //'%" + tod.getText() + "%'

            while (rs.next()) {

                dwdata.add(new dwdet("" + rs.getDate(3), "" + rs.getString(1), "" + rs.getDouble(2)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }
            System.out.println("Succuss load dw");

        } catch (Exception e) {
            System.err.println("err dw table load" + e);
        }
        dwtab.setItems(dwdata);

    }

//*************************************************************************************************************************************************    
//***************** find datewise *******************************************************************************************************************
    @FXML
    private void find(ActionEvent event) {
        if (frmd.getText().equals("") || abc.getValue() == null) {
            setTabledw();
            loadtabledw();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("All fields shoud be filled");
            a.show();
        } else if (!frmd.getText().matches("[0-9]{4}")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid input for YEAR");
            a.show();
        } else {
            try {
                dwdata.clear();
                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();

                /* ResultSet rs = con.createStatement().executeQuery(" select a.regNo , a.cost , a.boughtDate\n"
                    + " from vehicleaccounts a\n"
                    + "WHERE MONTH(a.boughtDate) LIKE '%" + tod.getText() + "%' AND YEAR(a.boughtDate) LIKE '%" + frmd.getText() + "%' ");
                 */
                ResultSet rs = con.createStatement().executeQuery("select u.billType , u.paidAmount , u.paidDate from utilities u where  MONTH(u.paidDate)= '" + abc.getValue() + "' AND YEAR(u.paidDate) = '" + frmd.getText() + "' UNION\n"
                        + "select a.regNo , a.cost , a.boughtDate from vehicleaccounts a where MONTH(a.boughtDate)= '" + abc.getValue() + "' AND YEAR(a.boughtDate) = '" + frmd.getText() + "' UNION\n"
                        + " select e.name ,(e.cost*e.count),DATE_FORMAT(e.date,'%Y-%m-%d') from eqaccounts e WHERE  MONTH(DATE_FORMAT(e.date,'%Y-%m-%d'))= '" + abc.getValue() + "' AND YEAR(DATE_FORMAT(e.date,'%Y-%m-%d')) = '" + frmd.getText() + "'   UNION \n"
                        + "select type,(r.price*r.quantity),r.date from rawaccounts r where MONTH(r.date)= '" + abc.getValue() + "' AND YEAR(r.date) = '" + frmd.getText() + "'UNION\n"
                        + "select e.empId,p.finalSalary,p.date from employee e , payroll p where e.empId= p.empId AND e.availability =\"available\" AND MONTH(p.date) ='" + abc.getValue() + "' AND YEAR(p.date) = '" + frmd.getText() + "' ");

                //"LIKE '%"+tid.getText() +"%'"
                while (rs.next()) {
                    dwdata.add(new dwdet("" + rs.getDate(3), "" + rs.getString(1), "" + rs.getDouble(2)));
                }
                System.err.println("done");
            } catch (Exception ex) {
                System.err.println("Error searching" + ex);

            }

            dwtab.setItems(dwdata);
            try {
                ww();
            } catch (Exception e) {
                System.out.println("aef" + e);
            }

        }
    }

    //************************************************************************************************************************************************   
//***************************************** sum ***************************************************************************************************    
    //vehicleaccounts sum
//    private void assum() {
//        try {
//            double sum = 0;
//            DbConnection.openConnection();
//            Connection con = DbConnection.getConnection();
//
//            ResultSet rs = con.createStatement().executeQuery("SELECT SUM(cost)\n"
//                    + "from vehicleaccounts");
//
//            while (rs.next()) {
//
//                double c = rs.getDouble(1);
//                sum = sum + c;
//
//            }
//            // l.setText(sum);
//            System.out.println("Sum of column = " + sum);
//            assum.setText(Double.toString(sum));
//
//        } catch (Exception e) {
//            System.err.println("err sum" + e);
//        }
//
//    }
//    //equipment sum
//    private void eqsum() {
//        try {
//            double sum = 0;
//            DbConnection.openConnection();
//            Connection con = DbConnection.getConnection();
//
//            ResultSet rs = con.createStatement().executeQuery("select SUM(count*cost) \n"
//                    + "from eqaccounts\n"
//                    + "");
//
//            while (rs.next()) {
//
//                double c = rs.getDouble(1);
//                sum = sum + c;
//
//            }
//            // l.setText(sum);
//            System.out.println("Sum of column = " + sum);
//            eqsum.setText(Double.toString(sum));
//
//        } catch (Exception e) {
//            System.err.println("err sum" + e);
//        }
//
//    }
    private void ww() {

        try {
            double dutsum = 0;
            double dassum = 0;
            double deqsum = 0;
            double dmatsum = 0;
            double dtotsum = 0;
            double dchrsum =0;

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs3 = con.createStatement().executeQuery("select sum(u.paidAmount) from utilities u where  MONTH(u.paidDate)= '" + abc.getValue() + "' AND YEAR(u.paidDate) = '" + frmd.getText() + "'");

            while (rs3.next()) {

                double c = rs3.getDouble(1);
                dutsum = dutsum + c;
            }
             ResultSet rs8 = con.createStatement().executeQuery("select sum(p.finalSalary) from employee e , payroll p where e.empId= p.empId AND e.availability =\"available\" AND  MONTH(p.date)= '" + abc.getValue() + "' AND YEAR(p.date) = '" + frmd.getText() + "'");

            while (rs8.next()) {

                double c = rs8.getDouble(1);
                dchrsum = dchrsum + c;
            }

            ResultSet rs2 = con.createStatement().executeQuery("select sum(a.cost) from vehicleaccounts a where MONTH(a.boughtDate)= '" + abc.getValue() + "' AND YEAR(a.boughtDate) = '" + frmd.getText() + "'");

            while (rs2.next()) {

                double d = rs2.getDouble(1);
                dassum = dassum + d;
            }

            ResultSet rs4 = con.createStatement().executeQuery("select sum(e.count*e.cost) from eqaccounts e WHERE  MONTH(DATE_FORMAT(e.date,'%y-%m-%d'))= '" + abc.getValue() + "' AND YEAR(DATE_FORMAT(e.date,'%y-%m-%d')) = '" + frmd.getText() + "'");

            while (rs4.next()) {

                double e = rs4.getDouble(1);
                deqsum = deqsum + e;
            }

            ResultSet rs5 = con.createStatement().executeQuery("select sum(r.price*r.quantity) from rawaccounts r where MONTH(r.date)= '" + abc.getValue() + "' AND YEAR(r.date) = '" + frmd.getText() + "'");

            while (rs5.next()) {

                double f = rs5.getDouble(1);
                dmatsum = dmatsum + f;
            }

            dtotsum = dutsum + dassum + deqsum + dmatsum +dchrsum;

            utdt.setText(Double.toString(dutsum)+" Rs");
            asdt.setText(Double.toString(dassum)+" Rs");
            eqdt.setText(Double.toString(deqsum)+" Rs");
            matdt.setText(Double.toString(dmatsum)+" Rs");
            tdt.setText(Double.toString(dtotsum)+" Rs");
            hrdt.setText(Double.toString(dchrsum)+" Rs");
        } catch (Exception e) {
            System.err.println("error cal" + e);
        }

    }

    //********************************************************************************************************************************   
//*************************************** SPREAD SHEETS ********************************************************************************************
//utilities spreadsheet
    @FXML
    private void utex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/Utilities.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("PaidDate");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("BillType");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("PaidAmount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select paidDate,billType,paidAmount from utilities");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("paidDate"));
                    row.createCell((short) 1).setCellValue(rs.getString("billType"));
                    row.createCell((short) 2).setCellValue(rs.getString("paidAmount"));

                    counter++;
                    try {
                        //For performing write to Excel file  
                        fileOut = new FileOutputStream(filename);
                        hwb.write(fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Close all the parameters once writing to excel is compelte.  
                fileOut.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //vehicleaccounts spreasheet
    @FXML
    private void asex(ActionEvent event) throws ClassNotFoundException {

        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/Assets.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("BoughtDate");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("RegNo");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select boughtDate,regNo,cost from vehicleaccounts");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("boughtDate"));
                    row.createCell((short) 1).setCellValue(rs.getString("regNo"));
                    row.createCell((short) 2).setCellValue(rs.getString("cost"));

                    counter++;
                    try {
                        //For performing write to Excel file  
                        fileOut = new FileOutputStream(filename);
                        hwb.write(fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Close all the parameters once writing to excel is compelte.  
                fileOut.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
//equipment
    @FXML
    private void eqex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/Equipment.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Type");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select DATE_FORMAT(date,'%y-%m-%d'),name,(count*cost) from eqaccounts");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("DATE_FORMAT(date,'%y-%m-%d')"));
                    row.createCell((short) 1).setCellValue(rs.getString("name"));
                    row.createCell((short) 2).setCellValue(rs.getString("(count*cost)"));

                    counter++;
                    try {
                        //For performing write to Excel file  
                        fileOut = new FileOutputStream(filename);
                        hwb.write(fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Close all the parameters once writing to excel is compelte.  
                fileOut.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  
    //chr
        @FXML
    private void chrex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/CompanyHR.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Type");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select e.empId,p.finalSalary,p.date from employee e , payroll p where e.empId= p.empId AND e.availability =\"available\"");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("date"));
                    row.createCell((short) 1).setCellValue(rs.getString("empId"));
                    row.createCell((short) 2).setCellValue(rs.getString("finalSalary"));

                    counter++;
                    try {
                        //For performing write to Excel file  
                        fileOut = new FileOutputStream(filename);
                        hwb.write(fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Close all the parameters once writing to excel is compelte.  
                fileOut.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //companyRawMat
       @FXML
    private void rawmatex(ActionEvent event) throws ClassNotFoundException {
                if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/companyRawMAt.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Type");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select date,type,(price*quantity) from rawaccounts");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("date"));
                    row.createCell((short) 1).setCellValue(rs.getString("type"));
                    row.createCell((short) 2).setCellValue(rs.getString("(price*quantity)"));

                    counter++;
                    try {
                        //For performing write to Excel file  
                        fileOut = new FileOutputStream(filename);
                        hwb.write(fileOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Close all the parameters once writing to excel is compelte.  
                fileOut.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//***************************************************************************************************************************************************
//************************ alertbox *****************************************************************************************************************  
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
//********************************************************************************************************************************************************
//*********************** scene change ********************************************************************************************************************   

    @FXML
    private void ta(ActionEvent event) throws IOException {

        dkjconstruction.DKJConstruction.showAccounts();

    }

//**************************************************************************************************************************************************
//*************** refresh ******************************************************************************************************************************
    @FXML
    private void refresh_company(ActionEvent event) {
        try {
            utiltabb.refresh();
            asstab.refresh();
            mbtab.refresh();
            dwtab.refresh();
            eqtab.refresh();
            System.out.println("refreshed");
        } catch (Exception e) {
            System.err.println("error refreshing " + e);
        }

    }

//***********************************************************************************************************************************************************    
//***************** ireport **********************************************************************************************************************************    
    @FXML
    private void utilrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\util.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

    @FXML
    private void assrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\assrep.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

    @FXML
    private void equiprep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\equip.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

    @FXML
    private void matrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\mat.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }
    
    
    @FXML
    private void chrrep(ActionEvent event) {
      if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\chr.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

 

//***************************** END *********************************************************************************************************************




    @FXML
    private void dwex(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                 Map<String, Object> map = new HashMap<>();
        
//        map.put("MONTH","'" + abc.getValue() + "'");
//        map.put("YEAR","'" + frmd.getText() + "'");
   map.put("MONTH","11");
        map.put("YEAR","2017");

        JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\report1.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(report, map, con);
        JasperViewer.viewReport(jp, false);
//           } catch (JRException ex) {
//        Logger.getLogger(TenController.class.getName()).log(Level.SEVERE, null, ex);
//    } 
            }catch (Exception ex) {
      //  Logger.getLogger(ReportTest.class.getName()).log(Level.SEVERE, null, ex);
    }
        }
    }
    

}
