
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : TtPtDmsOrderDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月21日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.partOrder;

import java.util.Date;
import java.util.List;

/**
 * @author zhanshiwei
 * @date 2017年4月21日
 */

public class TtPtDmsOrderDTO {

    private Integer                       dKey;
    private Date                          submitTime;
    private Integer                       sendMode;
    private Long                          createBy;
    private String                        entityCode;
    private Integer                       ver;
    private Integer                       orderStatus;
    private Date                          createDate;
    private String                        lockUser;
    private String                        oemOrderNo;
    private Integer                       isUpload;
    private String                        customerCode;
    private Date                          orderDate;
    private String                        groupCode;
    private Integer                       itemCount;
    private Integer                       leadTime;
    private Date                          fillinTime;
    private String                        customerName;
    private Integer                       isValid;
    private Date                          updateDate;
    private Date                          gkfwbDate;
    private Integer                       isSigned;
    private String                        contactorName;
    private Integer                       isLackGoods;
    private String                        orderNo;
    private String                        doe;
    private Double                        orderSum;
    private String                        pdc;
    private Long                          updateBy;
    private Integer                       isAchieve;
    private Integer                       oemTag;
    private Integer                       mainOrderType;
    private Integer                       partOrderType;
    private String                        remark;
    private Integer                       isBo;
    private String                        roNo;
    private String                        storageCode;
    private String                        elecCode;         // 电子编码
    private String                        mechCode;         // 机械代码
    private String                        keyCode;          // 锁芯机械码/CD机序列号
    private String                        change;           // 是否在未授权店私自更换
    private String                        emerg;            // 是否通过应急启动
    private String                        vin;              // VIN码
    private String                        ownerName;        // 车主姓名
    private String                        mobile;           // 联系电话
    private String                        license;          // 车牌号
    private String                        sheetNo;          // 三包缺料单
    private String                        isMopOrder;       // 否MOP拆单
    private String                        codeOrderOneId;   // CODE订单上传附件一
    private String                        codeOrderOneUrl;
    private String                        codeOrderTwoId;   // CODE订单上传附件二
    private String                        codeOrderTwoUrl;
    private String                        codeOrderThreeId; // CODE订单上传附件三
    private String                        codeOrderThreeUrl;
    private String                        sapOrderNo;       // SAP订单号
    private Date                          sendSapDate;      // 上报SAP时间 YYYY-MM-DD HH:mm
    private String                        status;
    private List<TtPtDmsOrderItemDTO>     partOrderItem;
    private List<ListTtPtDmsOrderItemDTO> ptpartOrder;

    public List<ListTtPtDmsOrderItemDTO> getPtpartOrder() {
        return ptpartOrder;
    }

    public void setPtpartOrder(List<ListTtPtDmsOrderItemDTO> ptpartOrder) {
        this.ptpartOrder = ptpartOrder;
    }

    private String orderItems;

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public List<TtPtDmsOrderItemDTO> getPartOrderItem() {
        return partOrderItem;
    }

    public void setPartOrderItem(List<TtPtDmsOrderItemDTO> partOrderItem) {
        this.partOrderItem = partOrderItem;
    }

    public Integer getdKey() {
        return dKey;
    }

    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSapOrderNo() {
        return sapOrderNo;
    }

    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    public Date getSendSapDate() {
        return sendSapDate;
    }

