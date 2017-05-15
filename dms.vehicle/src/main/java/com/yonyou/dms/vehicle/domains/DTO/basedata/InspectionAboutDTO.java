
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectionAboutDTO.java
*
* @Author : yangjie
*
* @Date : 2017年1月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月22日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.List;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月22日
 */

public class InspectionAboutDTO {

    private String                 vin;

    private String                 seNo;

    private Integer                inspectionResult;

    private String                 inspector;

    private Integer                marStatus;

    private List<InspectionMarDTO> dms_tab;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSeNo() {
        return seNo;
    }

    public void setSeNo(String seNo) {
        this.seNo = seNo;
    }

    public Integer getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(Integer inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public Integer getMarStatus() {
        return marStatus;
    }

    public void setMarStatus(Integer marStatus) {
        this.marStatus = marStatus;
    }

    public List<InspectionMarDTO> getDms_tab() {
        return dms_tab;
    }

    public void setDms_tab(List<InspectionMarDTO> dms_tab) {
        this.dms_tab = dms_tab;
    }

}
