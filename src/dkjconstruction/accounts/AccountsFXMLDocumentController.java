
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.accounts;

import dkjconstruction.DbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jxl.format.Colour;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author h3
 */
public class AccountsFXMLDocumentController implements Initializable {

    @FXML
    private Tab hracc;
    @FXML
    private TableView<Hrdet> hrtab;
    @FXML
    private TableColumn<?, ?> hrdatecol;
    @FXML
    private TableColumn<?, ?> hrtypecol;
    @FXML
    private TableColumn<?, ?> hramountcol;

    @FXML
    private JFXButton matreport;
    @FXML
    private TableView<matdet> mattab;
    @FXML
    private TableColumn<?, ?> matdatecol;
    @FXML
    private TableColumn<?, ?> mattypecol;
    @FXML
    private TableColumn<?, ?> matamountcol;

    @FXML
    private TableView<twdet> twtab;
    @FXML
    private TableColumn<?, ?> twdatecol;
    @FXML
    private TableColumn<?, ?> twtypecol;
    @FXML
    private TableColumn<?, ?> twamountcol;
    @FXML
    private TableColumn<?, ?> twtenidcol;

    @FXML
    private TableView<tendet> tentab;
    @FXML
    private TableColumn<?, ?> tencol;
    @FXML
    private JFXTextField twtxt;

    @FXML
    private TableView<trans> transtab;

    @FXML
    private TableColumn<?, ?> tdate;

    @FXML
    private TableColumn<?, ?> ttype;

    @FXML
    private TableColumn<?, ?> tamount;

    @FXML
    private GridPane pane;

    @FXML
    private Label hrtot;

    @FXML
    private Label mattot;

    @FXML
    private Label trantot;

    @FXML
    private Label tottot;

    private ObservableList<Hrdet> hrdata;
    private ObservableList<matdet> matdata;

    private ObservableList<twdet> twdata;
    private ObservableList<tendet> tendata;
    private ObservableList<trans> transdata;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        hrdata = FXCollections.observableArrayList();
        matdata = FXCollections.observableArrayList();

        twdata = FXCollections.observableArrayList();
        tendata = FXCollections.observableArrayList();
        transdata = FXCollections.observableArrayList();

        /* setTablehr();
        loadtablehr();*/
        setTablemat();
        loadtablemat();

        setTabletw();
        loadtabletw();

        setTableten();
        loadtableten();

        setTabletrans();
        loadtabletrans();
  
        setTablehr();
        loadtablehr();
       
        searchtw();
        

    
    }
//********************************************************************************************************************************************
//******************* Table ***************************************************************************************************************  

