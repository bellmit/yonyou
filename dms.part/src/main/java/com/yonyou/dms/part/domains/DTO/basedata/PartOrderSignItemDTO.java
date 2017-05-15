
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderSignItemDTO.java
*
* @Author : zhongsw
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;



/**
* 货运单明细表
* @author zhongsw
* @date 2016年8月11日
*/

public class PartOrderSignItemDTO {
    
    private Long item_id;
    
    private String dealer_code;//经销商代码
    
    private Integer deliver_id;
    
    private String batch_sign;//批量签收
    
    private String oem_order_no;//OEM订单号
    
    private String part_no;//配件代码
    
    private String part_name;//配件名称
    
    private Double count;//订货数量
    
    private Double supply_qty;//供应数量
    
    private Double lj_sign_count;//累计签收数量
    
    private Double this_time_sign_count;//本次签收数量
    
    private Double in_quantity_have;//已入库数量
    
    private Double amount;//采购价
    
    private Double taxed_amount;//采购金额
    
    private String remark2;//子表备注
    
    
    
    
    


    public String getBatch_sign() {
        return batch_sign;
    }

    
    public void setBatch_sign(String batch_sign) {
        this.batch_sign = batch_sign;
    }

    
    public String getOem_order_no() {
        return oem_order_no;
    }

    
    public void setOem_order_no(String oem_order_no) {
        this.oem_order_no = oem_order_no;
    }

    
    public String getPart_no() {
        return part_no;
    }

    
    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    
    public String getPart_name() {
        return part_name;
    }

    
    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    
    public Double getCount() {
        return count;
    }

    
    public void setCount(Double count) {
        this.count = count;
    }

    
    public Double getSupply_qty() {
        return supply_qty;
    }

    
    public void setSupply_qty(Double supply_qty) {
        this.supply_qty = supply_qty;
    }

    
    public Double getLj_sign_count() {
        return lj_sign_count;
    }

    
    public void setLj_sign_count(Double lj_sign_count) {
        this.lj_sign_count = lj_sign_count;
    }

    
    public Double getThis_time_sign_count() {
        return this_time_sign_count;
    }

    
    public void setThis_time_sign_count(Double this_time_sign_count) {
        this.this_time_sign_count = this_time_sign_count;
    }

    
    public Double getIn_quantity_have() {
        return in_quantity_have;
    }

    
    public void setIn_quantity_have(Double in_quantity_have) {
        this.in_quantity_have = in_quantity_have;
    }

    
    public Double getAmount() {
        return amount;
    }

    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    
    public Double getTaxed_amount() {
        return taxed_amount;
    }

    
    public void setTaxed_amount(Double taxed_amount) {
        this.taxed_amount = taxed_amount;
    }

    
    public String getRemark() {
        return remark2;
    }

    
    public void setRemark(String remark) {
        this.remark2 = remark;
    }

    public String getDealer_code() {
        return dealer_code; 
    }


    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }


    public Integer getDeliver_id() {
        return deliver_id;
    }


    public void setDeliver_id(Integer deliver_id) {
        this.deliver_id = deliver_id;
    }


    public Long getItem_id() {
        return item_id;
    }


    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    

}
