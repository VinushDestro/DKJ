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
public class material {
    
    private String MaterialType;
    private int MaterialCount;

    public material(String MaterialType, int MaterialCount) {
        this.MaterialType = MaterialType;
        this.MaterialCount = MaterialCount;
    }

    /**
     * @return the MaterialType
     */
    public String getMaterialType() {
        return MaterialType;
    }

    /**
     * @param MaterialType the MaterialType to set
     */
    public void setMaterialType(String MaterialType) {
        this.MaterialType = MaterialType;
    }

    /**
     * @return the MaterialCount
     */
    public int getMaterialCount() {
        return MaterialCount;
    }

    /**
     * @param MaterialCount the MaterialCount to set
     */
    public void setMaterialCount(int MaterialCount) {
        this.MaterialCount = MaterialCount;
    }
   

    
    
    
}
