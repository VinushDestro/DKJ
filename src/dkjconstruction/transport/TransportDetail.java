/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.transport;

import java.sql.Date;

/**
 *
 * @author User
 */
public class TransportDetail {
    String tripId;
    String RegNo;
    String tenderId;
    String destination;
    Date date;
    Double cost;

    public TransportDetail(String tripId, String RegNo, String tenderId, String destination, Date date, Double cost) {
        this.tripId = tripId;
        this.RegNo = RegNo;
        this.tenderId = tenderId;
        this.destination = destination;
        this.date = date;
        this.cost = cost;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    
}
