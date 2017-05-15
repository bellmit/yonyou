
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockInItemsDTO.java
*
* @Author : yangjie
*
* @Date : 2017年1月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月19日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.DTO.stockManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO description
 * 
 * @author yangjie
 * @Date 2017年1月19日
 */

public class StockInItemsDTO{

    private Integer is_finished;

    private Integer is_upload;

    private Integer inspection_result;

    private String  vin;

    private String  product_code;

    private String  storage_code;

    private String  storage_position_code;

    private String  engine_no;

    private String  key_number;

    private String  po_no;

    private Double  purchase_price;

    private Date    vs_purchase_date;

    private Double  additional_cost;

    private String  deliveryman_name;

    private String  deliveryman_phone;

    private String  shipper_license;

    private String  shipper_name;

    private String  shipping_address;

    private String  shipping_order_no;

    private String  vendor_code;

    private String  vendor_name;

    private String  inspector;

    private Integer mar_status;

    private String  consigner_code;

    private String  consigner_name;

    private String  certificate_number;

    private Integer has_certificate;

    private Date    manufacture_date;

    private Date    shipping_date;

    private Date    factory_date;

    private Date    inspection_date;

    private Date    submit_time;

    private Date    arriving_date;

    private Integer oem_tag;

    private Date    arrived_date;

    private String  remark;

    private String  producting_area;

    private String  exhaust_quantity;

    private String  vsn;

    private Integer discharge_standard;

    private String  model_year;

    private Date    finished_date;

    private String  se_no;

    private String  financial_bill_no;

    private Integer supervise_type;

    private Integer is_direct;

    private String  delivery_type;

    private Integer inspection_consigned;

    private String  color_code;
    
	public Integer getIs_finished() {
        return is_finished;
    }

    public void setIs_finished(Integer is_finished) {
        this.is_finished = is_finished;
    }

    public Integer getIs_upload() {
        return is_upload;
    }

    public void setIs_upload(Integer is_upload) {
        this.is_upload = is_upload;
    }

    public Integer getInspection_result() {
        return inspection_result;
    }

    public void setInspection_result(Integer inspection_result) {
        this.inspection_result = inspection_result;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getStorage_code() {
        return storage_code;
    }

    public void setStorage_code(String storage_code) {
        this.storage_code = storage_code;
    }

    public String getStorage_position_code() {
        return storage_position_code;
    }

    public void setStorage_position_code(String storage_position_code) {
        this.storage_position_code = storage_position_code;
    }

    public String getEngine_no() {
        return engine_no;
    }

    public void setEngine_no(String engine_no) {
        this.engine_no = engine_no;
    }

    public String getKey_number() {
        return key_number;
    }

    public void setKey_number(String key_number) {
        this.key_number = key_number;
    }

    public String getPo_no() {
        return po_no;
    }

    public void setPo_no(String po_no) {
        this.po_no = po_no;
    }

    public Double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(Double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public Date getVs_purchase_date() {
        return vs_purchase_date;
    }

    public void setVs_purchase_date(Date vs_purchase_date) {
        this.vs_purchase_date = vs_purchase_date;
    }

    public Double getAdditional_cost() {
        return additional_cost;
    }

    public void setAdditional_cost(Double additional_cost) {
        this.additional_cost = additional_cost;
    }

    public String getDeliveryman_name() {
        return deliveryman_name;
    }

    public void setDeliveryman_name(String deliveryman_name) {
        this.deliveryman_name = deliveryman_name;
    }

    public String getDeliveryman_phone() {
        return deliveryman_phone;
    }

    public void setDeliveryman_phone(String deliveryman_phone) {
        this.deliveryman_phone = deliveryman_phone;
    }

    public String getShipper_license() {
        return shipper_license;
    }

    public void setShipper_license(String shipper_license) {
        this.shipper_license = shipper_license;
    }

    public String getShipper_name() {
        return shipper_name;
    }

    public void setShipper_name(String shipper_name) {
        this.shipper_name = shipper_name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_order_no() {
        return shipping_order_no;
    }

    public void setShipping_order_no(String shipping_order_no) {
        this.shipping_order_no = shipping_order_no;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public void setVendor_code(String vendor_code) {
        this.vendor_code = vendor_code;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public Integer getMar_status() {
        return mar_status;
    }

    public void setMar_status(Integer mar_status) {
        this.mar_status = mar_status;
    }

    public String getConsigner_code() {
        return consigner_code;
    }

    public void setConsigner_code(String consigner_code) {
        this.consigner_code = consigner_code;
    }

    public String getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(String consigner_name) {
        this.consigner_name = consigner_name;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public Integer getHas_certificate() {
        return has_certificate;
    }

    public void setHas_certificate(Integer has_certificate) {
        this.has_certificate = has_certificate;
    }

    public Date getManufacture_date() {
        return manufacture_date;
    }

    public void setManufacture_date(Date manufacture_date) {
        this.manufacture_date = manufacture_date;
    }

    public Date getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(Date shipping_date) {
        this.shipping_date = shipping_date;
    }

    public Date getFactory_date() {
        return factory_date;
    }

    public void setFactory_date(Date factory_date) {
        this.factory_date = factory_date;
    }

    public Date getInspection_date() {
        return inspection_date;
    }

    public void setInspection_date(Date inspection_date) {
        this.inspection_date = inspection_date;
    }

    public Date getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }

    public Date getArriving_date() {
        return arriving_date;
    }

    public void setArriving_date(Date arriving_date) {
        this.arriving_date = arriving_date;
    }

    public Integer getOem_tag() {
        return oem_tag;
    }

    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }

    public Date getArrived_date() {
        return arrived_date;
    }

    public void setArrived_date(Date arrived_date) {
        this.arrived_date = arrived_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProducting_area() {
        return producting_area;
    }

    public void setProducting_area(String producting_area) {
        this.producting_area = producting_area;
    }

    public String getExhaust_quantity() {
        return exhaust_quantity;
    }

    public void setExhaust_quantity(String exhaust_quantity) {
        this.exhaust_quantity = exhaust_quantity;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public Integer getDischarge_standard() {
        return discharge_standard;
    }

    public void setDischarge_standard(Integer discharge_standard) {
        this.discharge_standard = discharge_standard;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public Date getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(Date finished_date) {
        this.finished_date = finished_date;
    }

    public String getSe_no() {
        return se_no;
    }

    public void setSe_no(String se_no) {
        this.se_no = se_no;
    }

    public String getFinancial_bill_no() {
        return financial_bill_no;
    }

    public void setFinancial_bill_no(String financial_bill_no) {
        this.financial_bill_no = financial_bill_no;
    }

    public Integer getSupervise_type() {
        return supervise_type;
    }

    public void setSupervise_type(Integer supervise_type) {
        this.supervise_type = supervise_type;
    }

    public Integer getIs_direct() {
        return is_direct;
    }

    public void setIs_direct(Integer is_direct) {
        this.is_direct = is_direct;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public Integer getInspection_consigned() {
        return inspection_consigned;
    }

    public void setInspection_consigned(Integer inspection_consigned) {
        this.inspection_consigned = inspection_consigned;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    /**
     * String转Date类型
    * TODO description
    * @author yangjie
    * @date 2017年1月20日
    * @param time
    * @return
     */
    public static Date strToDate(String time){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
}
