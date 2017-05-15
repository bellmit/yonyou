
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceProjectDTO.java
*
* @Author : zhongsw
*
* @Date : 2016年9月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月9日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 服务项目定义字段
* 
* @author zhongsw
* @date 2016年9月9日
*/

public class ServiceProjectDTO {
    
    private Long service_id;//服务ID
    
    private String dealer_code;
    @Required
    private String service_code;//服务代码
    @Required
    private String service_name;//服务项目名称
    @Required
    private Integer service_type;//服务类型
   
    private Double directive_price;//销售指导价
    
    private Double cost_price;//成本单价
    
    private String remark;//备注


    public String getDealer_code() {
        return dealer_code;
    }

    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Integer getService_type() {
        return service_type;
    }

    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    public Double getDirective_price() {
        return directive_price;
    }

    public void setDirective_price(Double directive_price) {
        this.directive_price = directive_price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

}
