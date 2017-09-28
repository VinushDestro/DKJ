/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.Transport;

/**
 *
 * @author User
 */
public class TransportDetail {
    String tripId;
    String RegNo;
    String tenderId;
    String destination;
    String date;
    String cost;

    public TransportDetail(String tripId, String RegNo, String tenderId, String destination, String date, String cost) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    
    
}
