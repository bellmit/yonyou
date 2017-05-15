
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestDTO.java
*
* @Author : jcsi
*
* @Date : 2016年9月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月9日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;

import java.util.List;

/**
*
* @author jcsi
* @date 2016年9月9日
*/

public class SuggestDTO {

    private List<SuggestLabourDTO> suggestLabours; 
    
    private List<SuggestPartDTO> suggestParts;
    
    private String delLabourId;
    
    private String delPartId;
    
    private String vin;
    
    

    
    
    public String getVin() {
        return vin;
    }


    
    public void setVin(String vin) {
        this.vin = vin;
    }


    public List<SuggestLabourDTO> getSuggestLabours() {
        return suggestLabours;
    }

    
    public void setSuggestLabours(List<SuggestLabourDTO> suggestLabours) {
        this.suggestLabours = suggestLabours;
    }

    
    public List<SuggestPartDTO> getSuggestParts() {
        return suggestParts;
    }

    
    public void setSuggestParts(List<SuggestPartDTO> suggestParts) {
        this.suggestParts = suggestParts;
    }


    
    public String getDelLabourId() {
        return delLabourId;
    }


    
    public void setDelLabourId(String delLabourId) {
        this.delLabourId = delLabourId;
    }


    
    public String getDelPartId() {
        return delPartId;
    }


    
    public void setDelPartId(String delPartId) {
        this.delPartId = delPartId;
    }
    
    
}
