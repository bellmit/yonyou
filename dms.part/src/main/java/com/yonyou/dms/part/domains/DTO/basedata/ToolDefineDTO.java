package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

/**
 * 
* TODO description
* @author yujiangheng
* @date 2017年4月13日
 */
public class ToolDefineDTO {
    /**
     *  A.DEALER_CODE,A.VER,TOOL_SPELL, TOOL_CODE, TOOL_NAME, A.TOOL_TYPE_CODE, B.TOOL_TYPE_NAME, 
     *  a.UNIT_CODE UNIT_CODE,D.UNIT_NAME UNIT_NAME, 
          PRINCIPAL,C.EMPLOYEE_NAME AS PRINCIPAL_NAME, A.STOCK_QUANTITY,  POSITION,
           A.LEND_QUANTITY,BEGIN_DATE_VALIDITY,END_DATE_VALIDITY,A.DATE_VALIDITY, A.CAPITAL_ASSERTS_MANAGE_NO 
     */
    private String dealerCode;//厂商代码
    private String toolCode;//代码
    private String toolName;//名称
    private String toolSpell;//拼音
    private String toolTypeCode; //工具类别代码   --TOOL_TYPE_NAME
    private String toolTypeName;//工具类别名称
    private String unitCode; //计量单位代码   --UNIT_NAME
    private String unitName;//计量单位名称
    private String position;//存放位置
    
    private Float lendQuantity;//借进数量
    private Float stockQuantity;//库存数量
    
    private String principal;//负责人代码    -- PRINCIPAL_NAME
    private String principalName;//负责人名称
    
    private Float dateValidity;//使用年限
    
    private String capitalAssertsManageNo;//固定资产编号
    
    private Date beginDateValidity;
    private Date endDateValidity;
    private Float ver;
    
    private String flag;
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }




    @Override
    public String toString() {
        return "ToolDefineDTO [dealerCode=" + dealerCode + ", toolCode=" + toolCode + ", toolName=" + toolName
               + ", toolSpell=" + toolSpell + ", toolTypeCode=" + toolTypeCode + ", toolTypeName=" + toolTypeName
               + ", unitCode=" + unitCode + ", unitName=" + unitName + ", position=" + position + ", lendQuantity="
               + lendQuantity + ", stockQuantity=" + stockQuantity + ", principal=" + principal + ", principalName="
               + principalName + ", dateValidity=" + dateValidity + ", capitalAssertsManageNo=" + capitalAssertsManageNo
               + ", beginDateValidity=" + beginDateValidity + ", endDateValidity=" + endDateValidity + ", ver=" + ver
               + ", flag=" + flag + "]";
    }
    public String getToolTypeName() {
        return toolTypeName;
    }

    
    public void setToolTypeName(String toolTypeName) {
        this.toolTypeName = toolTypeName;
    }

    
    public String getUnitName() {
        return unitName;
    }

    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    
    public String getPrincipalName() {
        return principalName;
    }

    
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    
    public Date getBeginDateValidity() {
        return beginDateValidity;
    }

    
    public void setBeginDateValidity(Date beginDateValidity) {
        this.beginDateValidity = beginDateValidity;
    }

    
    public Date getEndDateValidity() {
        return endDateValidity;
    }

    
    public void setEndDateValidity(Date endDateValidity) {
        this.endDateValidity = endDateValidity;
    }

    
    public Float getVer() {
        return ver;
    }

    
    public void setVer(Float ver) {
        this.ver = ver;
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getToolCode() {
        return toolCode;
    }
    
    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
    
    public String getToolName() {
        return toolName;
    }
    
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
    
    public String getToolSpell() {
        return toolSpell;
    }
    
    public void setToolSpell(String toolSpell) {
        this.toolSpell = toolSpell;
    }
    
    public String getToolTypeCode() {
        return toolTypeCode;
    }
    
    public void setToolTypeCode(String toolTypeCode) {
        this.toolTypeCode = toolTypeCode;
    }
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public Float getLendQuantity() {
        return lendQuantity;
    }
    
    public void setLendQuantity(Float lendQuantity) {
        this.lendQuantity = lendQuantity;
    }
    
    public Float getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public String getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    
    
    


    
    public Float getDateValidity() {
        return dateValidity;
    }


    
    public void setDateValidity(Float dateValidity) {
        this.dateValidity = dateValidity;
    }


    public String getCapitalAssertsManageNo() {
        return capitalAssertsManageNo;
    }
    
    public void setCapitalAssertsManageNo(String capitalAssertsManageNo) {
        this.capitalAssertsManageNo = capitalAssertsManageNo;
    }
  
    
    
}