//assigned hr -tenderwise
    private void setTablehr() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            hrdatecol.setCellValueFactory(new PropertyValueFactory<>("hdate"));
            hrtypecol.setCellValueFactory(new PropertyValueFactory<>("htype"));
            hramountcol.setCellValueFactory(new PropertyValueFactory<>("hamount"));
            System.out.println("Succuss set ut");
        } catch (Exception e) {
            System.err.println("hr tbl set error" + e);

        }

    }

    private void loadtablehr() {
        hrdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select p.empId,p.date,p.finalSalary\n"
                    + "from payroll p ,emptender e\n"
                    + "where p.empId = e.empId"
            );
            while (rs.next()) {

                hrdata.add(new Hrdet("" + rs.getDate(2), rs.getString(1), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err hr table load" + e);
        }
        hrtab.setItems(hrdata);

    }

    //material table-tenderwise
    private void setTablemat() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            matdatecol.setCellValueFactory(new PropertyValueFactory<>("mdate"));
            mattypecol.setCellValueFactory(new PropertyValueFactory<>("mtype"));
            matamountcol.setCellValueFactory(new PropertyValueFactory<>("mamount"));
            System.out.println("loading hr");
        } catch (Exception e) {
            System.err.println("mat tbl set error" + e);

        }

    }

    private void loadtablemat() {
        matdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select m.date, m.materialType,(r.price*m.assignCount)\n"
                    + "from rawmaterial r , materialtender m\n"
                    + "where r.type = m.materialType ");
            while (rs.next()) {

                matdata.add(new matdet("" + rs.getDate(1), rs.getString(2), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err mat table load" + e);
        }
        mattab.setItems(matdata);

    }

    //transport table -tenderwise
    private void setTabletrans() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            tdate.setCellValueFactory(new PropertyValueFactory<>("tdate"));
            ttype.setCellValueFactory(new PropertyValueFactory<>("ttype"));
            tamount.setCellValueFactory(new PropertyValueFactory<>("tamount"));

        } catch (Exception e) {
            System.err.println("t tbl set error" + e);

        }

    }

    private void loadtabletrans() {
        transdata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select date,tripid,cost from transport");
            while (rs.next()) {

                transdata.add(new trans("" + rs.getDate(1), rs.getString(2), "" + rs.getDouble(3)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err mat table load" + e);
        }
        transtab.setItems(transdata);

    }

    //tenderwise accounts table
    private void setTabletw() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            twdatecol.setCellValueFactory(new PropertyValueFactory<>("twdate"));
            twtypecol.setCellValueFactory(new PropertyValueFactory<>("twtype"));
            twamountcol.setCellValueFactory(new PropertyValueFactory<>("twamount"));
            twtenidcol.setCellValueFactory(new PropertyValueFactory<>("twid"));

        } catch (Exception e) {
            System.err.println("tw tbl set error" + e);

        }

    }

    private void loadtabletw() {
        twdata.clear();

        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select m.date,m.tenderId, m.materialType,(r.price*m.assignCount)\n"
                    + "                    from rawmaterial r , materialtender m\n"
                    + "                    where r.type = m.materialType \n"
                    + "UNION \n"
                    + "select date,tenderId,destination,cost from transport\n"
                    + "UNION \n"
                    + "select p.date,e.tenderId,p.empId,p.finalSalary\n"
                    + "  from payroll p ,emptender e\n"
                    + "  where p.empId = e.empId"
            );
            while (rs.next()) {

                //  twdata.add(new twdet(twdate, twtype, twamount, twid));
                twdata.add(new twdet("" + rs.getDate(1), rs.getString(3), "" + rs.getDouble(4), "" + rs.getString(2)));

            }
            System.out.println("helooooooooooooooooooooooooooooooooooooooooooooooo");
        } catch (Exception e) {
            System.err.println("err tw table load" + e);
        }
        twtab.setItems(twdata);

    }

//tender table -tenderwise
    private void setTableten() {

        try {

            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            tencol.setCellValueFactory(new PropertyValueFactory<>("tenid"));

        } catch (Exception e) {
            System.err.println("ten tbl set error" + e);

        }

    }

    private void loadtableten() {
        tendata.clear();
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select tenderId from tender");
            while (rs.next()) {

                tendata.add(new tendet(rs.getString(1)));
                //utdata.add(new utdet(uudate, uutype, uuamount));

            }

        } catch (Exception e) {
            System.err.println("err ten table load" + e);
        }
        tentab.setItems(tendata);

    }

 //*******************************************************************************************************************************************
 //**********************search tenderwise*********************************************************************************************** 
 
  //search tenderwise
    private void searchtw() {
        //   twtxt.setOnKeyReleased(e -> {
        tentab.setOnMouseClicked((e) -> {
            tendet t1 = tentab.getItems().get(tentab.getSelectionModel().getSelectedIndex());

            twtxt.setText(t1.getTenid());

            if (twtxt.getText().equals("")) {
                setTabletw();
                loadtabletw();
            } else {
                twdata.clear();
                try {

                    DbConnection.openConnection();
                    Connection con = DbConnection.getConnection();

                    ResultSet rs = con.createStatement().executeQuery("select m.date,m.tenderId, m.materialType,(r.price*m.assignCount)\n"
                            + "from rawmaterial r , materialtender m\n"
                            + "where r.type = m.materialType   and m.`tenderId` like '%" + twtxt.getText() + "%' union\n"
                            + "select t.date,t.tenderId,t.destination,t.cost\n"
                            + "from transport t\n"
                            + "where t.tenderId like  '%" + twtxt.getText() + "%' union\n"
                            + "select p.date,e.tenderId,p.empId,p.finalSalary\n"
                            + "from payroll p ,emptender e\n"
                            + "where p.empId = e.empId and e.tenderId like '%" + twtxt.getText() + "%' ");
                    System.out.println("hiiiiiiiiiiiiiiiiii");
                    //"LIKE '%"+tid.getText() +"%'"
                    while (rs.next()) {

                        twdata.add(new twdet("" + rs.getDate(1), rs.getString(3), "" + rs.getDouble(4), "" + rs.getString(2)));

                    }

                } catch (Exception ex) {
                    System.err.println("Error searching" + ex);

                }

                twtab.setItems(twdata);
                qq();
            }

        });

    }

    
