/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.vehicle;

/**
 *
 * @author VINUSH
 */
public class AvailableVehicleDetail {
     private String regNo;
    private String name;
    private String type;
    private String cost;
    private String lifeTime;
    private String boughtDate;
    private String condition;
    private String currentDep;
    private String depPercent;
    private String totalDep;
    private String currentValue;
    
    

     public  AvailableVehicleDetail(String regNo, String name, String type,String cost,String lifeTime,String boughtDate,String condition,String depPercent, String currentDep,String totalDep,String currentValue) {
        this.regNo = regNo;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.lifeTime=lifeTime;
        this.boughtDate=boughtDate;
        this.condition=condition;
        this.depPercent=depPercent;
        this.currentDep=currentDep;
        this.totalDep=totalDep;
        this.currentValue=currentValue;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    
    public String getCurrentDep() {
        return this.currentDep;
    }

    public void setCurrentDep(String currentDep) {
        this.cost = cost;
    }
    
    
    public String getLifeTim() {
        return lifeTime;
    }

    public void setLifeTime(String lifeTime) {
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
//////////////////////////////////////////////////////////////////////////
    
    public String getDepPercent() {
        return depPercent;
    }

    public void setDepPercent(String depPercent) {
         this.depPercent=depPercent;
    }
    
     public String getTotalDep() {
        return totalDep;
    }

    public void setTotalDep(String totalDep) {
        this.totalDep=totalDep;
        
    }
    
     public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
       this.currentValue=currentValue;
    }

    
}
