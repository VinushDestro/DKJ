/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.equip;

/**
 *
 * @author VINUSH
 */
public class AvailableEquipmentDetails {
    public class AvailableEquipmentDetail {


    private String equipID;
    private String name;
    private double cost;
    private int count;
   
    

     public  AvailableEquipmentDetail(String equipID, String name, double cost,int count) {
        this.equipID = equipID;
        this.name = name;
        this.cost = cost;
        this.count=count;
        

    }

        private AvailableEquipmentDetail(String equipID, String name,int count, double cost) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    public String getequipID() {
        return equipID;
    }

    public void setequipID(String equipID) {
         this.equipID = equipID;
    }

    public String getName()
    {return name; }

    public void setName(String name)
    { this.name = name; }

  
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    

 }

    
}
