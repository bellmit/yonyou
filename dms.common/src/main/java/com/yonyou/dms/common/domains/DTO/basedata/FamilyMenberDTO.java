/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.basedata;

/**
 * @author Administrator
 *
 */
public class FamilyMenberDTO {
	private String customerName;
	
	private String customerCode;
	
	private String birthday;
	
	private String gender;
	
	private String educationLevel;
	
	private String ownerMarriage;
	
	private String phone;
	
	private String relation;



	
    
    public String getCustomerName() {
        return customerName;
    }


    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getOwnerMarriage() {
		return ownerMarriage;
	}

	public void setOwnerMarriage(String ownerMarriage) {
		this.ownerMarriage = ownerMarriage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	
}