    public void setSendSapDate(Date sendSapDate) {
        this.sendSapDate = sendSapDate;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getCodeOrderOneId() {
        return codeOrderOneId;
    }

    public void setCodeOrderOneId(String codeOrderOneId) {
        this.codeOrderOneId = codeOrderOneId;
    }

    public String getCodeOrderOneUrl() {
        return codeOrderOneUrl;
    }

    public void setCodeOrderOneUrl(String codeOrderOneUrl) {
        this.codeOrderOneUrl = codeOrderOneUrl;
    }

    public String getCodeOrderThreeId() {
        return codeOrderThreeId;
    }

    public void setCodeOrderThreeId(String codeOrderThreeId) {
        this.codeOrderThreeId = codeOrderThreeId;
    }

    public String getCodeOrderThreeUrl() {
        return codeOrderThreeUrl;
    }

    public void setCodeOrderThreeUrl(String codeOrderThreeUrl) {
        this.codeOrderThreeUrl = codeOrderThreeUrl;
    }

    public String getCodeOrderTwoId() {
        return codeOrderTwoId;
    }

    public void setCodeOrderTwoId(String codeOrderTwoId) {
        this.codeOrderTwoId = codeOrderTwoId;
    }

    public String getCodeOrderTwoUrl() {
        return codeOrderTwoUrl;
    }

    public void setCodeOrderTwoUrl(String codeOrderTwoUrl) {
        this.codeOrderTwoUrl = codeOrderTwoUrl;
    }

    public String getElecCode() {
        return elecCode;
    }

    public void setElecCode(String elecCode) {
        this.elecCode = elecCode;
    }

    public String getEmerg() {
        return emerg;
    }

    public void setEmerg(String emerg) {
        this.emerg = emerg;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMechCode() {
        return mechCode;
    }

    public void setMechCode(String mechCode) {
        this.mechCode = mechCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setDKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getDKey() {
        return this.dKey;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() {
        return this.submitTime;
    }

    public void setSendMode(Integer sendMode) {
        this.sendMode = sendMode;
    }

    public Integer getSendMode() {
        return this.sendMode;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() {
        return this.orderStatus;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public String getLockUser() {
        return this.lockUser;
    }

    public void setOemOrderNo(String oemOrderNo) {
        this.oemOrderNo = oemOrderNo;
    }

    public String getOemOrderNo() {
        return this.oemOrderNo;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public Integer getIsUpload() {
        return this.isUpload;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Integer getLeadTime() {
        return this.leadTime;
    }

    public void setFillinTime(Date fillinTime) {
        this.fillinTime = fillinTime;
    }

    public Date getFillinTime() {
        return this.fillinTime;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setGkfwbDate(Date gkfwbDate) {
        this.gkfwbDate = gkfwbDate;
    }

    public Date getGkfwbDate() {
        return this.gkfwbDate;
    }

    public void setIsSigned(Integer isSigned) {
        this.isSigned = isSigned;
    }

    public Integer getIsSigned() {
        return this.isSigned;
    }

    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public String getContactorName() {
        return this.contactorName;
    }

    public void setIsLackGoods(Integer isLackGoods) {
        this.isLackGoods = isLackGoods;
    }

    public Integer getIsLackGoods() {
        return this.isLackGoods;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setDoe(String doe) {
        this.doe = doe;
    }

    public String getDoe() {
        return this.doe;
    }

    public void setOrderSum(Double orderSum) {
        this.orderSum = orderSum;
    }

    public Double getOrderSum() {
        return this.orderSum;
    }

    public void setPdc(String pdc) {
        this.pdc = pdc;
    }

    public String getPdc() {
        return this.pdc;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setIsAchieve(Integer isAchieve) {
        this.isAchieve = isAchieve;
    }

    public Integer getIsAchieve() {
        return this.isAchieve;
    }

    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }

    public Integer getOemTag() {
        return this.oemTag;
    }

    public void setMainOrderType(Integer mainOrderType) {
        this.mainOrderType = mainOrderType;
    }

    public Integer getMainOrderType() {
        return this.mainOrderType;
    }

    public void setPartOrderType(Integer partOrderType) {
        this.partOrderType = partOrderType;
    }

    public Integer getPartOrderType() {
        return this.partOrderType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public Integer getIsBo() {
        return isBo;
    }

    public void setIsBo(Integer isBo) {
        this.isBo = isBo;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getIsMopOrder() {
        return isMopOrder;
    }

    public void setIsMopOrder(String isMopOrder) {
        this.isMopOrder = isMopOrder;
    }
}
