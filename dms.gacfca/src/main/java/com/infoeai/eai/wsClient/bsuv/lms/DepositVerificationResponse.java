/**
 * DepositVerificationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class DepositVerificationResponse  implements java.io.Serializable {
    private double VERIFICATION_AMOUNT;

    private java.util.Date VERIFICATION_DATE;

    private boolean VERIFICATION_TIMEOUT;

    private boolean VERIFIED;

    public DepositVerificationResponse() {
    }

    public DepositVerificationResponse(
           double VERIFICATION_AMOUNT,
           java.util.Date VERIFICATION_DATE,
           boolean VERIFICATION_TIMEOUT,
           boolean VERIFIED) {
           this.VERIFICATION_AMOUNT = VERIFICATION_AMOUNT;
           this.VERIFICATION_DATE = VERIFICATION_DATE;
           this.VERIFICATION_TIMEOUT = VERIFICATION_TIMEOUT;
           this.VERIFIED = VERIFIED;
    }


    /**
     * Gets the VERIFICATION_AMOUNT value for this DepositVerificationResponse.
     * 
     * @return VERIFICATION_AMOUNT
     */
    public double getVERIFICATION_AMOUNT() {
        return VERIFICATION_AMOUNT;
    }


    /**
     * Sets the VERIFICATION_AMOUNT value for this DepositVerificationResponse.
     * 
     * @param VERIFICATION_AMOUNT
     */
    public void setVERIFICATION_AMOUNT(double VERIFICATION_AMOUNT) {
        this.VERIFICATION_AMOUNT = VERIFICATION_AMOUNT;
    }


    /**
     * Gets the VERIFICATION_DATE value for this DepositVerificationResponse.
     * 
     * @return VERIFICATION_DATE
     */
    public java.util.Date getVERIFICATION_DATE() {
        return VERIFICATION_DATE;
    }


    /**
     * Sets the VERIFICATION_DATE value for this DepositVerificationResponse.
     * 
     * @param VERIFICATION_DATE
     */
    public void setVERIFICATION_DATE(java.util.Date VERIFICATION_DATE) {
        this.VERIFICATION_DATE = VERIFICATION_DATE;
    }


    /**
     * Gets the VERIFICATION_TIMEOUT value for this DepositVerificationResponse.
     * 
     * @return VERIFICATION_TIMEOUT
     */
    public boolean isVERIFICATION_TIMEOUT() {
        return VERIFICATION_TIMEOUT;
    }


    /**
     * Sets the VERIFICATION_TIMEOUT value for this DepositVerificationResponse.
     * 
     * @param VERIFICATION_TIMEOUT
     */
    public void setVERIFICATION_TIMEOUT(boolean VERIFICATION_TIMEOUT) {
        this.VERIFICATION_TIMEOUT = VERIFICATION_TIMEOUT;
    }


    /**
     * Gets the VERIFIED value for this DepositVerificationResponse.
     * 
     * @return VERIFIED
     */
    public boolean isVERIFIED() {
        return VERIFIED;
    }


    /**
     * Sets the VERIFIED value for this DepositVerificationResponse.
     * 
     * @param VERIFIED
     */
    public void setVERIFIED(boolean VERIFIED) {
        this.VERIFIED = VERIFIED;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepositVerificationResponse)) return false;
        DepositVerificationResponse other = (DepositVerificationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.VERIFICATION_AMOUNT == other.getVERIFICATION_AMOUNT() &&
            ((this.VERIFICATION_DATE==null && other.getVERIFICATION_DATE()==null) || 
             (this.VERIFICATION_DATE!=null &&
              this.VERIFICATION_DATE.equals(other.getVERIFICATION_DATE()))) &&
            this.VERIFICATION_TIMEOUT == other.isVERIFICATION_TIMEOUT() &&
            this.VERIFIED == other.isVERIFIED();
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
        _hashCode += new Double(getVERIFICATION_AMOUNT()).hashCode();
        if (getVERIFICATION_DATE() != null) {
            _hashCode += getVERIFICATION_DATE().hashCode();
        }
        _hashCode += (isVERIFICATION_TIMEOUT() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isVERIFIED() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepositVerificationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERIFICATION_AMOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "VERIFICATION_AMOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERIFICATION_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "VERIFICATION_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERIFICATION_TIMEOUT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "VERIFICATION_TIMEOUT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERIFIED");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "VERIFIED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
