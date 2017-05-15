/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */

/**
* TODO description
* @author Administrator
* @date 2017年1月1日
*/
	
public class OwnerCusDTO {
    
    private String    noList;//隐藏字段 ，用于潜客再分配
    private String    isCustomer;//隐藏字段 ，用于潜客再分配
	
    /**
     * @return the noList
     */
    public String getNoList() {
        return noList;
    }





    
    /**
     * @param noList the noList to set
     */
    public void setNoList(String noList) {
        this.noList = noList;
    }





    
    /**
     * @return the isCustomer
     */
    public String getIsCustomer() {
        return isCustomer;
    }





    
    /**
     * @param isCustomer the isCustomer to set
     */
    public void setIsCustomer(String isCustomer) {
        this.isCustomer = isCustomer;
    }

    private String customerNo;
	
	private Integer customerType;
	
	private String customerName;
	
	private Integer gender;
	
	private Date birthday;
	
	private Integer ctCode;
	
    private String certificateNo;
	
	private Integer province1;
	
	private Integer city1;
	
	private Integer district1;
	
	private Integer zipCode;
	
    private String address;
	
	private Integer bestContactType;
	
	private String eMail;
	
	private String contactorPhone;
	
	private String contactorMobile;
	
    private String fax;
	
	private Integer cusSource;
	
	private Integer mediaType;
	
	private Integer buyPurpose;
	
	private String soldBy;
	
    private String dcrcService;
	
	private Integer isFirstBuy;
	
	private Integer hasDriverLicense;
	
	private String recommendEmpName;
	
	private Integer ownerMarriage;
	
	private Integer ageStage;
	
	private Integer educationLevel;
	
	private Integer industryFirst;
	
    private String positionName;
	
	private Integer industrySecond;
	
	private Integer vocationType;
	
	private Integer familyIncome;
	
	private Integer hobby;
	
	 private List<String> buyReason;
	
    private String recommendEmpPhone;
	
	private Integer isPersonDriveCar;
	
    private String modifyReason;
	
	private String largeCustomerNo;
	
	private Date firstdateDrive;
	
	private String poCustomerName;
	
	private String poCustomerPhone;
	
	private String poCustomerMobile;
	
	private String poAddress;
	
	private String remark;	
	
	private List<TtCustomerLinkmanInfoDTO> ownerList4;

	private List<FamilyMenberDTO> ownerList3;
	
	private String vin;

	private String ownerName;
	
	   private String brandId;

	    private String seriesId;
	    
	    private String intentModel;

	    private String apackage;
	    
	    private String color;

	    private Date salesdate;
	    
	    private Date licenseDate;

	    private String lisence;
	    
	    private String contractNo;

	    private Integer vehiclePurpose;
	    
	    private Date insuranceEndDate;

	    private Date stockOutDate;
	    
	    private String vehiclePrice;

	    private String mileage;
	    
	    private String salesAgentName;

	    private String consultant;

	
    
    
        
        public Date getSalesdate() {
            return salesdate;
        }




        
        public void setSalesdate(Date salesdate) {
            this.salesdate = salesdate;
        }




        
        public Date getLicenseDate() {
            return licenseDate;
        }




        
        public void setLicenseDate(Date licenseDate) {
            this.licenseDate = licenseDate;
        }




        
        public Integer getVehiclePurpose() {
            return vehiclePurpose;
        }




        
        public void setVehiclePurpose(Integer vehiclePurpose) {
            this.vehiclePurpose = vehiclePurpose;
        }




        
        public Date getInsuranceEndDate() {
            return insuranceEndDate;
        }




        
        public void setInsuranceEndDate(Date insuranceEndDate) {
            this.insuranceEndDate = insuranceEndDate;
        }




        
        public Date getStockOutDate() {
            return stockOutDate;
        }




        
        public void setStockOutDate(Date stockOutDate) {
            this.stockOutDate = stockOutDate;
        }




