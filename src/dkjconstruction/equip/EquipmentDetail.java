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
public class EquipmentDetail {
    


    private String equipID;
    private String name;
    private double cost;
    private int count;
   
    

     public EquipmentDetail(String equipID, String name,int count, double cost) {
        this.equipID = equipID;
        this.name = name;
        this.count=count;
        this.cost = cost;
        
        

    }

     


    

    public String getName() {return name; }

    public void setName(String name) { this.name = name; }

  
    public double getCost() {
        return cost;
    }

    public void setCost(double contactNumber) {
        this.cost = cost;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int lifeTime) {
        this.count = count;
    }

    /**
     * @return the equipID
     */
    public String getEquipID() {
        return equipID;
    }

    /**
     * @param equipID the equipID to set
     */
    public void setEquipID(String equipID) {
        this.equipID = equipID;
    }
    
    

 }

