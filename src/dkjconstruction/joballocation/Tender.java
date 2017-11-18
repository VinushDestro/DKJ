/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.joballocation;

/**
 *
 * @author Ranjitha
 */
public class Tender {
     private String pendingtenderId;
     private String pendingtype;

    public String getPendingtenderId() {
        return pendingtenderId;
    }

    public void setPendingtenderId(String pendingtenderId) {
        this.pendingtenderId = pendingtenderId;
    }

    public String getPendingtype() {
        return pendingtype;
    }

    public void setPendingtype(String pendingtype) {
        this.pendingtype = pendingtype;
    }

    public Tender(String pendingtenderId, String pendingtype) {
        this.pendingtenderId = pendingtenderId;
        this.pendingtype = pendingtype;
    }
}