        public String getVin() {
            return vin;
        }



        
        public void setVin(String vin) {
            this.vin = vin;
        }



        
        public String getOwnerName() {
            return ownerName;
        }



        
        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }



        
        public String getBrandId() {
            return brandId;
        }



        
        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }



        
        public String getSeriesId() {
            return seriesId;
        }



        
        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }



        
        public String getIntentModel() {
            return intentModel;
        }



        
        public void setIntentModel(String intentModel) {
            this.intentModel = intentModel;
        }



        
        public String getApackage() {
            return apackage;
        }



        
        public void setApackage(String apackage) {
            this.apackage = apackage;
        }



        
        public String getColor() {
            return color;
        }



        
        public void setColor(String color) {
            this.color = color;
        }



        
      


        
        public String getLisence() {
            return lisence;
        }



        
        public void setLisence(String lisence) {
            this.lisence = lisence;
        }



        
        public String getContractNo() {
            return contractNo;
        }



        
        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }



        
       


        
        public String getVehiclePrice() {
            return vehiclePrice;
        }



        
        public void setVehiclePrice(String vehiclePrice) {
            this.vehiclePrice = vehiclePrice;
        }



        
        public String getMileage() {
            return mileage;
        }



        
        public void setMileage(String mileage) {
            this.mileage = mileage;
        }



        
        public String getSalesAgentName() {
            return salesAgentName;
        }



        
        public void setSalesAgentName(String salesAgentName) {
            this.salesAgentName = salesAgentName;
        }



        
        public String getConsultant() {
            return consultant;
        }



        
        public void setConsultant(String consultant) {
            this.consultant = consultant;
        }



    public List<TtCustomerLinkmanInfoDTO> getOwnerList4() {
        return ownerList4;
    }


    
    public void setOwnerList4(List<TtCustomerLinkmanInfoDTO> ownerList4) {
        this.ownerList4 = ownerList4;
    }


    public List<String> getBuyReason() {
        return buyReason;
    }

    
    public void setBuyReason(List<String> buyReason) {
        this.buyReason = buyReason;
    }


	public List<FamilyMenberDTO> getOwnerList3() {
		return ownerList3;
	}

	public void setOwnerList3(List<FamilyMenberDTO> ownerList3) {
		this.ownerList3 = ownerList3;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getCtCode() {
		return ctCode;
	}

	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
    
    public Integer getProvince1() {
        return province1;
    }

    
    public void setProvince1(Integer province1) {
        this.province1 = province1;
    }

    
    public Integer getCity1() {
        return city1;
    }

    
    public void setCity1(Integer city1) {
        this.city1 = city1;
    }

    
    public Integer getDistrict1() {
        return district1;
    }

    
    public void setDistrict1(Integer district1) {
        this.district1 = district1;
    }

    public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getBestContactType() {
		return bestContactType;
	}

	public void setBestContactType(Integer bestContactType) {
		this.bestContactType = bestContactType;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}

	public String getContactorMobile() {
		return contactorMobile;
	}

	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getCusSource() {
		return cusSource;
	}

	public void setCusSource(Integer cusSource) {
		this.cusSource = cusSource;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getBuyPurpose() {
		return buyPurpose;
	}

	public void setBuyPurpose(Integer buyPurpose) {
		this.buyPurpose = buyPurpose;
	}



    
    public String getSoldBy() {
        return soldBy;
    }



    
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }



    public String getDcrcService() {
		return dcrcService;
	}

	public void setDcrcService(String dcrcService) {
		this.dcrcService = dcrcService;
	}


	public Integer getIsFirstBuy() {
		return isFirstBuy;
	}

	public void setIsFirstBuy(Integer isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}

	public Integer getHasDriverLicense() {
		return hasDriverLicense;
	}

	public void setHasDriverLicense(Integer hasDriverLicense) {
		this.hasDriverLicense = hasDriverLicense;
	}

	public String getRecommendEmpName() {
		return recommendEmpName;
	}

	public void setRecommendEmpName(String recommendEmpName) {
		this.recommendEmpName = recommendEmpName;
	}

	public Integer getOwnerMarriage() {
		return ownerMarriage;
	}

	public void setOwnerMarriage(Integer ownerMarriage) {
		this.ownerMarriage = ownerMarriage;
	}

	public Integer getAgeStage() {
		return ageStage;
	}

	public void setAgeStage(Integer ageStage) {
		this.ageStage = ageStage;
	}

	public Integer getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Integer getIndustryFirst() {
		return industryFirst;
	}

	public void setIndustryFirst(Integer industryFirst) {
		this.industryFirst = industryFirst;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getIndustrySecond() {
		return industrySecond;
	}

	public void setIndustrySecond(Integer industrySecond) {
		this.industrySecond = industrySecond;
	}

	public Integer getVocationType() {
		return vocationType;
	}

	public void setVocationType(Integer vocationType) {
		this.vocationType = vocationType;
	}

	public Integer getFamilyIncome() {
		return familyIncome;
	}

	public void setFamilyIncome(Integer familyIncome) {
		this.familyIncome = familyIncome;
	}

	public Integer getHobby() {
		return hobby;
	}

	public void setHobby(Integer hobby) {
		this.hobby = hobby;
	}

	public String getRecommendEmpPhone() {
		return recommendEmpPhone;
	}

	public void setRecommendEmpPhone(String recommendEmpPhone) {
		this.recommendEmpPhone = recommendEmpPhone;
	}

	public Integer getIsPersonDriveCar() {
		return isPersonDriveCar;
	}

	public void setIsPersonDriveCar(Integer isPersonDriveCar) {
		this.isPersonDriveCar = isPersonDriveCar;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getLargeCustomerNo() {
		return largeCustomerNo;
	}

	public void setLargeCustomerNo(String largeCustomerNo) {
		this.largeCustomerNo = largeCustomerNo;
	}

	public Date getFirstdateDrive() {
		return firstdateDrive;
	}

	public void setFirstdateDrive(Date firstdateDrive) {
		this.firstdateDrive = firstdateDrive;
	}

	public String getPoCustomerName() {
		return poCustomerName;
	}

	public void setPoCustomerName(String poCustomerName) {
		this.poCustomerName = poCustomerName;
	}

	public String getPoCustomerPhone() {
		return poCustomerPhone;
	}

	public void setPoCustomerPhone(String poCustomerPhone) {
		this.poCustomerPhone = poCustomerPhone;
	}

	public String getPoCustomerMobile() {
		return poCustomerMobile;
	}

	public void setPoCustomerMobile(String poCustomerMobile) {
		this.poCustomerMobile = poCustomerMobile;
	}

	public String getPoAddress() {
		return poAddress;
	}

	public void setPoAddress(String poAddress) {
		this.poAddress = poAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	
	
	
	
}
