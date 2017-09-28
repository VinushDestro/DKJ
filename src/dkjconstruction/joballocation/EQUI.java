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
class EQUI {
    
    private String eQID;
    private String eQNAME;

    public String geteQID() {
        return eQID;
    }

    public void seteQID(String eQID) {
        this.eQID = eQID;
    }

    public String geteQNAME() {
        return eQNAME;
    }

    public void seteQNAME(String eQNAME) {
        this.eQNAME = eQNAME;
    }

    public EQUI(String eQID, String eQNAME) {
        this.eQID = eQID;
        this.eQNAME = eQNAME;
    }

   
    
}
