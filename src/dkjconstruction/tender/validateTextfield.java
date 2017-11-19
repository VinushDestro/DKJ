/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

import com.jfoenix.controls.JFXTextField;
/**
 *
 * @author KishBelic
 */
public class validateTextfield {
    
    
    
    public static boolean validateNumberTextfield(JFXTextField text)
    {
    boolean bool =false;
    
        
        
        if(text.getText().isEmpty())
        {
        text.clear();
        
        TenderManagement.alerboxInfo("Validate empty", "Textfield is empty");
        
        return true;
        
        }
        
        if(!text.getText().matches("[0-9]*"))
        {
        text.clear();
        
        TenderManagement.alerboxInfo("Invalid input", "Input should be in numbers");
        return true;
        
        }
 
        return bool;
    
    }
    
    
    public static boolean validateNumber(JFXTextField text,String title,String msg)
    {
    boolean bool = false;
    
        
        
        if(!text.getText().matches("[0-9]+"))
        {
        text.clear();
        
        TenderManagement.alerboxInfo(title, msg);
        return true;
        
        }
 
        return bool;
    
    }
    
    
    public static boolean validateLetterTextfield(JFXTextField text)
    {
    boolean bool =false;
    
        
        if(!text.getText().matches("[a-z A-Z]*"))
        {
        text.clear();
        
        TenderManagement.alerboxInfo("Invalid input", "Input should be in numbers");
        return true;
        
        }
 
        return bool;
    
    }
    
    
    
}
