package com.yonyou.dms.manage.domains.DTO.basedata;


public class UserMappingDto {
    
    private String employeeNo;
    private String userCode;//员工代码
    private String targetSystem;//映射系统
    private String targetUser;//映射账号
    private String targetPassword;//映射账号密码
    private String targetRole;//映射账号权限
    
    public String getTargetSystem() {
        return targetSystem;
    }
    
    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }
    
    public String getTargetUser() {
        return targetUser;
    }
    
    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }
    
   
    public String getTargetRole() {
        return targetRole;
    }
    
    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }
    
    

}
