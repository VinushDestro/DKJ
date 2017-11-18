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
public class chrdet {

    private String chdate;
    private String chtype;
    private String chamount;

    public chrdet(String chdate, String chtype, String chamount) {
        this.chdate = chdate;
        this.chtype = chtype;
        this.chamount = chamount;
    }
;

    public String getChdate() {
        return chdate;
    }

    public void setChdate(String chdate) {
        this.chdate = chdate;
    }


    public String getChtype() {
        return chtype;
    }

    public void setChtype(String chtype) {
        this.chtype = chtype;
    }


    public String getChamount() {
        return chamount;
    }

    public void setChamount(String chamount) {
        this.chamount = chamount;
    }


}