//************************************************************************************************************************************
//****************alert****************************************************************************************************************


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

    public void alertboxWarn(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setWidth(500);
        alert.setHeight(300);
        alert.setContentText(message);
        alert.showAndWait();

    }

//    private void total() {
//        if (twtxt.getText().equals("")) {
//
//        } else {
//
//            try {
//                double matsum = 0;
//
//                DbConnection.openConnection();
//                Connection con = DbConnection.getConnection();
//
//                ResultSet rs3 = con.createStatement().executeQuery("select SUM(r.price*m.assignCount)\n"
//                        + "from rawmaterial r , materialtender m\n"
//                        + "where r.type = m.materialType and tenderId like '%" + twtxt.getText() + "%'\n"
//                        + "");
//
//                while (rs3.next()) {
//
//                    double c = rs3.getDouble(1);
//                    matsum = matsum + c;
//                }
//
//                // l.setText(sum);
//                //System.out.println("Sum of column = " + transsum);
//                mattot.setText(Double.toString(matsum));
//
//            } catch (Exception e) {
//                System.err.println("err sum" + e);
//            }
//        }
//    }

//*****************************************************************************************************************************************************
//**************************** go to company accounts*******************************************************************************************    
    @FXML
    private void ca(ActionEvent event) throws IOException {
       
        dkjconstruction.DKJConstruction.showCAccounts();

 }
//****************************************************************************************************************************************************
//***************************** sum ****************************************************************************************************************** 
    
    private void qq() {
        if (twtxt.getText().equals("")) {
            alertboxWarn("Warning", "Tender ID mustbe Selected");

        } else {
            try {
                double hrsum = 0;
                double matsum = 0;
                double transsum = 0;
                double totsum = 0;

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();

                ResultSet rs3 = con.createStatement().executeQuery("select SUM(r.price*m.assignCount)\n"
                        + "from rawmaterial r , materialtender m\n"
                        + "where r.type = m.materialType and tenderId like '%" + twtxt.getText() + "%'\n"
                        + "");

                while (rs3.next()) {

                    double c = rs3.getDouble(1);
                    matsum = matsum + c;
                }

                ResultSet rs2 = con.createStatement().executeQuery("select sum(cost)\n"
                        + "from transport t \n"
                        + "where t.`tenderId` like '%" + twtxt.getText() + "%'\n"
                        + "  ");

                while (rs2.next()) {

                    double d = rs2.getDouble(1);
                    transsum = transsum + d;
                }

                ResultSet rs9 = con.createStatement().executeQuery("select (p.`finalSalary`) from payroll p ,emptender e where p.`empId` = e.`empId` AND  e.`tenderId` like '%" + twtxt.getText() + "%'");
                         
                       

                while (rs9.next()) {

                    double d = rs9.getDouble(1);
                    hrsum = hrsum + d;
                }

                totsum = transsum + matsum + hrsum;
                tottot.setTextFill(Color.RED);
                
                mattot.setText(Double.toString(matsum)+" Rs");
                trantot.setText(Double.toString(transsum)+" Rs");
                tottot.setText(Double.toString(totsum )+" Rs");
                hrtot.setText(Double.toString(hrsum)+" Rs");
            } catch (Exception e) {
                System.err.println("error calculating" + e);
            }
        }
    }

