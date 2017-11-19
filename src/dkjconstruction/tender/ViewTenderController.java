/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import dkjconstruction.DbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.plaf.RootPaneUI;
import dkjconstruction.tendermanagement.TenderReport.Report;



/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class ViewTenderController implements Initializable {

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private JFXTextField tsearch;
    @FXML
    private AnchorPane centerpane;

    private ObservableList<TenderDetails> data;

    
    @FXML
    private TableView<TenderDetails> view_table;
    @FXML
    private TableColumn<?, ?> collumn_tenderId;
    @FXML
    private TableColumn<?, ?> collumn_date;
    @FXML
    private TableColumn<?, ?> collumn_bidValidity;
    @FXML
    private TableColumn<?, ?> collumn_cName;
    
    @FXML
    private JFXComboBox v_status;
    
    @FXML private javafx.scene.control.Button previousButton;
    @FXML
    private Label ve_noOfEmp;
    @FXML
    private Label v_tid;
    @FXML
    private Label ve_materialCost;
    @FXML
    private Label ve_totalCost;
    @FXML
    private Label vr_materialCost;
    @FXML
    private Label vr_totalCost;
    @FXML
    private Label vr_noOfEmp;
    @FXML
    private TableColumn<?, ?> collumn_place;
    @FXML
    private TableColumn<?, ?> collumn_period;
    @FXML
    private ProgressBar pb;
    @FXML
    private ProgressIndicator pi;
    @FXML
    private Label lbl_days;
    @FXML
    private Label vr_transportCost;
    @FXML
    private Label vr_hrmCost;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       v_status.getItems().addAll("Pending","On progress");
        
        
        data = FXCollections.observableArrayList();
        assignTableValues();
        
        
        
          rowclick();  
         
        
        searchTender();
        
       // rowclickProgress();

    }

    @FXML
    private void previousClicked(ActionEvent event) throws IOException {

        /*Parent root = FXMLLoader.load(getClass().getResource("Tender.fxml"));
        Scene root_scene = new Scene(root);
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(root_scene);
        
        stage.show();*/
        
//        Stage s = (Stage) previousButton.getScene().getWindow();
//         s.close();
        
         /* FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TenderHome.fxml"));
         Parent root1 = (Parent) fxmlLoader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root1));
         stage.show();*/
        dkjconstruction.DKJConstruction.showTender_TenderHome();

    }

    private void assignTableValues() {
        try {
            DbConnection.openConnection();
            con = DbConnection.getConnection();

            //Set cell value factory to tableview.
            collumn_tenderId.setCellValueFactory(new PropertyValueFactory<>("TenderId"));
            collumn_cName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
            collumn_bidValidity.setCellValueFactory(new PropertyValueFactory<>("BidValidity"));
            collumn_date.setCellValueFactory(new PropertyValueFactory<>("Tdate"));
            collumn_period.setCellValueFactory(new PropertyValueFactory<>("Period"));
            collumn_place.setCellValueFactory(new PropertyValueFactory<>("WorkingPlace"));

            
            

        } catch (Exception e) {
            System.err.println("Error set table data " + e);
        }

    }

    //load tenderDB to table
    @FXML
    private void loadDBtoTable() {
  
        
        data.clear();
        try {

            Connection con = DbConnection.getConnection();

            pst = con.prepareStatement("Select tenderId,date,bidValidity,period,companyName,workingPlace  \n"
                                    + "from tender \n"
                                   + "where status = '"+ v_status.getValue().toString()+ "' ");
            rs = pst.executeQuery();

            while (rs.next()) { 
                data.add(new TenderDetails(rs.getString(1), rs.getString(6), rs.getString(5), rs.getString(4), rs.getString(3), rs.getString(2)));
            }

        } catch (Exception e) {
            System.err.println("Error loading table data " + e);

        }
        view_table.setItems(data);


    }
    
    private void rowclick() {
        
        
        view_table.setOnMouseClicked((MouseEvent event) -> {

            TenderDetails t3 = view_table.getItems().get(view_table.getSelectionModel().getSelectedIndex());

            v_tid.setText(t3.getTenderId());

           

            
            ve_materialCost.setText(Double.toString(TenderManagement.findCost(t3.getTenderId(), 'e', 'm')));
            ve_totalCost.setText(Double.toString(TenderManagement.findCost(t3.getTenderId(), 'e', 'f')));
            ve_noOfEmp.setText(Integer.toString(TenderManagement.getEmp(t3.getTenderId(), 'e')));
            
            
            //vr_materialCost.setText(Double.toString(TenderManagement.findCost(t3.getTenderId(), 'a', 'm')));
            //vr_totalCost.setText(Double.toString(TenderManagement.findCost(t3.getTenderId(), 'a', 'f')));
            vr_noOfEmp.setText(Integer.toString(TenderManagement.getEmp(t3.getTenderId(), 'a')));
            
            //from findcost class
            vr_transportCost.setText(Double.toString(findCost.findcost(t3.getTenderId(), "transport")));
            vr_hrmCost.setText(Double.toString(findCost.findcost(t3.getTenderId(), "hrsum")));
            vr_totalCost.setText(Double.toString(findCost.findcost(t3.getTenderId(), "total")));
            vr_materialCost.setText(Double.toString(findCost.findcost(t3.getTenderId(), "material")));
            
            
            //rowclick progress
            v_tid.setText(t3.getTenderId());

           int passedDays = 0;
           int bidValidity = 0;
           int rDays =0;

            try {
                con = DbConnection.getConnection();
                pst = con.prepareStatement("SELECT tenderId,DATEDIFF(CURDATE(), date) AS 'DaysPassed',bidValidity,period FROM tender where tenderId = '"+ t3.getTenderId() +"' AND status = '"+v_status.getValue().toString()+"' ");
                rs = pst.executeQuery();
                
                if(rs.next())
                {
                passedDays = passedDays + rs.getInt(2);
                bidValidity = bidValidity + rs.getInt(3);
                rDays = bidValidity - passedDays;
                }
                
            } catch (Exception e) {
                System.out.println("date error" + e);
            }
            double percent = passedDays/(bidValidity*1.0);
            System.out.println(percent);
            
            DoubleProperty pDAys = new SimpleDoubleProperty(percent);
            pb.progressProperty().bind(pDAys);
            pi.progressProperty().bind(pDAys);
            
            lbl_days.setText(Integer.toString(rDays) + " days remaining");
            
            
            

        });
        
        
        

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

                    pst = con.prepareStatement("SELECT * FROM tender where tenderId LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' "
                            + "UNION SELECT * FROM tender where date LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' "
                            + "UNION SELECT * FROM tender where period LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' "
                            + "UNION SELECT * FROM tender where bidValidity LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' "
                            + "UNION SELECT * FROM tender where companyName LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' "
                            + "UNION SELECT * FROM tender where workingPlace LIKE '%" + tsearch.getText() + "%' AND status ='"+ v_status.getValue().toString()+ "' ");
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        data.add(new TenderDetails(rs.getString(1), rs.getString(7), rs.getString(11), rs.getString(8), rs.getString(9), rs.getString(6)));
                       
                    }

                } catch (Exception ex) {
                    System.err.println("Error loading table data " + ex);

                }

                view_table.setItems(data);

            }

        });//event
    }
    
    
    
    
    private void rowclickProgress() {
        
        view_table.setOnMouseClicked((MouseEvent event) -> {

            TenderDetails t4 = view_table.getItems().get(view_table.getSelectionModel().getSelectedIndex());

            v_tid.setText(t4.getTenderId());

           int passedDays = 0;
           int bidValidity = 0;
           int rDays =0;

            try {
                con = DbConnection.getConnection();
                pst = con.prepareStatement("SELECT tenderId,DATEDIFF(CURDATE(), date) AS 'DaysPassed',bidValidity,period FROM tender where tenderId = '"+ t4.getTenderId() +"' AND status = '"+v_status.getValue().toString()+"' ");
                rs = pst.executeQuery();
                
                if(rs.next())
                {
                passedDays = passedDays + rs.getInt(2);
                bidValidity = bidValidity + rs.getInt(3);
                rDays = bidValidity - passedDays;
                }
                
            } catch (Exception e) {
                System.out.println("date error" + e);
            }
            double percent = passedDays/(bidValidity*1.0);
            System.out.println(percent);
            
            DoubleProperty pDAys = new SimpleDoubleProperty(percent);
            pb.progressProperty().bind(pDAys);
            pi.progressProperty().bind(pDAys);
            
            
            
            lbl_days.setText(Integer.toString(rDays) + " days remaining");

        });
        
        

    }
    
    @FXML
    public void pending_tenderReport()
    {
        
            Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\tendermanagement\\TenderReport\\viewPending.jrxml");
 
    }
    
   
    
    @FXML
    public void ongoing_tenderReport()
    {
    Report.gen_Normal_report("C:\\Users\\Mahesh\\Documents\\NetBeansProjects\\dkjconstructions\\src\\dkjconstruction\\tendermanagement\\TenderReport\\viewOngoing.jrxml");
 
    
    }
    
    

}
