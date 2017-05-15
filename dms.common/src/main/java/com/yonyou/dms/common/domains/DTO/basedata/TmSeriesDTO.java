package com.yonyou.dms.common.domains.DTO.basedata;

public class TmSeriesDTO {

    
    private String brandCode;//		品牌代码	VARCHAR(30)
    private String seriesCode;//	车系代码	VARCHAR(30)
    private String seriesName;//	车系名称	VARCHAR(90)
    private Integer oemTag;//		OEM标志	NUMERIC(8)
 

//    public String toXMLString() {
//	return VOUtil.vo2Xml(this);
//    }


    public String getBrandCode() {
        return brandCode;
    }


    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }


    public Integer getOemTag() {
        return oemTag;
    }


    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }


    public String getSeriesCode() {
        return seriesCode;
    }


    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }


    public String getSeriesName() {
        return seriesName;
    }


    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }


}
