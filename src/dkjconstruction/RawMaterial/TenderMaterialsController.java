/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.RawMaterial;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class TenderMaterialsController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TableColumn tabtenderid;
    @FXML
    private TableColumn tabmaterialtype;
    @FXML
    private TableColumn tabmaterialquantity;
    @FXML
    private Button assign;
    @FXML
    private JFXTextField entertender;
    @FXML
    private JFXTextField entertype;
    @FXML
   TableView<TenderDetail> tenderTab;
    
    /**
     * Initializes the controller class.
     */
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        tabtenderid.setCellValueFactory(new PropertyValueFactory<TenderDetail,String>("id"));
        tabmaterialtype.setCellValueFactory(new PropertyValueFactory<TenderDetail,String>("type"));
        tabmaterialquantity.setCellValueFactory(new PropertyValueFactory<TenderDetail,String>("quantity"));
       
     



        try {
            tenderTab.setItems(RawMaterial.getTender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }    

    
    
    @FXML
    private void doassignrawmaterial(ActionEvent event) {
    }
    
}
