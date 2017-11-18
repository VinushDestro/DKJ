/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkjconstruction.accounts;

/**
 *
 * @author h3
 */
public class dwdet {
 private String dwdate;
    private String dwtype;
    private String dwamount;
    
    public dwdet(String dwdate,String dwtype,String dwamount){
     this.dwdate = dwdate;
    this.dwtype = dwtype;
     this.dwamount = dwamount;
  
    } 

    public String getDwdate() {
        return dwdate;
    }

    public void setDwdate(String dwdate) {
        this.dwdate = dwdate;
    }

    public String getDwtype() {
        return dwtype;
    }

    public void setDwtype(String dwtype) {
        this.dwtype = dwtype;
    }

    public String getDwamount() {
        return dwamount;
    }

    public void setDwamount(String dwamount) {
        this.dwamount = dwamount;
    }
    
}
