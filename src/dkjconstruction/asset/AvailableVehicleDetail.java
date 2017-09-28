/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.asset;

/**
 *
 * @author VINUSH
 */
public class AvailableVehicleDetail {
     private String regNo;
    private String name;
    private String type;
    private double cost;
    private int lifeTime;
    private String boughtDate;
    private String condition;
    
    

     public  AvailableVehicleDetail(String regNo, String name, String type, double cost,int lifeTime,String boughtDate,String condition) {
        this.regNo = regNo;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.lifeTime=lifeTime;
        this.boughtDate=boughtDate;
        this.condition=condition;

    }

        AvailableVehicleDetail(String name, String regNo, String type, double cost, String boughtDate, int lifeTime, String condition) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
         this.regNo = regNo;
    }

    public String getName() {return name; }

    public void setName(String name) { this.name = name; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double contactNumber) {
        this.cost = cost;
    }
    
    public int getLifeTim() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    
    public String getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate =boughtDate ;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condtion) {
        this.condition = condition ;
    }

    
}
