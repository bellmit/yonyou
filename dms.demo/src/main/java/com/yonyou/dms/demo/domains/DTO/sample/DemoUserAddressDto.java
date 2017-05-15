package com.yonyou.dms.demo.domains.DTO.sample;

import java.util.Date;
import java.util.List;

/**
 * 
* UserDto
* @author zhangxc
* @date 2016年6月30日
 */
public class DemoUserAddressDto {

	private List<String> province;
	private Long country;
	private String address;
	private Long status;
	private Double mills;
	private Date addressDate;
	private Long addressFile;
    
    
    public Long getCountry() {
        return country;
    }
    
    public void setCountry(Long country) {
        this.country = country;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Long getStatus() {
        return status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    public Double getMills() {
        return mills;
    }
    
    public List<String> getProvince() {
        return province;
    }

    
    public void setProvince(List<String> province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "UserAddressDto [province=" + province + ", country=" + country + ", address=" + address + ", status="
               + status + ", mills=" + mills + ", addressDate=" + addressDate + "]";
    }

    
    public Date getAddressDate() {
        return addressDate;
    }

    
    public void setAddressDate(Date addressDate) {
        this.addressDate = addressDate;
    }

    
    public void setMills(Double mills) {
        this.mills = mills;
    }

    
    public Long getAddressFile() {
        return addressFile;
    }

    
    public void setAddressFile(Long addressFile) {
        this.addressFile = addressFile;
    }

    
    
	
}