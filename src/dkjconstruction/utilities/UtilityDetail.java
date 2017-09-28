/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.utilities;

/**
 *
 * @author h3
 */
public class UtilityDetail {

    private String Type;
    private String ID;
    private String Amount;
    private String BillDate;
    private String PaidDate;
    private String PaymentMethod;

    public UtilityDetail(String Type, String ID, String Amount, String BillDate, String PaidDate, String PaymentMethod) {
        this.Type = Type;
        this.ID = ID;
        this.Amount = Amount;
        this.BillDate = BillDate;
        this.PaidDate = PaidDate;
        this.PaymentMethod = PaymentMethod;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type the Type to set
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the Amount
     */
    public String getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the BillDate
     */
    public String getBillDate() {
        return BillDate;
    }

    /**
     * @param BillDate the BillDate to set
     */
    public void setBillDate(String BillDate) {
        this.BillDate = BillDate;
    }

    /**
     * @return the PaidDate
     */
    public String getPaidDate() {
        return PaidDate;
    }

    /**
     * @param PaidDate the PaidDate to set
     */
    public void setPaidDate(String PaidDate) {
        this.PaidDate = PaidDate;
    }

    /**
     * @return the PaymentMethod
     */
    public String getPaymentMethod() {
        return PaymentMethod;
    }

    /**
     * @param PaymentMethod the PaymentMethod to set
     */
    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    
}
