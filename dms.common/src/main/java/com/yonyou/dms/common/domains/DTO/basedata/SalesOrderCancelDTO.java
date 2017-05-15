
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : SaleSOrderCancleDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;



/**
* TODO description
* @author LiGaoqi
* @date 2017年1月13日
*/

public class SalesOrderCancelDTO {
    private String  applyList;
    private Integer abortingReason;
    private double  penaltyAmount;
    private double  orderReceivableSum;
    private double  orderArrearageAmount;
    private Integer payOff;
    
    public double getOrderReceivableSum() {
        return orderReceivableSum;
    }


    
    public void setOrderReceivableSum(double orderReceivableSum) {
        this.orderReceivableSum = orderReceivableSum;
    }


    
    public double getOrderArrearageAmount() {
        return orderArrearageAmount;
    }


    
    public void setOrderArrearageAmount(double orderArrearageAmount) {
        this.orderArrearageAmount = orderArrearageAmount;
    }


    
    public Integer getPayOff() {
        return payOff;
    }


    
    public void setPayOff(Integer payOff) {
        this.payOff = payOff;
    }


    /**
     * @return the applyList
     */
    public String getApplyList() {
        return applyList;
    }

    
    /**
     * @param applyList the applyList to set
     */
    public void setApplyList(String applyList) {
        this.applyList = applyList;
    }


    
    public Integer getAbortingReason() {
        return abortingReason;
    }


    
    public void setAbortingReason(Integer abortingReason) {
        this.abortingReason = abortingReason;
    }


    
    /**
     * @return the penaltyAmount
     */
    public double getPenaltyAmount() {
        return penaltyAmount;
    }


    
    /**
     * @param penaltyAmount the penaltyAmount to set
     */
    public void setPenaltyAmount(double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }


    
  
    
    

}
