/**
 * DCC_DMSDealer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.lms.dmswebservice;

public class DCC_DMSDealer  implements java.io.Serializable {
    private ExtensionDataObject extensionData;

    private java.lang.String address;

    private java.lang.Integer cityID;

    private java.lang.String dealerABCN;

    private java.lang.String dealerABEN;

    private java.lang.Integer dealerCode;

    private java.lang.String dealerName;

    private java.lang.Integer reallyCityID;

    private java.lang.String serviceTel;

    private java.lang.Integer status;

    public DCC_DMSDealer() {
    }

    public DCC_DMSDealer(
           ExtensionDataObject extensionData,
           java.lang.String address,
           java.lang.Integer cityID,
           java.lang.String dealerABCN,
           java.lang.String dealerABEN,
           java.lang.Integer dealerCode,
           java.lang.String dealerName,
           java.lang.Integer reallyCityID,
           java.lang.String serviceTel,
           java.lang.Integer status) {
           this.extensionData = extensionData;
           this.address = address;
           this.cityID = cityID;
           this.dealerABCN = dealerABCN;
           this.dealerABEN = dealerABEN;
           this.dealerCode = dealerCode;
           this.dealerName = dealerName;
           this.reallyCityID = reallyCityID;
           this.serviceTel = serviceTel;
           this.status = status;
    }


    /**
     * Gets the extensionData value for this DCC_DMSDealer.
     * 
     * @return extensionData
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }


    /**
     * Sets the extensionData value for this DCC_DMSDealer.
     * 
     * @param extensionData
     */
    public void setExtensionData(ExtensionDataObject extensionData) {
        this.extensionData = extensionData;
    }


    /**
     * Gets the address value for this DCC_DMSDealer.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this DCC_DMSDealer.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the cityID value for this DCC_DMSDealer.
     * 
     * @return cityID
     */
    public java.lang.Integer getCityID() {
        return cityID;
    }


    /**
     * Sets the cityID value for this DCC_DMSDealer.
     * 
     * @param cityID
     */
    public void setCityID(java.lang.Integer cityID) {
        this.cityID = cityID;
    }


    /**
     * Gets the dealerABCN value for this DCC_DMSDealer.
     * 
     * @return dealerABCN
     */
    public java.lang.String getDealerABCN() {
        return dealerABCN;
    }


    /**
     * Sets the dealerABCN value for this DCC_DMSDealer.
     * 
     * @param dealerABCN
     */
    public void setDealerABCN(java.lang.String dealerABCN) {
        this.dealerABCN = dealerABCN;
    }


    /**
     * Gets the dealerABEN value for this DCC_DMSDealer.
     * 
     * @return dealerABEN
     */
    public java.lang.String getDealerABEN() {
        return dealerABEN;
    }


    /**
     * Sets the dealerABEN value for this DCC_DMSDealer.
     * 
     * @param dealerABEN
     */
    public void setDealerABEN(java.lang.String dealerABEN) {
        this.dealerABEN = dealerABEN;
    }


    /**
     * Gets the dealerCode value for this DCC_DMSDealer.
     * 
     * @return dealerCode
     */
    public java.lang.Integer getDealerCode() {
        return dealerCode;
    }


    /**
     * Sets the dealerCode value for this DCC_DMSDealer.
     * 
     * @param dealerCode
     */
    public void setDealerCode(java.lang.Integer dealerCode) {
        this.dealerCode = dealerCode;
    }


    /**
     * Gets the dealerName value for this DCC_DMSDealer.
     * 
     * @return dealerName
     */
    public java.lang.String getDealerName() {
        return dealerName;
    }


    /**
     * Sets the dealerName value for this DCC_DMSDealer.
     * 
     * @param dealerName
     */
    public void setDealerName(java.lang.String dealerName) {
        this.dealerName = dealerName;
    }


    /**
     * Gets the reallyCityID value for this DCC_DMSDealer.
     * 
     * @return reallyCityID
     */
    public java.lang.Integer getReallyCityID() {
        return reallyCityID;
    }


    /**
     * Sets the reallyCityID value for this DCC_DMSDealer.
     * 
     * @param reallyCityID
     */
    public void setReallyCityID(java.lang.Integer reallyCityID) {
        this.reallyCityID = reallyCityID;
    }


    /**
     * Gets the serviceTel value for this DCC_DMSDealer.
     * 
     * @return serviceTel
     */
    public java.lang.String getServiceTel() {
        return serviceTel;
    }


    /**
     * Sets the serviceTel value for this DCC_DMSDealer.
     * 
     * @param serviceTel
     */
    public void setServiceTel(java.lang.String serviceTel) {
        this.serviceTel = serviceTel;
    }


    /**
     * Gets the status value for this DCC_DMSDealer.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DCC_DMSDealer.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DCC_DMSDealer)) return false;
        DCC_DMSDealer other = (DCC_DMSDealer) obj;
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
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.cityID==null && other.getCityID()==null) || 
             (this.cityID!=null &&
              this.cityID.equals(other.getCityID()))) &&
            ((this.dealerABCN==null && other.getDealerABCN()==null) || 
             (this.dealerABCN!=null &&
              this.dealerABCN.equals(other.getDealerABCN()))) &&
            ((this.dealerABEN==null && other.getDealerABEN()==null) || 
             (this.dealerABEN!=null &&
              this.dealerABEN.equals(other.getDealerABEN()))) &&
            ((this.dealerCode==null && other.getDealerCode()==null) || 
             (this.dealerCode!=null &&
              this.dealerCode.equals(other.getDealerCode()))) &&
            ((this.dealerName==null && other.getDealerName()==null) || 
             (this.dealerName!=null &&
              this.dealerName.equals(other.getDealerName()))) &&
            ((this.reallyCityID==null && other.getReallyCityID()==null) || 
             (this.reallyCityID!=null &&
              this.reallyCityID.equals(other.getReallyCityID()))) &&
            ((this.serviceTel==null && other.getServiceTel()==null) || 
             (this.serviceTel!=null &&
              this.serviceTel.equals(other.getServiceTel()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getCityID() != null) {
            _hashCode += getCityID().hashCode();
        }
        if (getDealerABCN() != null) {
            _hashCode += getDealerABCN().hashCode();
        }
        if (getDealerABEN() != null) {
            _hashCode += getDealerABEN().hashCode();
        }
        if (getDealerCode() != null) {
            _hashCode += getDealerCode().hashCode();
        }
        if (getDealerName() != null) {
            _hashCode += getDealerName().hashCode();
        }
        if (getReallyCityID() != null) {
            _hashCode += getReallyCityID().hashCode();
        }
        if (getServiceTel() != null) {
            _hashCode += getServiceTel().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DCC_DMSDealer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "DCC_DMSDealer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extensionData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ExtensionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ExtensionDataObject"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerABCN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealerABCN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerABEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealerABEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DealerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reallyCityID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ReallyCityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceTel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceTel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
