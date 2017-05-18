/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : EmployeeDto.java
*
* @Author : jcsi
*
* @Date : 2016年7月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月8日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateSerializer;
import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.IDNumber;
import com.yonyou.dms.function.utils.validate.define.Phone;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
 * 员工信息
 * 
 * @author jcsi
 * @date 2016年7月6日
 */
public class EmployeeDto implements Serializable {

    /**
     * 
     */
    private static final long         serialVersionUID = 1L;

    @Required
    private String                    employeeNo;                                // 员工编号
    private String                    employeeids;
   

    @Required
    private String                    employeeName;                              // 员工姓名

    private String                    orgCode;                                   // ORG_CODE 部门 (废弃)

    @Required
    private int                       organizationId;                            // 部门

    private String                    positionCode;                              // POSITION_CODE 职务

    private Long                      gender;                                    // GENDER 性别
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    private Date                      birthday;                                  // BIRTHDAY 出生日期

   
    private String                    certificateId;                             // CERTIFICATE_ID 身份证号

    @Phone
    private String                    mobile;                                    // MOBILE 手机

    private String                    phone;                                     // PHONE 电话

    @Email
    private String                    eMail;                                     // E_MAIL

    private String                    address;                                   // ADDRESS //地址

    @ZipCode
    private String                    zipCode;                                   // ZIP_CODE 邮编

    private Long                      isOnjob;                                   // 在职状态
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    private Date                      dimissionDate;                             // DIMISSION_DATE 离职日期
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    private Date                      foundDate;                                 // 建档日期

    private String                    workerTypeCode;                            // WORKER_TYPE_CODE 工种

    private Long                      technicianGrade;                           // TECHNICIAN_GRADE 技师等级

    private String                    defaultPosition;                           // DEFAULT_POSITION 主维修工位

    private String                    workgroupCode;                             // 班组

    private List<String>              employeeRoles    = new ArrayList<String>();

    private List<EmployeeTrainingDto> listEmpTrain;

    public List<EmployeeTrainingDto> getListEmpTrain() {
        return listEmpTrain;
    }

    public void setListEmpTrain(List<EmployeeTrainingDto> listEmpTrain) {
        this.listEmpTrain = listEmpTrain;
    }

    private String  fromEntity;
    private String  creditName;
    private String  entityCode;
    private Integer ver;
    private Date    createDate;
    private Integer downTag;
    private Integer isUpload;
    private String  labourPositionCode;
    private String  secLabourPositionCode;
    private Integer isTakePart;
    private String  training;
    private Integer isTestDriver;
    private Long    updateBy;
    private String  specialty;
    private String  industryYears;
    private Integer isTechnician;
    private Integer isChecker;
    private Long    createBy;
    private Integer isConsultant;

    private Float   avoirdupois;
    private Integer isValid;
    private Integer isServiceAdvisor;
    private Date    updateDate;
    private String  nation;
    private String  resume;
    private String  workYears;
    private Float   stature;
    private Float   labourFactor;
    private Integer isTracer;
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    private Date    entryDate;
    private Integer eduLevel;
    private String  serviceBrand;
    private Integer isMaintainAdvisor;
    private Integer isInsuranceAdvisor;
    private Integer isDcrcAdvisor;
    private Integer isUpholsterAdvisor;
    private Integer isInsurationAdvisor;
    private String  fax;
    private String  dispatchCode;
    private Integer isMajorRepairer;
    private Integer isDispatcher;
    private String  techLevelCode;
    private String  orderCode;
    private Integer isDefaultManager;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getFromEntity() {
        return fromEntity;
    }

    public void setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDownTag() {
        return downTag;
    }

