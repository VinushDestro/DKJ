/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;
//import dkj.construction.DbConnection; 
/**
 *
 * @author Ranjitha
 */
 public class KISHANTH {
    private String tenderId;
    private String worktype;
    private String category;
    private String workingPlace;
    private String estimatedCost;
    private String tenderDate;

    public KISHANTH(String tenderId, String worktype, String category, String workingPlace, String estimatedCost,String tenderDate) {
        this.tenderId = tenderId;
        this.worktype = worktype;
        this.category = category;
        this.workingPlace = workingPlace;
        this.estimatedCost = estimatedCost;
        this.tenderDate=tenderDate;
    }

    /**
     * @return the tenderId
     */
    public String getTenderId() {
        return tenderId;
    }

    /**
     * @param tenderId the tenderId to set
     */
    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    /**
     * @return the worktype
     */
    public String getWorktype() {
        return worktype;
    }

    /**
     * @param worktype the worktype to set
     */
    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the workingPlace
     */
    public String getWorkingPlace() {
        return workingPlace;
    }

    /**
     * @param workingPlace the workingPlace to set
     */
    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    /**
     * @return the estimatedCost
     */
    public String getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * @param estimatedCost the estimatedCost to set
     */
    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getTenderDate() {
        return tenderDate;
    }

    public void setTenderDate(String tenderDate) {
        this.tenderDate = tenderDate;
    }

   
    
 }