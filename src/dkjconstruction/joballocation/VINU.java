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
public class VINU {
    private String RegNo;
    private String Name;
    private String Type;

    public VINU(String RegNo, String Name, String Type) {
        this.RegNo = RegNo;
        this.Name = Name;
        this.Type = Type;
    }

    /**
     * @return the RegNo
     */
    public String getRegNo() {
        return RegNo;
    }

    /**
     * @param RegNo the RegNo to set
     */
    public void setRegNo(String RegNo) {
        this.RegNo = RegNo;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
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
   
    
    
}
