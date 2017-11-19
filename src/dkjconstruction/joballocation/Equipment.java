/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

/**
 *
 * @author Ranjitha
 */
//Class Equipment Referncing equipment
public class Equipment {
    
    // private String equipmentId;
    private String equipName;
    private Integer available;
/*
    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
*/
    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Equipment( String equipName, Integer available) {
       // this.equipmentId = equipmentId;
        this.equipName = equipName;
        this.available = available;
    }
    
}
