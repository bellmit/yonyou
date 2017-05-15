package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * 装潢项目类型  二级分类
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
public class SubDressTypeDTO {
    /**
     *  dealer_code 
     *  main_group_code 
     *  sub_group_code 
     *  sub_group_name 
     *  down_tag
     */
    private String dealerCode;
    private String mainGroupCode;
    private String subGroupCode;
    private String subGroupName;
    private Integer  downTag;
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getMainGroupCode() {
        return mainGroupCode;
    }
    
    public void setMainGroupCode(String mainGroupCode) {
        this.mainGroupCode = mainGroupCode;
    }
    
    public String getSubGroupCode() {
        return subGroupCode;
    }
    
    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }
    
    public String getSubGroupName() {
        return subGroupName;
    }
    
    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }
    
    public Integer getDownTag() {
        return downTag;
    }
    
    public void setDownTag(Integer downTag) {
        this.downTag = downTag;
    }
    
    
    
}
