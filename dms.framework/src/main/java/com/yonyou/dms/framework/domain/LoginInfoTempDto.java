package com.yonyou.dms.framework.domain;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 临时记录loginInfo信息的dto
 * 
 * */
@Component
@Scope("session")
public class LoginInfoTempDto {
    private String                           dealerCode;
    private String                           userAccount;
    private Long                             dealerId;
    private String                           dealerName;
    private String                           dealerShortName;
    private String                           userName;
    private String                           mobile;
    private String                           orgCode;
    private String                           orgName;
    private Long                             userId;
    private Locale                           locale;
    private String                           employeeNo;
    private Integer                          gender;
    private Long                             orgId;
    private String                           userCode;
    private String                           mappingAccount;//用户的映射账号

    private Map                              receptionMaintain;// 接待
    private Map                              partsOption;      // 配件
    private Map                              settleAccounts;   // 结算
    private Map                              systemAttention;  // 系统
    private List                             customerOption;   // 客户
    private List                             vehicleOption;    // 整车

    // 设置可以管理的仓库
    private String                           canAccessStores;
    private String                           carLoadDepot;     // 整车仓库权限
    private String                           purchaseDepot;    // 配件仓库权限
    private Map                              repair;           // 维修权限
    private Map                              purchase;         // 配件权限
    private List                             preferentialMode; // 优惠模式权限

    private Map<String, Map<String, Object>> ctlDataAction;    // 用戶菜單控制

    // add xiawei by 2017-02-07 DCS登录用户所需字段

    private Integer                          sysType;          // 用来区分是登录0 DMS端客户还是 1 DCS端客户

    /**
     * 用户所属公司ID
     */
    private Long                             companyId;

    /**
     * 用户类型
     */
    private Integer                          userType;
    // 组织类型
    private Integer                          orgType;
    // 车厂用户业务范围
    private String                           oemPositionArea;
    /**
     * 用户当前登录的职位ID
     */
    private Long                             poseId;

    // 职位类型
    private Integer                          poseType;
    // 职位业务类别
    private Integer                          poseBusType;

    // 经销商OEM_COMPANY_ID
    private String                           oemCompanyId;

    // 经销商DEALER_ORG_ID
    private String                           dealerOrgId;

    // 组织DUTY_TYPE
    private String                           dutyType;

    // 上级组织PARENT_ORG_ID
    private String                           parentOrgId;

    // 公司code
    private String                           companyCode;
    // 下端登录id
    private String                           rpcUserId;
    // 接口请求标志
    private String                           rpcFlag;
    // 接口用户
    private String                           rpcName;
    // 经销商-车系
    private String                           dealerSeriesIDs;
    // 职位-车系
    private String                           poseSeriesIDs;
    
    //是否显示切换职位
    private Integer                          isShowChangePose;//1显示，0隐藏
    
    //切换职位时判断刷新Token
    private Integer                          isRefreshToken;//1不刷新，0刷新
    
    
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getIsShowChangePose() {
        return isShowChangePose;
    }

    public void setIsShowChangePose(Integer isShowChangePose) {
        this.isShowChangePose = isShowChangePose;
    }

    public Integer getIsRefreshToken() {
        return isRefreshToken;
    }

    public void setIsRefreshToken(Integer isRefreshToken) {
        this.isRefreshToken = isRefreshToken;
    }

    //用户角色新增删除临时记录字段
    private String roleIds;
    
    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * 当前登录用户所在部门，以及下级部门
     */
    private String                           bxjDept;

    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    public String getBxjDept() {
        return bxjDept;
    }

