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
public class TenderDetails {
    
    private String TenderId; 
    private String TenderName;
    private String Grade;
    private String Category;
    private String WorkType;
    private String WorkingPlace;
    private String CompanyName;
    private String Period;
    private String BidValidity;
    private String Tdate;
    private String EstimatedCost;
    
    private String MaterialType;
    private String MaterialCount;
    private String m_price;
    private String NoOfEmployee;

    public TenderDetails(String TenderId, String WorkingPlace, String CompanyName, String Period, String BidValidity, String Tdate) {
        this.TenderId = TenderId;
        this.WorkingPlace = WorkingPlace;
        this.CompanyName = CompanyName;
        this.Period = Period;
        this.BidValidity = BidValidity;
        this.Tdate = Tdate;
    }

    public TenderDetails(String NoOfEmployee) {
        this.NoOfEmployee = NoOfEmployee;
    }

    

    public TenderDetails(String MaterialType, String MaterialCount, String m_price) {
        this.MaterialType = MaterialType;
        this.MaterialCount = MaterialCount;
        this.m_price = m_price;
    }
    

    /* public TenderDetails(String TenderId, String BidValidity, String EstimatedCost) {
    this.TenderId = TenderId;
    this.BidValidity = BidValidity;
    this.EstimatedCost = EstimatedCost;
    }*/

    public TenderDetails(String TenderId, String TenderName, String Grade, String Category, String WorkType, String WorkingPlace, String CompanyName, String Period, String BidValidity, String Tdate, String EstimatedCost) {
        this.TenderId = TenderId;
        this.TenderName = TenderName;
        this.Grade = Grade;
        this.Category = Category;
        this.WorkType = WorkType;
        this.WorkingPlace = WorkingPlace;
        this.CompanyName = CompanyName;
        this.Period = Period;
        this.BidValidity = BidValidity;
        this.Tdate = Tdate;
        this.EstimatedCost = EstimatedCost;
    }

    

    /**
     * @return the TenderId
     */
    public String getTenderId() {
        return TenderId;
    }

    /**
     * @param TenderId the TenderId to set
     */
    public void setTenderId(String TenderId) {
        this.TenderId = TenderId;
    }

    /**
     * @return the TenderName
     */
    public String getTenderName() {
        return TenderName;
    }

    /**
     * @param TenderName the TenderName to set
     */
    public void setTenderName(String TenderName) {
        this.TenderName = TenderName;
    }

    /**
     * @return the Grade
     */
    public String getGrade() {
        return Grade;
    }

    /**
     * @param Grade the Grade to set
     */
    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    /**
     * @return the Category
     */
    public String getCategory() {
        return Category;
    }

    /**
     * @param Category the Category to set
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }

    /**
     * @return the WorkType
     */
    public String getWorkType() {
        return WorkType;
    }

    /**
     * @param WorkType the WorkType to set
     */
    public void setWorkType(String WorkType) {
        this.WorkType = WorkType;
    }

    /**
     * @return the WorkingPlace
     */
    public String getWorkingPlace() {
        return WorkingPlace;
    }

    /**
     * @param WorkingPlace the WorkingPlace to set
     */
    public void setWorkingPlace(String WorkingPlace) {
        this.WorkingPlace = WorkingPlace;
    }

    /**
     * @return the CompanyName
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * @param CompanyName the CompanyName to set
     */
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    /**
     * @return the Period
     */
    public String getPeriod() {
        return Period;
    }

    /**
     * @param Period the Period to set
     */
    public void setPeriod(String Period) {
        this.Period = Period;
    }

    /**
     * @return the BidValidity
     */
    public String getBidValidity() {
        return BidValidity;
    }

    /**
     * @param BidValidity the BidValidity to set
     */
    public void setBidValidity(String BidValidity) {
        this.BidValidity = BidValidity;
    }

    /**
     * @return the EstimatedCost
     */
    public String getEstimatedCost() {
        return EstimatedCost;
    }

    /**
     * @param EstimatedCost the EstimatedCost to set
     */
    public void setEstimatedCost(String EstimatedCost) {
        this.EstimatedCost = EstimatedCost;
    }

    /**
     * @return the Tdate
     */
    public String getTdate() {
        return Tdate;
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
    public String getMaterialCount() {
        return MaterialCount;
    }

    /**
     * @param MaterialCount the MaterialCount to set
     */
    public void setMaterialCount(String MaterialCount) {
        this.MaterialCount = MaterialCount;
    }

    /**
     * @return the NoOfEmployee
     */
    public String getNoOfEmployee() {
        return NoOfEmployee;
    }

    /**
     * @param NoOfEmployee the NoOfEmployee to set
     */
    public void setNoOfEmployee(String NoOfEmployee) {
        this.NoOfEmployee = NoOfEmployee;
    }

    /**
     * @return the m_price
     */
    public String getM_price() {
        return m_price;
    }

    /**
     * @param m_price the m_price to set
     */
    public void setM_price(String m_price) {
        this.m_price = m_price;
    }

    

    
}
