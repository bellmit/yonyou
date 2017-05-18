
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : RolePDto.java
*
* @Author : yll
*
* @Date : 2016年8月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月8日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata.role;

import java.util.ArrayList;
import java.util.List;

/**
 * 接收角色对应权限的dto
 * 
 * @author yll
 * @date 2016年8月8日
 */

public class RolePDto {

    private Integer      roleId;
    private String       dealerCode;
    private String       roleCode;
    private String       roleName;
    private String       treeCtrlCode;

    private List<String> favorableModels;                        // 优惠模式
    private List<String> maintain;                               // 维修
    private List<String> accessories;                            // 配件
    private List<String> vehicleWarehouse;                       // 整车仓库
    private List<String> accessoriesWarehouse;                   // 配件仓库
    // private List<String> warehouse;//仓库(现已不用)
    private String       text;
    private String       nowRole;
    private String       nowTree;
    private String       treeMenuAction;
    private String       treeMenuRange;                          ///

    //////////

    private Long         employeeId;                             // 员工id
    private Long         userId;                             // 员工id
    private String       userCode;                               // 账号
    private String       employeeName;                           // 姓名
    private String       password;
    private String       userStatus;                             // 有效无效
    private List<String> employeeRoles = new ArrayList<String>();
    private String       employeeNo;
    
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String       orgCode;
    private String       isServiceAdvisor;
    private String       isConsultant;

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

    public String getText() {
        return text;
    }

    public String getTreeMenuAction() {
        return treeMenuAction;
    }

    public void setTreeMenuAction(String treeMenuAction) {
        this.treeMenuAction = treeMenuAction;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getFavorableModels() {
        return favorableModels;
    }

    public void setFavorableModels(List<String> favorableModels) {
        this.favorableModels = favorableModels;
    }

    public List<String> getMaintain() {
        return maintain;
    }

    public void setMaintain(List<String> maintain) {
        this.maintain = maintain;
    }

    public List<String> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<String> accessories) {
        this.accessories = accessories;
    }

    public String getNowRole() {
        return nowRole;
    }

    public void setNowRole(String nowRole) {
        this.nowRole = nowRole;
    }

    public String getNowTree() {
        return nowTree;
    }

    public void setNowTree(String nowTree) {
        this.nowTree = nowTree;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getTreeMenuRange() {
        return treeMenuRange;
    }

    public void setTreeMenuRange(String treeMenuRange) {
        this.treeMenuRange = treeMenuRange;
    }

    public List<String> getEmployeeRoles() {
        return employeeRoles;
    }

    public void setEmployeeRoles(List<String> employeeRoles) {
        this.employeeRoles = employeeRoles;
    }

    public List<String> getVehicleWarehouse() {
        return vehicleWarehouse;
    }

    public void setVehicleWarehouse(List<String> vehicleWarehouse) {
        this.vehicleWarehouse = vehicleWarehouse;
    }

    public List<String> getAccessoriesWarehouse() {
        return accessoriesWarehouse;
    }

    public void setAccessoriesWarehouse(List<String> accessoriesWarehouse) {
        this.accessoriesWarehouse = accessoriesWarehouse;
    }

    public String getTreeCtrlCode() {
        return treeCtrlCode;
    }

    public void setTreeCtrlCode(String treeCtrlCode) {
        this.treeCtrlCode = treeCtrlCode;
    }

}
