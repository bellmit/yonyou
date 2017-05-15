
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : BigCustomerDTO.java
*
* @Author : Administrator
*
* @Date : 2017年1月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

/**
* TODO description
* @author Administrator
* @date 2017年1月16日
*/

public class BigCustomerDTO {
        private String customerName;  
        
        private String contactorName;
        
        private String positionName;
        
        private String customerNo;
        
        private String fax;
        
        private String phone;
        
        private String mobile;
        
        private Integer wsAppType;
        
        private Integer customerKind;
        
        private Integer wsthreeType;
        
        private Date estimateApplyTime;
        
        private String projectRemark;
        
        private String dlrPrincipal;
        
        private String dlrPrincipalPhone;
        
        private Date submitTime;
        
        private Integer wsStatus;
        
        private Date auditingDate;
        
        private String wsAuditor;
        
        private String companyName;
        
        private String wsAuditingRemark;
        
        private List<BigCustomerCarDTO> invent;

        private String dmsFileIds;
        
        
        public String getDmsFileIds() {
            return dmsFileIds;
        }



        
        public void setDmsFileIds(String dmsFileIds) {
            this.dmsFileIds = dmsFileIds;
        }



        public String getCustomerNo() {
            return customerNo;
        }


        
        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }


        public String getCustomerName() {
            return customerName;
        }

        
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        
        public String getContactorName() {
            return contactorName;
        }

        
        public void setContactorName(String contactorName) {
            this.contactorName = contactorName;
        }

        
        public String getPositionName() {
            return positionName;
        }

        
        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        
        public String getFax() {
            return fax;
        }

        
        public void setFax(String fax) {
            this.fax = fax;
        }

        
        public String getPhone() {
            return phone;
        }

        
        public void setPhone(String phone) {
            this.phone = phone;
        }

        
        public String getMobile() {
            return mobile;
        }

        
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        
        public Integer getWsAppType() {
            return wsAppType;
        }

        
        public void setWsAppType(Integer wsAppType) {
            this.wsAppType = wsAppType;
        }

        
        public Integer getCustomerKind() {
            return customerKind;
        }

        
        public void setCustomerKind(Integer customerKind) {
            this.customerKind = customerKind;
        }

        
        public Integer getWsthreeType() {
            return wsthreeType;
        }

        
        public void setWsthreeType(Integer wsthreeType) {
            this.wsthreeType = wsthreeType;
        }

        
        public Date getEstimateApplyTime() {
            return estimateApplyTime;
        }

        
        public void setEstimateApplyTime(Date estimateApplyTime) {
            this.estimateApplyTime = estimateApplyTime;
        }

        
        public String getProjectRemark() {
            return projectRemark;
        }

        
        public void setProjectRemark(String projectRemark) {
            this.projectRemark = projectRemark;
        }

        
        public String getDlrPrincipal() {
            return dlrPrincipal;
        }

        
        public void setDlrPrincipal(String dlrPrincipal) {
            this.dlrPrincipal = dlrPrincipal;
        }

        
        public String getDlrPrincipalPhone() {
            return dlrPrincipalPhone;
        }

        
        public void setDlrPrincipalPhone(String dlrPrincipalPhone) {
            this.dlrPrincipalPhone = dlrPrincipalPhone;
        }

        
        public Date getSubmitTime() {
            return submitTime;
        }

        
        public void setSubmitTime(Date submitTime) {
            this.submitTime = submitTime;
        }

        
        public Integer getWsStatus() {
            return wsStatus;
        }

        
        public void setWsStatus(Integer wsStatus) {
            this.wsStatus = wsStatus;
        }

        
        public Date getAuditingDate() {
            return auditingDate;
        }

        
        public void setAuditingDate(Date auditingDate) {
            this.auditingDate = auditingDate;
        }

        
        public String getWsAuditor() {
            return wsAuditor;
        }

        
        public void setWsAuditor(String wsAuditor) {
            this.wsAuditor = wsAuditor;
        }

        
        public String getCompanyName() {
            return companyName;
        }

        
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        
        public String getWsAuditingRemark() {
            return wsAuditingRemark;
        }

        
        public void setWsAuditingRemark(String wsAuditingRemark) {
            this.wsAuditingRemark = wsAuditingRemark;
        }


        
        public List<BigCustomerCarDTO> getInvent() {
            return invent;
        }


        
        public void setInvent(List<BigCustomerCarDTO> invent) {
            this.invent = invent;
        }

        
   
        
        
}
