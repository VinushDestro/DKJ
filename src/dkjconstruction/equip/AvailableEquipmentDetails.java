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


    //private String equipID;
    private String name;
    private double cost;
    private int count;
    private double totalCost;
    private String assignedCount;
   private String availableCount;
    

     public  AvailableEquipmentDetail( String name, double cost,int count,double totalCost,String assignedCount,String availableCount) {
       // this.equipID = equipID;
        this.name = name;
        this.cost = cost;
        this.count=count;
        this.totalCost = totalCost;
         this.assignedCount=assignedCount;
        this.availableCount=availableCount;
        

    }

      

//    public String getequipID() {
//        return equipID;
//    }
//
//    public void setequipID(String equipID) {
//         this.equipID = equipID;
//    }

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
    
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getAssignedCount() {
        return assignedCount;
    }

    public void setAssignedCount(String assignedCount) {
         this.assignedCount=assignedCount;
    }

    
    public String getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(String availableCount) {
       
        this.availableCount=availableCount;
    }

    

 }

    
}
