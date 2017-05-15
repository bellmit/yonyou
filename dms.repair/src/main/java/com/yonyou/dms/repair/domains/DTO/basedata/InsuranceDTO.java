package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 
* 保险表字段
* @author zhongshiwei
* @date 2016年6月30日
 */

public class InsuranceDTO {

    private Long insurance_id;//保险公司ID
    
    private String dealer_code;//经销商代码
    @Required 
    private String insuration_code;//保险公司代码
    @Required
    private String insuration_name;//保险公司名称
    @Required
    private String insuration_short_name;//保险公司简称

    private String contactor_name;//联系人

    private String contactor_phone;//联系人电话

    private String contactor_mobile;//联系人手机

    private String fax;//传真

    private String address;//地址

    private String zip_code;//邮编

    private String bank;//开户银行

    private String bank_account;//银行帐号

    private Double increase_rate;//保险加价率

    private String insuration_level;//机构级别

    private String insuration_higher;//上级机构

    private String insuration_medium;//保险中介机构

    private Integer oem_tag;//OEM标志

    private Integer is_valid;//是否有效

    private Double busi_insurance_rebate_numc;//传统商业险返利点

    private Double com_insurance_rebate_numc;//传统交强险返利点

    private Double busi_insurance_rebate_numx;//信贷商业险返利点

    private Double com_insurance_rebate_numx;//信贷交强险返利点

    private Double busi_insurance_rebate_numd;//电话商业险返利点

    private Double com_insurance_rebate_numd;//电话交强险返利点
    
    private Double busi_insurance_rebate_numq;//其它商业险返利点

    private Double com_insurance_rebate_numq;//其它交强险返利点

    private Integer record_version;

    private String create_by;

    private Date created_at;

    private String update_by;

    private Date updated_at;

    

    
    public String getDealer_code() {
        return dealer_code;
    }

    
    public void setDealer_code(String dealer_code) {
        this.dealer_code = dealer_code;
    }

    
    public String getInsuration_code() {
        return insuration_code;
    }

    
    public void setInsuration_code(String insuration_code) {
        this.insuration_code = insuration_code;
    }

    
    public String getInsuration_name() {
        return insuration_name;
    }

    
    public void setInsuration_name(String insuration_name) {
        this.insuration_name = insuration_name;
    }

    
    public String getInsuration_short_name() {
        return insuration_short_name;
    }

    
    public void setInsuration_short_name(String insuration_short_name) {
        this.insuration_short_name = insuration_short_name;
    }

    
    public String getContactor_name() {
        return contactor_name;
    }

    
    public void setContactor_name(String contactor_name) {
        this.contactor_name = contactor_name;
    }

    
    public String getContactor_phone() {
        return contactor_phone;
    }

    
    public void setContactor_phone(String contactor_phone) {
        this.contactor_phone = contactor_phone;
    }

    
    public String getContactor_mobile() {
        return contactor_mobile;
    }

    
    public void setContactor_mobile(String contactor_mobile) {
        this.contactor_mobile = contactor_mobile;
    }

    
    public String getFax() {
        return fax;
    }

    
    public void setFax(String fax) {
        this.fax = fax;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getZip_code() {
        return zip_code;
    }

    
    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    
    public String getBank() {
        return bank;
    }

    
    public void setBank(String bank) {
        this.bank = bank;
    }

    
    public String getBank_account() {
        return bank_account;
    }

    
    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    
    public Double getIncrease_rate() {
        return increase_rate;
    }

    
    public void setIncrease_rate(Double increase_rate) {
        this.increase_rate = increase_rate;
    }

    
    public String getInsuration_level() {
        return insuration_level;
    }

    
    public void setInsuration_level(String insuration_level) {
        this.insuration_level = insuration_level;
    }

    
    public String getInsuration_higher() {
        return insuration_higher;
    }

    
    public void setInsuration_higher(String insuration_higher) {
        this.insuration_higher = insuration_higher;
    }

    
    public String getInsuration_medium() {
        return insuration_medium;
    }

    
    public void setInsuration_medium(String insuration_medium) {
        this.insuration_medium = insuration_medium;
    }

    
    public Integer getOem_tag() {
        return oem_tag;
    }

    
    public void setOem_tag(Integer oem_tag) {
        this.oem_tag = oem_tag;
    }

    
    public Integer getIs_valid() {
        return is_valid;
    }

    
    public void setIs_valid(Integer is_valid) {
        this.is_valid = is_valid;
    }

    
    public Double getBusi_insurance_rebate_numc() {
        return busi_insurance_rebate_numc;
    }

    
    public void setBusi_insurance_rebate_numc(Double busi_insurance_rebate_numc) {
        this.busi_insurance_rebate_numc = busi_insurance_rebate_numc;
    }

    
    public Double getCom_insurance_rebate_numc() {
        return com_insurance_rebate_numc;
    }

    
    public void setCom_insurance_rebate_numc(Double com_insurance_rebate_numc) {
        this.com_insurance_rebate_numc = com_insurance_rebate_numc;
    }

    
    public Double getBusi_insurance_rebate_numx() {
        return busi_insurance_rebate_numx;
    }

    
    public void setBusi_insurance_rebate_numx(Double busi_insurance_rebate_numx) {
        this.busi_insurance_rebate_numx = busi_insurance_rebate_numx;
    }

    
    public Double getCom_insurance_rebate_numx() {
        return com_insurance_rebate_numx;
    }

    
    public void setCom_insurance_rebate_numx(Double com_insurance_rebate_numx) {
        this.com_insurance_rebate_numx = com_insurance_rebate_numx;
    }

    
    public Double getBusi_insurance_rebate_numd() {
        return busi_insurance_rebate_numd;
    }

    
    public void setBusi_insurance_rebate_numd(Double busi_insurance_rebate_numd) {
        this.busi_insurance_rebate_numd = busi_insurance_rebate_numd;
    }

    
    public Double getCom_insurance_rebate_numd() {
        return com_insurance_rebate_numd;
    }

    
    public void setCom_insurance_rebate_numd(Double com_insurance_rebate_numd) {
        this.com_insurance_rebate_numd = com_insurance_rebate_numd;
    }

    
    public Double getBusi_insurance_rebate_numq() {
        return busi_insurance_rebate_numq;
    }

    
    public void setBusi_insurance_rebate_numq(Double busi_insurance_rebate_numq) {
        this.busi_insurance_rebate_numq = busi_insurance_rebate_numq;
    }

    
    public Double getCom_insurance_rebate_numq() {
        return com_insurance_rebate_numq;
    }

    
    public void setCom_insurance_rebate_numq(Double com_insurance_rebate_numq) {
        this.com_insurance_rebate_numq = com_insurance_rebate_numq;
    }

    
    public Integer getRecord_version() {
        return record_version;
    }

    
    public void setRecord_version(Integer record_version) {
        this.record_version = record_version;
    }

    
    public String getCreate_by() {
        return create_by;
    }

    
    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    
    public Date getCreated_at() {
        return created_at;
    }

    
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    
    public String getUpdate_by() {
        return update_by;
    }

    
    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    
    public Date getUpdated_at() {
        return updated_at;
    }

    
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }


    public Long getInsurance_id() {
        return insurance_id;
    }


    public void setInsurance_id(Long insurance_id) {
        this.insurance_id = insurance_id;
    }
    
}
