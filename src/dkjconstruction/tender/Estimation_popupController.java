/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXTextField;
//import com.sun.j3d.loaders.Loader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author KishBelic
 */
public class Estimation_popupController implements Initializable {

    @FXML
    private Label est_tenderId;
    @FXML
    private Label est_grade;
    @FXML
    private Label est_roadType;
    @FXML
    private JFXTextField est_cement;
    @FXML
    private JFXTextField est_gravel;
    @FXML
    private JFXTextField est_steel;
    @FXML
    private JFXTextField est_asphalt;
    @FXML
    private JFXTextField est_sand;
    @FXML
    private JFXTextField est_distance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void est_estimateClicked(ActionEvent event) {
        
       
        
      
        
    }
    public void setText(String est_tenderId,String est_grade,String est_roadType)
    {
    this.est_tenderId.setText(est_tenderId);
    this.est_grade.setText(est_grade);
    this.est_roadType.setText(est_roadType);
    }
    
    
}
