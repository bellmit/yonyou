
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VehicleInspectDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.stockManage;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;

/**
 * 车辆验收DTO
 * 
 * @author zhanshiwei
 * @date 2016年12月6日
 */

public class VehicleInspectDTO {

    private Integer                 vehicleInspectId;

    private Long                    vsStockId;

    private String                  dealerCode;

    private String                  bussinessNo;

    private String                  vin;

    private Integer                 vbType;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date                    bussinessDate;

    private Integer                 inspectStatus;

    private String                  inspectPerson;

    private Integer                 marStatus;

    private String                  veInspectIds;

    // 文件上传的ID
    private String                  inspectDmsFileIds;

    private List<InspectPdiItemDTO> inspenctItem;

    private List<VinspectionMarDTO> inspectMarList;

    private String                  storageCode;

    private String                  storagePositionCode;

    public Long getVsStockId() {
        return vsStockId;
    }

    public void setVsStockId(Long vsStockId) {
        this.vsStockId = vsStockId;
    }

    public String getInspectDmsFileIds() {
        return inspectDmsFileIds;
    }

    public String getVeInspectIds() {
        return veInspectIds;
    }

    public void setVeInspectIds(String veInspectIds) {
        this.veInspectIds = veInspectIds;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStoragePositionCode() {
        return storagePositionCode;
    }

    public Integer getMarStatus() {
        return marStatus;
    }

    public void setMarStatus(Integer marStatus) {
        this.marStatus = marStatus;
    }

    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }

    public List<VinspectionMarDTO> getInspectMarList() {
        return inspectMarList;
    }

    public void setInspectMarList(List<VinspectionMarDTO> inspectMarList) {
        this.inspectMarList = inspectMarList;
    }

    public void setInspectDmsFileIds(String inspectDmsFileIds) {
        this.inspectDmsFileIds = inspectDmsFileIds;
    }

    public List<InspectPdiItemDTO> getInspenctItem() {
        return inspenctItem;
    }

    public void setInspenctItem(List<InspectPdiItemDTO> inspenctItem) {
        this.inspenctItem = inspenctItem;
    }

    public Integer getVehicleInspectId() {
        return vehicleInspectId;
    }

    public void setVehicleInspectId(Integer vehicleInspectId) {
        this.vehicleInspectId = vehicleInspectId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getBussinessNo() {
        return bussinessNo;
    }

    public void setBussinessNo(String bussinessNo) {
        this.bussinessNo = bussinessNo == null ? null : bussinessNo.trim();
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin == null ? null : vin.trim();
    }

    public Integer getVbType() {
        return vbType;
    }

    public void setVbType(Integer vbType) {
        this.vbType = vbType;
    }

    public Date getBussinessDate() {
        return bussinessDate;
    }

    public void setBussinessDate(Date bussinessDate) {
        this.bussinessDate = bussinessDate;
    }

    public Integer getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Integer inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectPerson() {
        return inspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        this.inspectPerson = inspectPerson == null ? null : inspectPerson.trim();
    }
}