    public void setDownTag(Integer downTag) {
        this.downTag = downTag;
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public String getLabourPositionCode() {
        return labourPositionCode;
    }

    public void setLabourPositionCode(String labourPositionCode) {
        this.labourPositionCode = labourPositionCode;
    }

    public String getSecLabourPositionCode() {
        return secLabourPositionCode;
    }

    public void setSecLabourPositionCode(String secLabourPositionCode) {
        this.secLabourPositionCode = secLabourPositionCode;
    }

    public Integer getIsTakePart() {
        return isTakePart;
    }

    public void setIsTakePart(Integer isTakePart) {
        this.isTakePart = isTakePart;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public Integer getIsTestDriver() {
        return isTestDriver;
    }

    public void setIsTestDriver(Integer isTestDriver) {
        this.isTestDriver = isTestDriver;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getIndustryYears() {
        return industryYears;
    }

    public void setIndustryYears(String industryYears) {
        this.industryYears = industryYears;
    }

    public Integer getIsTechnician() {
        return isTechnician;
    }

    public void setIsTechnician(Integer isTechnician) {
        this.isTechnician = isTechnician;
    }

    public Integer getIsChecker() {
        return isChecker;
    }

    public void setIsChecker(Integer isChecker) {
        this.isChecker = isChecker;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Integer getIsConsultant() {
        return isConsultant;
    }

    public void setIsConsultant(Integer isConsultant) {
        this.isConsultant = isConsultant;
    }

    public Float getAvoirdupois() {
        return avoirdupois;
    }

    public void setAvoirdupois(Float avoirdupois) {
        this.avoirdupois = avoirdupois;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsServiceAdvisor() {
        return isServiceAdvisor;
    }

    public void setIsServiceAdvisor(Integer isServiceAdvisor) {
        this.isServiceAdvisor = isServiceAdvisor;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public Float getStature() {
        return stature;
    }

    public void setStature(Float stature) {
        this.stature = stature;
    }

    public Float getLabourFactor() {
        return labourFactor;
    }

    public void setLabourFactor(Float labourFactor) {
        this.labourFactor = labourFactor;
    }

    public Integer getIsTracer() {
        return isTracer;
    }

    public void setIsTracer(Integer isTracer) {
        this.isTracer = isTracer;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getServiceBrand() {
        return serviceBrand;
    }

    public void setServiceBrand(String serviceBrand) {
        this.serviceBrand = serviceBrand;
    }

    public Integer getIsMaintainAdvisor() {
        return isMaintainAdvisor;
    }

    public void setIsMaintainAdvisor(Integer isMaintainAdvisor) {
        this.isMaintainAdvisor = isMaintainAdvisor;
    }

    public Integer getIsInsuranceAdvisor() {
        return isInsuranceAdvisor;
    }

    public void setIsInsuranceAdvisor(Integer isInsuranceAdvisor) {
        this.isInsuranceAdvisor = isInsuranceAdvisor;
    }

    public Integer getIsDcrcAdvisor() {
        return isDcrcAdvisor;
    }

    public void setIsDcrcAdvisor(Integer isDcrcAdvisor) {
        this.isDcrcAdvisor = isDcrcAdvisor;
    }

    public Integer getIsUpholsterAdvisor() {
        return isUpholsterAdvisor;
    }

    public void setIsUpholsterAdvisor(Integer isUpholsterAdvisor) {
        this.isUpholsterAdvisor = isUpholsterAdvisor;
    }

    public Integer getIsInsurationAdvisor() {
        return isInsurationAdvisor;
    }

    public void setIsInsurationAdvisor(Integer isInsurationAdvisor) {
        this.isInsurationAdvisor = isInsurationAdvisor;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDispatchCode() {
        return dispatchCode;
    }

    public void setDispatchCode(String dispatchCode) {
        this.dispatchCode = dispatchCode;
    }

    public Integer getIsMajorRepairer() {
        return isMajorRepairer;
    }

    public void setIsMajorRepairer(Integer isMajorRepairer) {
        this.isMajorRepairer = isMajorRepairer;
    }

    public Integer getIsDispatcher() {
        return isDispatcher;
    }

    public void setIsDispatcher(Integer isDispatcher) {
        this.isDispatcher = isDispatcher;
    }

    public String getTechLevelCode() {
        return techLevelCode;
    }

    public void setTechLevelCode(String techLevelCode) {
        this.techLevelCode = techLevelCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getIsDefaultManager() {
        return isDefaultManager;
    }

    public void setIsDefaultManager(Integer isDefaultManager) {
        this.isDefaultManager = isDefaultManager;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getWorkgroupCode() {
        return workgroupCode;
    }

    public void setWorkgroupCode(String workgroupCode) {
        this.workgroupCode = workgroupCode;
    }

    public List<String> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<String> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(Date dimissionDate) {
        this.dimissionDate = dimissionDate;
    }

    public String getWorkerTypeCode() {
        return workerTypeCode;
    }

    public void setWorkerTypeCode(String workerTypeCode) {
        this.workerTypeCode = workerTypeCode;
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getIsOnjob() {
        return isOnjob;
    }

    public void setIsOnjob(Long isOnjob) {
        this.isOnjob = isOnjob;
    }

    public Long getTechnicianGrade() {
        return technicianGrade;
    }

    public void setTechnicianGrade(Long technicianGrade) {
        this.technicianGrade = technicianGrade;
    }

    public String getDefaultPosition() {
        return defaultPosition;
    }

    public void setDefaultPosition(String defaultPosition) {
        this.defaultPosition = defaultPosition;
    }

    public String getEmployeeids() {
        return employeeids;
    }

    public void setEmployeeids(String employeeids) {
        this.employeeids = employeeids;
    }
}
