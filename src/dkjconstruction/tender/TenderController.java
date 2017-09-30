/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.*;
import static com.sun.javafx.tk.Toolkit.getToolkit;
import dkjconstruction.DbConnection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/*
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;*/
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author KishBelic
 */
public class TenderController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    private ObservableList<TenderDetails> data;

    @FXML
    private Label cssLabel;
    @FXML
    private AnchorPane centerpane;

    @FXML
    public JFXTextField tenderId, tenderName, companyName, period, bidValidity, tsearch;
    @FXML
    private JFXTextField estimatedCost;

    @FXML
    private JFXComboBox category, workType, place, grade;

    @FXML
    private TableView<TenderDetails> ttable;

    @FXML
    private TableColumn<?, ?> column_tid;
    @FXML
    private TableColumn<?, ?> column_tname;
    @FXML
    private TableColumn<?, ?> column_tgrade;
    @FXML
    private TableColumn<?, ?> column_tcategory;
    @FXML
    private TableColumn<?, ?> column_tworktype;
    @FXML
    private TableColumn<?, ?> column_tdate;
    @FXML
    private TableColumn<?, ?> column_tplace;
    @FXML
    private TableColumn<?, ?> column_tclient;

    @FXML
    private TableColumn<?, ?> column_tperiod;
    @FXML
    private TableColumn<?, ?> column_bidvalidity;
    @FXML
    private TableColumn<?, ?> column_tcost;

    @FXML
    private DatePicker tdate;

    @FXML
    JFXButton esti_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        esti_btn.setVisible(false);
        //   category_opt.getItems().removeAll(category.getItems());
        category.getItems().addAll("Asphalt", "Gravel", "Concrete");
        //   category_opt.getSelectionModel().select("Option B");

        workType.getItems().addAll("Construct", "Re-fill", "Cleaning", "Painting");

        place.getItems().addAll("Batticaloa", "Trincomalee", "Kandy");

        grade.getItems().addAll("C6", "C7", "C7");

        data = FXCollections.observableArrayList();
        assignTableValues();
        loadDBtoTable();
        RowclickEvent();
        searchTender();

    }

    @FXML
    private void addClicked(ActionEvent event) throws SQLException {

        if (validatefields()) {

            try {
                String tId = tenderId.getText();
                String tName = tenderName.getText();
                String tgrade = grade.getValue().toString();
                String tcategory = category.getValue().toString();
                String twtype = workType.getValue().toString();
                String tplace = place.getValue().toString();
                String tcompName = companyName.getText();
                // LocalDate date = 
                int tperiod = Integer.parseInt(period.getText());
                int tbValidity = Integer.parseInt(bidValidity.getText());
                double estimated_cost = Double.parseDouble(estimatedCost.getText());

                DbConnection.openConnection();
                con = DbConnection.getConnection();
                pst = con.prepareStatement("insert into tender (tenderId,tenderName,grade,category,workType,workingPlace,companyName,period,bidValidity,date,estimatedCost,status) values (?,?,?,?,?,?,?,?,?,?,?,'Pending')");
                //PreparedStatement pst = con.prepareStatement("insert into tender (tenderId,tenderName,grade,category,workType,workingPlace,companyName,contactNo,period,bidValidity) values ('asdf1','asdasd','cd','asdasd','asdasd','asdasd','asda',1232131,12,12)");

                pst.setString(1, tId);
                pst.setString(2, tName);
                pst.setString(3, tgrade);
                pst.setString(4, tcategory);
                pst.setString(5, twtype);
                pst.setString(6, tplace);
                pst.setString(7, tcompName);
                pst.setInt(8, tperiod);
                pst.setInt(9, tbValidity);
                pst.setDate(10, java.sql.Date.valueOf(tdate.getValue()));
                //    pst.setString(10, date);
                pst.setDouble(11, estimated_cost);

                pst.executeUpdate();

                alerboxInfo("Add Tender", "Value added successfully");

                //  DbConnection.closeConnection();
            } catch (SQLIntegrityConstraintViolationException r) {

                alertboxWarn("Add Tender", "This Tender Id already has been taken");
            } catch (Exception e) {
                System.out.println(" Error");
                System.err.println(e);

            }

        }//if-else

        assignTableValues();
        loadDBtoTable();

    }

    //assign Tenderdetails to table
    private void assignTableValues() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            column_tid.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            column_tname.setCellValueFactory(new PropertyValueFactory<>("TenderName"));
            column_tgrade.setCellValueFactory(new PropertyValueFactory<>("Grade"));
            column_tcategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
            column_tworktype.setCellValueFactory(new PropertyValueFactory<>("WorkType"));
            column_tplace.setCellValueFactory(new PropertyValueFactory<>("WorkingPlace"));
            column_tclient.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            column_tperiod.setCellValueFactory(new PropertyValueFactory<>("Period"));
            column_bidvalidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            column_tdate.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            column_tcost.setCellValueFactory(new PropertyValueFactory<>("EstimatedCost"));

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load tenderDB to table
    private void loadDBtoTable() {

        data.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("SELECT * FROM tender");
            rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        ttable.setItems(data);

    }

    //rowclick for tendertable
    private void RowclickEvent() {
        ttable.setOnMouseClicked((e) -> {
            TenderDetails t1 = ttable.getItems().get(ttable.getSelectionModel().getSelectedIndex());

            tenderId.setText(t1.getTenderId());
            tenderName.setText(t1.getTenderName());
            grade.setValue(t1.getGrade());
            category.setValue(t1.getCategory());
            workType.setValue(t1.getWorkType());
            place.setValue(t1.getWorkingPlace());
            companyName.setText(t1.getCompanyName());
            period.setText(t1.getPeriod());
            bidValidity.setText(t1.getBidValidity());
            tdate.setValue(LocalDate.parse(t1.getTdate()));
            estimatedCost.setText(t1.getEstimatedCost());
        
        });

    }
    
    

    @FXML
    private void updateClicked(ActionEvent event) {
        if (validatefields()) {

            if (alertboxConfirm("Update tender details !", "Do you really want to update")) {

                try {

                    pst = con.prepareStatement("UPDATE tender set tenderName= '" + tenderName.getText() + "',grade= '" + grade.getValue() + "',category = '" + category.getValue() + "',workType = '" + workType.getValue() + "',workingPlace = '" + place.getValue() + "',companyName = '" + companyName.getText() + "',period = '" + Integer.parseInt(period.getText()) + "',bidValidity = '" + Integer.parseInt(bidValidity.getText()) + "',date = '" + tdate.getValue() + "',estimatedCost = '" + Double.parseDouble(estimatedCost.getText()) + "'  where tenderId = '" + tenderId.getText() + "' ");
                    pst.execute();

                } catch (Exception e) {
                    System.out.println("Update  error");

                }

                assignTableValues();
                loadDBtoTable();

            }

        }
    }

    @FXML
    private void deleteClicked(ActionEvent event) {

        if (alertboxConfirm("Delete tender details !", "Do you really want to Delete ?")) {

            try {

                pst = con.prepareStatement("DELETE from tender where tenderId = '" + tenderId.getText() + "' ");
                pst.execute();

            } catch (Exception e) {
                System.out.println("del  error");

            }

            assignTableValues();
            loadDBtoTable();

        }

    }

    @FXML
    private void viewClicked(ActionEvent event) throws IOException {

        //   FXMLLoader fxmlLoader = FXMLLoader(getClass().getResource("Demo.fxml"));
        //    Parent root1 = (Parent) fxmlLoader.load();
        Parent root1 = FXMLLoader.load(getClass().getResource("ViewTender.fxml"));
        Scene root1_scene = new Scene(root1);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(root1_scene);

        stage.show();

        /*
        try {
            AnchorPane cpane = FXMLLoader.load(getClass().getResource("ViewTender.fxml"));
            centerpane.getChildren().setAll(cpane);
        
        } catch (IOException ex) {
            Logger.getLogger(TenderController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
         */
    }

    @FXML
    private void esti() throws IOException {

        //double estimated_cost;
/*
        Parent root2 = FXMLLoader.load(getClass().getResource("estimation_popup.fxml"));

        Scene scene = new Scene(root2);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        
  */
        String est_tenderId = tenderId.getText();
        String est_grade = grade.getValue().toString();
        String est_tcategory = category.getValue().toString();
        

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("estimation_popup.fxml"));
        try {
           loader.load();
        } catch (Exception e) {
            System.out.println("Error loading popup class " + e);
          
        }
        
        try {
            Estimation_popupController estimate = new FXMLLoader().getController();
        estimate.setText(est_tenderId, est_grade, est_tcategory);
        
        } catch (Exception e) {
            System.out.println("Error loading set method " + e);
        }
        
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
        
        
        
        
        

    }

    //search
    private void searchTender() {
        tsearch.setOnKeyReleased(e -> {
            if (tsearch.getText().equals("")) {
                loadDBtoTable();
            } else {
                data.clear();
                try {

                    Connection con = DbConnection.getConnection();

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where tenderName LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where grade LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where category LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where estimatedCost LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%'"
                            + "UNION SELECT * FROM tender where workingPlace LIKE '%" + tsearch.getText() + "%' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new TenderDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getString(7), rs.getString(11), "" + rs.getInt(8), "" + rs.getInt(9), "" + rs.getDate(6), "" + rs.getDouble(10)));
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                ttable.setItems(data);

            }

        });//event
    }

    //alertbox Information
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
    private void keytyped(javafx.scene.input.KeyEvent event) {

        if (!(event.getCode().isDigitKey())) {

            // System.out.println(event.getCode());
            // event.consume();;
        }

        /*
        char c = event.getKeyChar();
        
      if (!((c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_BACK_SPACE) ||
         (c == KeyEvent.VK_DELETE))) {
        
        event.consume();
      }

         */
    }

    //validation
    private boolean validatefields() {
        if (tenderId.getText().isEmpty() || tenderName.getText().isEmpty() || (grade.getValue() == null)
                || (category.getValue() == null) || (workType.getValue() == null) || (place.getValue() == null)
                || companyName.getText().isEmpty() || period.getText().isEmpty() || bidValidity.getText().isEmpty()
                || estimatedCost.getText().isEmpty() || (tdate.getValue() == null)) {
            alertboxWarn("Add Tender", "All fields should be filled !");
            //  cssLabel.getStylesheets().add("error");

            return false;

        } else if (Integer.parseInt(period.getText()) <= Integer.parseInt(bidValidity.getText())) {
            alertboxWarn("Add Tender", "Bid Validity should be less than Tender Period");
            period.setText("");
            bidValidity.setText("");

            cssLabel.getStylesheets().remove("error");
            return false;

        } else {
            cssLabel.getStylesheets().remove("error");
            return true;

        }

    }

    
    
    //report
    @FXML
    private void tender_report(ActionEvent event)  {
        /*
        try {
            DbConnection.openConnection();
            Connection con = DbConnection.getConnection();
            String report = "/UsersKishBelic/Desktop/ITP/TenderManagement/src/tendermanagement/Tender.jrxml";
           JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
           System.out.println("sdas" + e);
        }*/
    }

}
