/**
 * CustomerBindingFeedbackPO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.wx.service4wx;

public class CustomerBindingFeedbackPO  implements java.io.Serializable {
    private java.lang.String vin;

    private java.lang.String dealerCode;

    private java.lang.String serviceAdvisor;

    private java.lang.String employeeName;

    private java.lang.String mobile;

    private int boundType;

    public CustomerBindingFeedbackPO() {
    }

    public CustomerBindingFeedbackPO(
           java.lang.String vin,
           java.lang.String dealerCode,
           java.lang.String serviceAdvisor,
           java.lang.String employeeName,
           java.lang.String mobile,
           int boundType) {
           this.vin = vin;
           this.dealerCode = dealerCode;
           this.serviceAdvisor = serviceAdvisor;
           this.employeeName = employeeName;
           this.mobile = mobile;
           this.boundType = boundType;
    }


    /**
     * Gets the vin value for this CustomerBindingFeedbackPO.
     * 
     * @return vin
     */
    public java.lang.String getVin() {
        return vin;
    }


    /**
     * Sets the vin value for this CustomerBindingFeedbackPO.
     * 
     * @param vin
     */
    public void setVin(java.lang.String vin) {
        this.vin = vin;
    }


    /**
     * Gets the dealerCode value for this CustomerBindingFeedbackPO.
     * 
     * @return dealerCode
     */
    public java.lang.String getDealerCode() {
        return dealerCode;
    }


    /**
     * Sets the dealerCode value for this CustomerBindingFeedbackPO.
     * 
     * @param dealerCode
     */
    public void setDealerCode(java.lang.String dealerCode) {
        this.dealerCode = dealerCode;
    }


    /**
     * Gets the serviceAdvisor value for this CustomerBindingFeedbackPO.
     * 
     * @return serviceAdvisor
     */
    public java.lang.String getServiceAdvisor() {
        return serviceAdvisor;
    }


    /**
     * Sets the serviceAdvisor value for this CustomerBindingFeedbackPO.
     * 
     * @param serviceAdvisor
     */
    public void setServiceAdvisor(java.lang.String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor;
    }


    /**
     * Gets the employeeName value for this CustomerBindingFeedbackPO.
     * 
     * @return employeeName
     */
    public java.lang.String getEmployeeName() {
        return employeeName;
    }


    /**
     * Sets the employeeName value for this CustomerBindingFeedbackPO.
     * 
     * @param employeeName
     */
    public void setEmployeeName(java.lang.String employeeName) {
        this.employeeName = employeeName;
    }


    /**
     * Gets the mobile value for this CustomerBindingFeedbackPO.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this CustomerBindingFeedbackPO.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the boundType value for this CustomerBindingFeedbackPO.
     * 
     * @return boundType
     */
    public int getBoundType() {
        return boundType;
    }


    /**
     * Sets the boundType value for this CustomerBindingFeedbackPO.
     * 
     * @param boundType
     */
    public void setBoundType(int boundType) {
        this.boundType = boundType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerBindingFeedbackPO)) return false;
        CustomerBindingFeedbackPO other = (CustomerBindingFeedbackPO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vin==null && other.getVin()==null) || 
             (this.vin!=null &&
              this.vin.equals(other.getVin()))) &&
            ((this.dealerCode==null && other.getDealerCode()==null) || 
             (this.dealerCode!=null &&
              this.dealerCode.equals(other.getDealerCode()))) &&
            ((this.serviceAdvisor==null && other.getServiceAdvisor()==null) || 
             (this.serviceAdvisor!=null &&
              this.serviceAdvisor.equals(other.getServiceAdvisor()))) &&
            ((this.employeeName==null && other.getEmployeeName()==null) || 
             (this.employeeName!=null &&
              this.employeeName.equals(other.getEmployeeName()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            this.boundType == other.getBoundType();
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
        if (getVin() != null) {
            _hashCode += getVin().hashCode();
        }
        if (getDealerCode() != null) {
            _hashCode += getDealerCode().hashCode();
        }
        if (getServiceAdvisor() != null) {
            _hashCode += getServiceAdvisor().hashCode();
        }
        if (getEmployeeName() != null) {
            _hashCode += getEmployeeName().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        _hashCode += getBoundType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerBindingFeedbackPO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/service4wx/", "customerBindingFeedbackPO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceAdvisor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceAdvisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employeeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "employeeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("boundType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "boundType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
