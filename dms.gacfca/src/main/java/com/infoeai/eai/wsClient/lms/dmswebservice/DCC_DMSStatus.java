/**
 * DCC_DMSStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.dmswebservice;

public class DCC_DMSStatus  implements java.io.Serializable {
    private ExtensionDataObject extensionData;

    private java.lang.String brandID;

    private java.lang.String carStyleID;

    private int cityID;

    private int considerationID;

    private java.util.Calendar createDate;

    private java.util.Calendar DMSCreateDateTime;

    private java.lang.String DMSCustomerID;

    private java.util.Calendar DMSFollowUpDateTime;

    private java.lang.String DMSOpportunityLevelID;

    private java.lang.String DMSSalesConsultant;

    private java.lang.String dealerCode;

    private java.lang.String gender;

    private java.util.Calendar giveUpDate;

    private int ID;

    private java.lang.String modelID;

    private java.lang.String name;

    private java.lang.String otherGiveUpReason;

    private java.lang.String phone;

    private int potentialCustomerID;

    private int provinceID;

    private int status;

    private java.lang.String telephone;

    private java.util.Calendar winOrderDate;

    public DCC_DMSStatus() {
    }

    public DCC_DMSStatus(
           ExtensionDataObject extensionData,
           java.lang.String brandID,
           java.lang.String carStyleID,
           int cityID,
           int considerationID,
           java.util.Calendar createDate,
           java.util.Calendar DMSCreateDateTime,
           java.lang.String DMSCustomerID,
           java.util.Calendar DMSFollowUpDateTime,
           java.lang.String DMSOpportunityLevelID,
           java.lang.String DMSSalesConsultant,
           java.lang.String dealerCode,
           java.lang.String gender,
           java.util.Calendar giveUpDate,
           int ID,
           java.lang.String modelID,
           java.lang.String name,
           java.lang.String otherGiveUpReason,
           java.lang.String phone,
           int potentialCustomerID,
           int provinceID,
           int status,
           java.lang.String telephone,
           java.util.Calendar winOrderDate) {
           this.extensionData = extensionData;
           this.brandID = brandID;
           this.carStyleID = carStyleID;
           this.cityID = cityID;
           this.considerationID = considerationID;
           this.createDate = createDate;
           this.DMSCreateDateTime = DMSCreateDateTime;
           this.DMSCustomerID = DMSCustomerID;
           this.DMSFollowUpDateTime = DMSFollowUpDateTime;
           this.DMSOpportunityLevelID = DMSOpportunityLevelID;
           this.DMSSalesConsultant = DMSSalesConsultant;
           this.dealerCode = dealerCode;
           this.gender = gender;
           this.giveUpDate = giveUpDate;
           this.ID = ID;
           this.modelID = modelID;
           this.name = name;
           this.otherGiveUpReason = otherGiveUpReason;
           this.phone = phone;
           this.potentialCustomerID = potentialCustomerID;
           this.provinceID = provinceID;
           this.status = status;
           this.telephone = telephone;
           this.winOrderDate = winOrderDate;
    }


    /**
     * Gets the extensionData value for this DCC_DMSStatus.
     * 
     * @return extensionData
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }


    /**
     * Sets the extensionData value for this DCC_DMSStatus.
     * 
     * @param extensionData
     */
    public void setExtensionData(ExtensionDataObject extensionData) {
        this.extensionData = extensionData;
    }


    /**
     * Gets the brandID value for this DCC_DMSStatus.
     * 
     * @return brandID
     */
    public java.lang.String getBrandID() {
        return brandID;
    }


    /**
     * Sets the brandID value for this DCC_DMSStatus.
     * 
     * @param brandID
     */
    public void setBrandID(java.lang.String brandID) {
        this.brandID = brandID;
    }


    /**
     * Gets the carStyleID value for this DCC_DMSStatus.
     * 
     * @return carStyleID
     */
    public java.lang.String getCarStyleID() {
        return carStyleID;
    }


    /**
     * Sets the carStyleID value for this DCC_DMSStatus.
     * 
     * @param carStyleID
     */
    public void setCarStyleID(java.lang.String carStyleID) {
        this.carStyleID = carStyleID;
    }


    /**
     * Gets the cityID value for this DCC_DMSStatus.
     * 
     * @return cityID
     */
    public int getCityID() {
        return cityID;
    }


    /**
     * Sets the cityID value for this DCC_DMSStatus.
     * 
     * @param cityID
     */
    public void setCityID(int cityID) {
        this.cityID = cityID;
    }


    /**
     * Gets the considerationID value for this DCC_DMSStatus.
     * 
     * @return considerationID
     */
    public int getConsiderationID() {
        return considerationID;
    }


    /**
     * Sets the considerationID value for this DCC_DMSStatus.
     * 
     * @param considerationID
     */
    public void setConsiderationID(int considerationID) {
        this.considerationID = considerationID;
    }


    /**
     * Gets the createDate value for this DCC_DMSStatus.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this DCC_DMSStatus.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the DMSCreateDateTime value for this DCC_DMSStatus.
     * 
     * @return DMSCreateDateTime
     */
    public java.util.Calendar getDMSCreateDateTime() {
        return DMSCreateDateTime;
    }


    /**
     * Sets the DMSCreateDateTime value for this DCC_DMSStatus.
     * 
     * @param DMSCreateDateTime
     */
    public void setDMSCreateDateTime(java.util.Calendar DMSCreateDateTime) {
        this.DMSCreateDateTime = DMSCreateDateTime;
    }


    /**
     * Gets the DMSCustomerID value for this DCC_DMSStatus.
     * 
     * @return DMSCustomerID
     */
    public java.lang.String getDMSCustomerID() {
        return DMSCustomerID;
    }


    /**
     * Sets the DMSCustomerID value for this DCC_DMSStatus.
     * 
     * @param DMSCustomerID
     */
    public void setDMSCustomerID(java.lang.String DMSCustomerID) {
        this.DMSCustomerID = DMSCustomerID;
    }


    /**
     * Gets the DMSFollowUpDateTime value for this DCC_DMSStatus.
     * 
     * @return DMSFollowUpDateTime
     */
    public java.util.Calendar getDMSFollowUpDateTime() {
        return DMSFollowUpDateTime;
    }


    /**
     * Sets the DMSFollowUpDateTime value for this DCC_DMSStatus.
     * 
     * @param DMSFollowUpDateTime
     */
    public void setDMSFollowUpDateTime(java.util.Calendar DMSFollowUpDateTime) {
        this.DMSFollowUpDateTime = DMSFollowUpDateTime;
    }


    /**
     * Gets the DMSOpportunityLevelID value for this DCC_DMSStatus.
     * 
     * @return DMSOpportunityLevelID
     */
    public java.lang.String getDMSOpportunityLevelID() {
        return DMSOpportunityLevelID;
    }


    /**
     * Sets the DMSOpportunityLevelID value for this DCC_DMSStatus.
     * 
     * @param DMSOpportunityLevelID
     */
    public void setDMSOpportunityLevelID(java.lang.String DMSOpportunityLevelID) {
        this.DMSOpportunityLevelID = DMSOpportunityLevelID;
    }


    /**
     * Gets the DMSSalesConsultant value for this DCC_DMSStatus.
     * 
     * @return DMSSalesConsultant
     */
    public java.lang.String getDMSSalesConsultant() {
        return DMSSalesConsultant;
    }


    /**
     * Sets the DMSSalesConsultant value for this DCC_DMSStatus.
     * 
     * @param DMSSalesConsultant
     */
    public void setDMSSalesConsultant(java.lang.String DMSSalesConsultant) {
        this.DMSSalesConsultant = DMSSalesConsultant;
    }


    /**
     * Gets the dealerCode value for this DCC_DMSStatus.
     * 
     * @return dealerCode
     */
    public java.lang.String getDealerCode() {
        return dealerCode;
    }


    /**
     * Sets the dealerCode value for this DCC_DMSStatus.
     * 
     * @param dealerCode
     */
    public void setDealerCode(java.lang.String dealerCode) {
        this.dealerCode = dealerCode;
    }


    /**
     * Gets the gender value for this DCC_DMSStatus.
     * 
     * @return gender
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this DCC_DMSStatus.
     * 
     * @param gender
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the giveUpDate value for this DCC_DMSStatus.
     * 
     * @return giveUpDate
     */
    public java.util.Calendar getGiveUpDate() {
        return giveUpDate;
    }


    /**
     * Sets the giveUpDate value for this DCC_DMSStatus.
     * 
     * @param giveUpDate
     */
    public void setGiveUpDate(java.util.Calendar giveUpDate) {
        this.giveUpDate = giveUpDate;
    }


    /**
     * Gets the ID value for this DCC_DMSStatus.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this DCC_DMSStatus.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the modelID value for this DCC_DMSStatus.
     * 
     * @return modelID
     */
    public java.lang.String getModelID() {
        return modelID;
    }


    /**
     * Sets the modelID value for this DCC_DMSStatus.
     * 
     * @param modelID
     */
    public void setModelID(java.lang.String modelID) {
        this.modelID = modelID;
    }


    /**
     * Gets the name value for this DCC_DMSStatus.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DCC_DMSStatus.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the otherGiveUpReason value for this DCC_DMSStatus.
     * 
     * @return otherGiveUpReason
     */
    public java.lang.String getOtherGiveUpReason() {
        return otherGiveUpReason;
    }


    /**
     * Sets the otherGiveUpReason value for this DCC_DMSStatus.
     * 
     * @param otherGiveUpReason
     */
    public void setOtherGiveUpReason(java.lang.String otherGiveUpReason) {
        this.otherGiveUpReason = otherGiveUpReason;
    }


    /**
     * Gets the phone value for this DCC_DMSStatus.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this DCC_DMSStatus.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the potentialCustomerID value for this DCC_DMSStatus.
     * 
     * @return potentialCustomerID
     */
    public int getPotentialCustomerID() {
        return potentialCustomerID;
    }


    /**
     * Sets the potentialCustomerID value for this DCC_DMSStatus.
     * 
     * @param potentialCustomerID
     */
    public void setPotentialCustomerID(int potentialCustomerID) {
        this.potentialCustomerID = potentialCustomerID;
    }


    /**
     * Gets the provinceID value for this DCC_DMSStatus.
     * 
     * @return provinceID
     */
    public int getProvinceID() {
        return provinceID;
    }


    /**
     * Sets the provinceID value for this DCC_DMSStatus.
     * 
     * @param provinceID
     */
    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }


    /**
     * Gets the status value for this DCC_DMSStatus.
     * 
     * @return status
     */
    public int getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DCC_DMSStatus.
     * 
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Gets the telephone value for this DCC_DMSStatus.
     * 
     * @return telephone
     */
    public java.lang.String getTelephone() {
        return telephone;
    }


    /**
     * Sets the telephone value for this DCC_DMSStatus.
     * 
     * @param telephone
     */
    public void setTelephone(java.lang.String telephone) {
        this.telephone = telephone;
    }


    /**
     * Gets the winOrderDate value for this DCC_DMSStatus.
     * 
     * @return winOrderDate
     */
    public java.util.Calendar getWinOrderDate() {
        return winOrderDate;
    }


    /**
     * Sets the winOrderDate value for this DCC_DMSStatus.
     * 
     * @param winOrderDate
     */
    public void setWinOrderDate(java.util.Calendar winOrderDate) {
        this.winOrderDate = winOrderDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DCC_DMSStatus)) return false;
        DCC_DMSStatus other = (DCC_DMSStatus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.extensionData==null && other.getExtensionData()==null) || 
             (this.extensionData!=null &&
              this.extensionData.equals(other.getExtensionData()))) &&
            ((this.brandID==null && other.getBrandID()==null) || 
             (this.brandID!=null &&
              this.brandID.equals(other.getBrandID()))) &&
            ((this.carStyleID==null && other.getCarStyleID()==null) || 
             (this.carStyleID!=null &&
              this.carStyleID.equals(other.getCarStyleID()))) &&
            this.cityID == other.getCityID() &&
            this.considerationID == other.getConsiderationID() &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.DMSCreateDateTime==null && other.getDMSCreateDateTime()==null) || 
             (this.DMSCreateDateTime!=null &&
              this.DMSCreateDateTime.equals(other.getDMSCreateDateTime()))) &&
            ((this.DMSCustomerID==null && other.getDMSCustomerID()==null) || 
             (this.DMSCustomerID!=null &&
              this.DMSCustomerID.equals(other.getDMSCustomerID()))) &&
            ((this.DMSFollowUpDateTime==null && other.getDMSFollowUpDateTime()==null) || 
             (this.DMSFollowUpDateTime!=null &&
              this.DMSFollowUpDateTime.equals(other.getDMSFollowUpDateTime()))) &&
            ((this.DMSOpportunityLevelID==null && other.getDMSOpportunityLevelID()==null) || 
             (this.DMSOpportunityLevelID!=null &&
              this.DMSOpportunityLevelID.equals(other.getDMSOpportunityLevelID()))) &&
            ((this.DMSSalesConsultant==null && other.getDMSSalesConsultant()==null) || 
             (this.DMSSalesConsultant!=null &&
              this.DMSSalesConsultant.equals(other.getDMSSalesConsultant()))) &&
            ((this.dealerCode==null && other.getDealerCode()==null) || 
             (this.dealerCode!=null &&
              this.dealerCode.equals(other.getDealerCode()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.giveUpDate==null && other.getGiveUpDate()==null) || 
             (this.giveUpDate!=null &&
              this.giveUpDate.equals(other.getGiveUpDate()))) &&
            this.ID == other.getID() &&
            ((this.modelID==null && other.getModelID()==null) || 
             (this.modelID!=null &&
              this.modelID.equals(other.getModelID()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.otherGiveUpReason==null && other.getOtherGiveUpReason()==null) || 
             (this.otherGiveUpReason!=null &&
              this.otherGiveUpReason.equals(other.getOtherGiveUpReason()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            this.potentialCustomerID == other.getPotentialCustomerID() &&
            this.provinceID == other.getProvinceID() &&
            this.status == other.getStatus() &&
            ((this.telephone==null && other.getTelephone()==null) || 
             (this.telephone!=null &&
              this.telephone.equals(other.getTelephone()))) &&
            ((this.winOrderDate==null && other.getWinOrderDate()==null) || 
             (this.winOrderDate!=null &&
              this.winOrderDate.equals(other.getWinOrderDate())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getExtensionData() != null) {
            _hashCode += getExtensionData().hashCode();
        }
        if (getBrandID() != null) {
            _hashCode += getBrandID().hashCode();
        }
        if (getCarStyleID() != null) {
            _hashCode += getCarStyleID().hashCode();
        }
        _hashCode += getCityID();
        _hashCode += getConsiderationID();
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getDMSCreateDateTime() != null) {
            _hashCode += getDMSCreateDateTime().hashCode();
        }
        if (getDMSCustomerID() != null) {
            _hashCode += getDMSCustomerID().hashCode();
        }
        if (getDMSFollowUpDateTime() != null) {
            _hashCode += getDMSFollowUpDateTime().hashCode();
        }
        if (getDMSOpportunityLevelID() != null) {
            _hashCode += getDMSOpportunityLevelID().hashCode();
        }
        if (getDMSSalesConsultant() != null) {
            _hashCode += getDMSSalesConsultant().hashCode();
        }
        if (getDealerCode() != null) {
            _hashCode += getDealerCode().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getGiveUpDate() != null) {
            _hashCode += getGiveUpDate().hashCode();
        }
        _hashCode += getID();
        if (getModelID() != null) {
            _hashCode += getModelID().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOtherGiveUpReason() != null) {
            _hashCode += getOtherGiveUpReason().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        _hashCode += getPotentialCustomerID();
        _hashCode += getProvinceID();
        _hashCode += getStatus();
        if (getTelephone() != null) {
            _hashCode += getTelephone().hashCode();
        }
        if (getWinOrderDate() != null) {
            _hashCode += getWinOrderDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DCC_DMSStatus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "DCC_DMSStatus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extensionData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ExtensionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ExtensionDataObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BrandID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carStyleID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CarStyleID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("considerationID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ConsiderationID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CreateDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DMSCreateDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DMSCreateDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DMSCustomerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DMSCustomerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DMSFollowUpDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DMSFollowUpDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DMSOpportunityLevelID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DMSOpportunityLevelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DMSSalesConsultant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DMSSalesConsultant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giveUpDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GiveUpDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ModelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherGiveUpReason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OtherGiveUpReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potentialCustomerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PotentialCustomerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provinceID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ProvinceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telephone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Telephone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("winOrderDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "WinOrderDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
