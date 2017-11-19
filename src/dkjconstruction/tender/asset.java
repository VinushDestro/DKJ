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
public class asset {
    
    private String AssetType;
    private int AssetCount;

    public asset(String AssetType, int AssetCount) {
        this.AssetType = AssetType;
        this.AssetCount = AssetCount;
    }

    /**
     * @return the AssetType
     */
    public String getAssetType() {
        return AssetType;
    }

    /**
     * @param AssetType the AssetType to set
     */
    public void setAssetType(String AssetType) {
        this.AssetType = AssetType;
    }

    /**
     * @return the AssetCount
     */
    public int getAssetCount() {
        return AssetCount;
    }

    /**
     * @param AssetCount the AssetCount to set
     */
    public void setAssetCount(int AssetCount) {
        this.AssetCount = AssetCount;
    }
    
}