//***********************************************************************************************************************************************
//*********************** spread sheet ***********************************************************************************************************

// assigned Raw material spread sheet   
    @FXML
    private void matex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/RawMaterial.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Transection");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select m.date, m.materialType,(r.price*m.assignCount)\n"
                        + "from rawmaterial r , materialtender m\n"
                        + "where r.type = m.materialType");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("date"));
                    row.createCell((short) 1).setCellValue(rs.getString("materialType"));
                    row.createCell((short) 2).setCellValue(rs.getString("(r.price*m.assignCount)"));

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

    //transport spread sheet
        @FXML
    private void transex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/Transport.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Transection");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select date,tripid,cost from transport");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("date"));
                    row.createCell((short) 1).setCellValue(rs.getString("tripid"));
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
    
    @FXML
    private void hrex(ActionEvent event) throws ClassNotFoundException {
        if (alertboxConfirm("Export to Excel Spreadsheet", "Do you really want to Export ?")) {
            try {

                DbConnection.openConnection();
                Connection con = DbConnection.getConnection();
                //Variable counter for keeping track of number of rows inserted.  
                int counter = 1;
                FileOutputStream fileOut = null;
                //sql - query to be executed; filename - Name of the excel file  

                String filename = "C:/Users/Mahesh/Desktop/EXDKJ/AssignedHR.xls";
                //Creation of New Work Book in Excel and sheet.  
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                //Creating Headings in Excel sheet.  
                HSSFRow rowhead = sheet.createRow((short) 0);

                rowhead.createCell((short) 0).setCellValue("Date");//Row Name5 
                rowhead.createCell((short) 1).setCellValue("Transection");//Row Name2  
                rowhead.createCell((short) 2).setCellValue("Amount");//Row Name3 

                ResultSet rs = con.createStatement().executeQuery("select p.empId,p.date,p.finalSalary\n"
                    + "from payroll p ,emptender e\n"
                    + "where p.empId = e.empId");

                while (rs.next()) {
                    //Insertion in corresponding row  
                    HSSFRow row = sheet.createRow((int) counter);
                    /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */

                    row.createCell((short) 0).setCellValue(rs.getString("p.date"));
                    row.createCell((short) 1).setCellValue(rs.getString("p.empId"));
                    row.createCell((short) 2).setCellValue(rs.getString("p.finalSalary"));

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
                System.out.println(e);
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }

    }

   //******************************************************************************************************************************************************
   //*****************************refresh***************************************************************************************************** 
    @FXML
    private void refresh_tender(ActionEvent event) {
        try {
            hrtab.refresh();
            mattab.refresh();
            twtab.refresh();
            tentab.refresh();
            transtab.refresh();
            System.out.println("refreshed tender");
        } catch (Exception e) {
            System.err.println("err refresh tender" + e);
        }
        ;

    }
//*******************************************************************************************************************************************
//******************** iReport ****************************************************************************************************************** 

//assigned hr
    @FXML
    private void hrrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\hr.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

    //assigned raw material
    @FXML
    private void assrawrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\material.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

// transport
    @FXML
    private void transrep(ActionEvent event) {
        if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                String report = "C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\transport.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(report);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                JasperViewer.viewReport(jp, false);
            } catch (Exception e) {
                System.out.println("report" + e);
            }
        }
    }

    //tw parameterpassed
    @FXML
    private void twrep(ActionEvent event) {
                if (alertboxConfirm("iReport", "Do you really want to view Ireport ?")) {
            Connection con = DbConnection.getConnection();
            try {
                 Map<String, Object> map = new HashMap<>();
        
//        map.put("MONTH","'" + abc.getValue() + "'");
//        map.put("YEAR","'" + frmd.getText() + "'");
             map.put("ID","'%" + twtxt.getText() + "%'");
    
//'%" + twtxt.getText() + "%'
        JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\accounts\\tw.jrxml");
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

} //L
