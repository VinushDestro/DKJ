/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.rawmaterial;

/**
 *
 * @author Asus
 */
public class RawMaterialDetail {
    
    private String type;
    private int quantity;
    private double price;
    private String measurement;
    private int supplier;
   
    public RawMaterialDetail(String type,int quantity,double price,String measurement,int supplier) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.measurement = measurement;
        this.supplier = supplier;
       
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the measurement
     */
    public String getMeasurement() {
        return measurement;
    }

    /**
     * @param measurement the measurement to set
     */
    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    /**
     * @return the supplier
     */
    public int getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }
}