    public void setBxjDept(String bxjDept) {
        this.bxjDept = bxjDept;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOemPositionArea() {
        return oemPositionArea;
    }

    public void setOemPositionArea(String oemPositionArea) {
        this.oemPositionArea = oemPositionArea;
    }

    public Long getPoseId() {
        return poseId;
    }

    public void setPoseId(Long poseId) {
        this.poseId = poseId;
    }

    public Integer getPoseType() {
        return poseType;
    }

    public void setPoseType(Integer poseType) {
        this.poseType = poseType;
    }

    public Integer getPoseBusType() {
        return poseBusType;
    }

    public void setPoseBusType(Integer poseBusType) {
        this.poseBusType = poseBusType;
    }

    public String getOemCompanyId() {
        return oemCompanyId;
    }

    public void setOemCompanyId(String oemCompanyId) {
        this.oemCompanyId = oemCompanyId;
    }

    public String getDealerOrgId() {
        return dealerOrgId;
    }

    public void setDealerOrgId(String dealerOrgId) {
        this.dealerOrgId = dealerOrgId;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getRpcUserId() {
        return rpcUserId;
    }

    public void setRpcUserId(String rpcUserId) {
        this.rpcUserId = rpcUserId;
    }

    public String getRpcFlag() {
        return rpcFlag;
    }

    public void setRpcFlag(String rpcFlag) {
        this.rpcFlag = rpcFlag;
    }

    public String getRpcName() {
        return rpcName;
    }

    public void setRpcName(String rpcName) {
        this.rpcName = rpcName;
    }

    public String getDealerSeriesIDs() {
        return dealerSeriesIDs;
    }

    public void setDealerSeriesIDs(String dealerSeriesIDs) {
        this.dealerSeriesIDs = dealerSeriesIDs;
    }

    public String getPoseSeriesIDs() {
        return poseSeriesIDs;
    }

    public void setPoseSeriesIDs(String poseSeriesIDs) {
        this.poseSeriesIDs = poseSeriesIDs;
    }

    public String getCanAccessStores() {
        return canAccessStores;
    }

    public void setCanAccessStores(String canAccessStores) {
        this.canAccessStores = canAccessStores;
    }

    public String getCarLoadDepot() {
        return carLoadDepot;
    }

    public void setCarLoadDepot(String carLoadDepot) {
        this.carLoadDepot = carLoadDepot;
    }

    public String getPurchaseDepot() {
        return purchaseDepot;
    }

    public void setPurchaseDepot(String purchaseDepot) {
        this.purchaseDepot = purchaseDepot;
    }

    public Map getRepair() {
        return repair;
    }

    public void setRepair(Map repair) {
        this.repair = repair;
    }

    public Map getPurchase() {
        return purchase;
    }

    public void setPurchase(Map purchase) {
        this.purchase = purchase;
    }

    public List getPreferentialMode() {
        return preferentialMode;
    }

    public void setPreferentialMode(List preferentialMode) {
        this.preferentialMode = preferentialMode;
    }

    public Map getReceptionMaintain() {
        return receptionMaintain;
    }

    public void setReceptionMaintain(Map receptionMaintain) {
        this.receptionMaintain = receptionMaintain;
    }

    public Map getPartsOption() {
        return partsOption;
    }

    public void setPartsOption(Map partsOption) {
        this.partsOption = partsOption;
    }

    public Map getSettleAccounts() {
        return settleAccounts;
    }

    public void setSettleAccounts(Map settleAccounts) {
        this.settleAccounts = settleAccounts;
    }

    public Map getSystemAttention() {
        return systemAttention;
    }

    public void setSystemAttention(Map systemAttention) {
        this.systemAttention = systemAttention;
    }

    public List getCustomerOption() {
        return customerOption;
    }

    public void setCustomerOption(List customerOption) {
        this.customerOption = customerOption;
    }

    public List getVehicleOption() {
        return vehicleOption;
    }

    public void setVehicleOption(List vehicleOption) {
        this.vehicleOption = vehicleOption;
    }

    /**
     * method:module:url,分隔符使用：FrameworkConstants.ACL_RESOUCCE_SPLIT
     */

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userName) {
        this.userAccount = userName;
    }

    @Override
    public String toString() {
        return "LoginInfoDto [dealerCode=" + dealerCode + ", userName=" + userAccount + "]";
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerShortName() {
        return dealerShortName;
    }

    public void setDealerShortName(String dealerShortName) {
        this.dealerShortName = dealerShortName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Map<String, Map<String, Object>> getCtlDataAction() {
        return ctlDataAction;
    }

    public void setCtlDataAction(Map<String, Map<String, Object>> ctlDataAction) {
        this.ctlDataAction = ctlDataAction;
    }

    public String getMappingAccount() {
        return mappingAccount;
    }

    public void setMappingAccount(String mappingAccount) {
        this.mappingAccount = mappingAccount;
    }
}
