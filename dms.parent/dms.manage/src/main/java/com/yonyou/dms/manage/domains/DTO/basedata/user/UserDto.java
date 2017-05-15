
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserDto.java
*
* @Author : yll
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata.user;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 用户Dto
 * 
 * @author yll
 * @date 2016年8月15日
 */

public class UserDto {

    private Long userId;
    private Long    employeeId;
    private String  dealerCode;
    @Required
    private String  userCode;
    private String  userName;        // 用户名，自己加的???***
    private Integer userStatus;
    private String  password;
    private String  newPassword;
    private Date    loginLastTime;
    private String  employeeNo;
    private String  orgCode;
    private String  isServiceAdvisor;
    private String  isConsultant;
    
    private Integer isModPassword;

    
    public Integer getIsModPassword() {
        return isModPassword;
    }

    
    public void setIsModPassword(Integer isModPassword) {
        this.isModPassword = isModPassword;
    }

    public String getIsServiceAdvisor() {
        return isServiceAdvisor;
    }

    public void setIsServiceAdvisor(String isServiceAdvisor) {
        this.isServiceAdvisor = isServiceAdvisor;
    }

    public String getIsConsultant() {
        return isConsultant;
    }

    public void setIsConsultant(String isConsultant) {
        this.isConsultant = isConsultant;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLoginLastTime() {
        return loginLastTime;
    }

    public void setLoginLastTime(Date loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
