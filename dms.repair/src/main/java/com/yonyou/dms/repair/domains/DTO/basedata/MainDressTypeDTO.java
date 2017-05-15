package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * 装潢项目类型 主分类
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
public class MainDressTypeDTO {
    /**
     * dealer_code 
     * main_group_code 
     *  main_group_name 
     *  down_tag
     */
    private String dealerCode;
    private String mainGroupCode;
    private String mainGroupName;
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
    
    public String getMainGroupName() {
        return mainGroupName;
    }
    
    public void setMainGroupName(String mainGroupName) {
        this.mainGroupName = mainGroupName;
    }
    
    public Integer getDownTag() {
        return downTag;
    }
    
    public void setDownTag(Integer downTag) {
        this.downTag = downTag;
    }
}
