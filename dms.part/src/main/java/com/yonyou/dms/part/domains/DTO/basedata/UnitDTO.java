package com.yonyou.dms.part.domains.DTO.basedata;

/**
 * 计量单位实体类
* TODO description
* @author yujiangheng
* @date 2017年3月31日
 */
public class UnitDTO {
   private String dealerCode;
   private String unitCode;
   private String unitName;
   private String oemTag;


@Override
public String toString() {
    return "UnitDTO [dealerCode=" + dealerCode + ", unitCode=" + unitCode + ", unitName=" + unitName + ", oemTag="
           + oemTag + "]";
}


public String getOemTag() {
    return oemTag;
}


public void setOemTag(String oemTag) {
    this.oemTag = oemTag;
}

public String getDealerCode() {
    return dealerCode;
}

public void setDealerCode(String dealerCode) {
    this.dealerCode = dealerCode;
}

public String getUnitCode() {
    return unitCode;
}

public void setUnitCode(String unitCode) {
    this.unitCode = unitCode;
}

public String getUnitName() {
    return unitName;
}

public void setUnitName(String unitName) {
    this.unitName = unitName;
}
}
