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
public class equip {
    
    private String EquipName;
    private int Count;

    public equip(String EquipName, int Count) {
        this.EquipName = EquipName;
        this.Count = Count;
    }

    /**
     * @return the EquipName
     */
    public String getEquipName() {
        return EquipName;
    }

    /**
     * @param EquipName the EquipName to set
     */
    public void setEquipName(String EquipName) {
        this.EquipName = EquipName;
    }

    /**
     * @return the Count
     */
    public int getCount() {
        return Count;
    }

    /**
     * @param Count the Count to set
     */
    public void setCount(int Count) {
        this.Count = Count;
    }

    
    
    
}
