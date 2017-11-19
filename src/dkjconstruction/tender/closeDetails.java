/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.tender;

/**
 *
 * @author KishBelic
 */
public class closeDetails {
    
    private String TenderId;
    private String Material;
    private int MatCount;

    public closeDetails(String TenderId, String Material, int MatCount) {
        this.TenderId = TenderId;
        this.Material = Material;
        this.MatCount = MatCount;
    }

    public closeDetails(String TenderId) {
        this.TenderId = TenderId;
    }

    
    
    
    public String getTenderId() {
        return TenderId;
    }

    public void setTenderId(String TenderId) {
        this.TenderId = TenderId;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String Material) {
        this.Material = Material;
    }

    public int getMatCount() {
        return MatCount;
    }

    public void setMatCount(int MatCount) {
        this.MatCount = MatCount;
    }
    
    
    
    
    
    
}
