
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingRecordDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Phone;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 展厅接待DTO
 * 
 * @author zhanshiwei
 * @date 2016年8月31日
 */

public class VisitingRecordDTO {

    private Long    itemId;
    private Long    potentialCustomerId;
    @Length(max=11)
    private String  customerNo;
    @Required
    @Length(max=120)
    private String  customerName;

    private Integer gender;
    @Phone   
    
    private String  contactorPhone;
    
    private String contactorMobile;    

	private Integer visitType;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    visitTime;

    @Max(99)
    private Double  visitor;

    private Integer intentLevel;

    private Integer cusSource;

    private Integer mediaType;

    private Integer isFirstTehShop;//是否首次到店

	private Integer isSecondTehShop;//是否二次到店
	
	@Max(99)
	private Double visitTimes;//新增字段“到店次数”

    public Long getPotentialCustomerId() {
        return potentialCustomerId;
    }

    public void setPotentialCustomerId(Long potentialCustomerId) {
        this.potentialCustomerId = potentialCustomerId;
    }

    private Integer                 isTestDrive;
    @Length(max=120)
    private String                  scene;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date                    leaveTime;
    private List<VisitingIntentDTO> intentList;
    
    private String soldBy;
    
    private String soldBy2;
    
    private Long isStepForwardGreeting;
    
    private Integer isFirstVisit;//是否首次客流
    
    private String contactorName;
    
    private Integer complaintResult;//回访结果
    
    private Long isLeavingFarewell;//是否离店远送
    
    private Long isRecording;//是否录音
    
    private String    noList;//隐藏字段 ，用于再分配
    
    private Integer isValid;//是否有效
    
    private String remark;//备注
    
    private String contactTime;//接触时长
    
    private Long mediaDetail; //渠道细分
    
    private String betterVisitTime; //拜访时间    
    
    private String campaignCode;
    
    public Integer getIsValid() {
        return isValid;
    }

    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public String getContactTime() {
        return contactTime;
    }

    
    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    public String getNoList() {
        return noList;
    }

    
    public void setNoList(String noList) {
        this.noList = noList;
    }

    public Long getIsStepForwardGreeting() {
		return isStepForwardGreeting;
	}

	public void setIsStepForwardGreeting(Long isStepForwardGreeting) {
		this.isStepForwardGreeting = isStepForwardGreeting;
	}

    public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getSoldBy() {
        return soldBy;
    }

    
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public String getSoldBy2() {
		return soldBy2;
	}

	public void setSoldBy2(String soldBy2) {
		this.soldBy2 = soldBy2;
	}

	public List<VisitingIntentDTO> getIntentList() {
        return intentList;
    }

    public void setIntentList(List<VisitingIntentDTO> intentList) {
        this.intentList = intentList;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}
	
	public String getContactorMobile() {
		return contactorMobile;
	}

	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}

	public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Double getVisitor() {
        return visitor;
    }

    public void setVisitor(Double visitor) {
        this.visitor = visitor;
    }

    public Integer getIntentLevel() {
        return intentLevel;
    }

    public void setIntentLevel(Integer intentLevel) {
        this.intentLevel = intentLevel;
    }

    public Integer getCusSource() {
        return cusSource;
    }

    public void setCusSource(Integer cusSource) {
        this.cusSource = cusSource;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getIsFirstTehShop() {
		return isFirstTehShop;
	}

	public void setIsFirstTehShop(Integer isFirstTehShop) {
		this.isFirstTehShop = isFirstTehShop;
	}

	public Integer getIsSecondTehShop() {
		return isSecondTehShop;
	}

	public void setIsSecondTehShop(Integer isSecondTehShop) {
		this.isSecondTehShop = isSecondTehShop;
	}

	public Integer getIsTestDrive() {
        return isTestDrive;
    }

    public void setIsTestDrive(Integer isTestDrive) {
        this.isTestDrive = isTestDrive;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

	public Integer getIsFirstVisit() {
		return isFirstVisit;
	}

	public void setIsFirstVisit(Integer isFirstVisit) {
		this.isFirstVisit = isFirstVisit;
	}

	public String getContactorName() {
		return contactorName;
	}

	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}

	public Integer getComplaintResult() {
		return complaintResult;
	}

	public void setComplaintResult(Integer complaintResult) {
		this.complaintResult = complaintResult;
	}

	public Long getIsLeavingFarewell() {
		return isLeavingFarewell;
	}

	public void setIsLeavingFarewell(Long isLeavingFarewell) {
		this.isLeavingFarewell = isLeavingFarewell;
	}

	public Long getIsRecording() {
		return isRecording;
	}

	public void setIsRecording(Long isRecording) {
		this.isRecording = isRecording;
	}	

	public Long getMediaDetail() {
		return mediaDetail;
	}

	public void setMediaDetail(Long mediaDetail) {
		this.mediaDetail = mediaDetail;
	}

	public String getBetterVisitTime() {
		return betterVisitTime;
	}

	public void setBetterVisitTime(String betterVisitTime) {
		this.betterVisitTime = betterVisitTime;
	}
	
	public Double getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(Double visitTimes) {
		this.visitTimes = visitTimes;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	
	
}
