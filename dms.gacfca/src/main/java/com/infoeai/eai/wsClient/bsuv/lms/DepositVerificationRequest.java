/**
 * DepositVerificationRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class DepositVerificationRequest  implements java.io.Serializable {
    private java.lang.String EC_ORDER_NO;

    public DepositVerificationRequest() {
    }

    public DepositVerificationRequest(
           java.lang.String EC_ORDER_NO) {
           this.EC_ORDER_NO = EC_ORDER_NO;
    }


    /**
     * Gets the EC_ORDER_NO value for this DepositVerificationRequest.
     * 
     * @return EC_ORDER_NO
     */
    public java.lang.String getEC_ORDER_NO() {
        return EC_ORDER_NO;
    }


    /**
     * Sets the EC_ORDER_NO value for this DepositVerificationRequest.
     * 
     * @param EC_ORDER_NO
     */
    public void setEC_ORDER_NO(java.lang.String EC_ORDER_NO) {
        this.EC_ORDER_NO = EC_ORDER_NO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepositVerificationRequest)) return false;
        DepositVerificationRequest other = (DepositVerificationRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.EC_ORDER_NO==null && other.getEC_ORDER_NO()==null) || 
             (this.EC_ORDER_NO!=null &&
              this.EC_ORDER_NO.equals(other.getEC_ORDER_NO())));
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
        if (getEC_ORDER_NO() != null) {
            _hashCode += getEC_ORDER_NO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepositVerificationRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">DepositVerificationRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EC_ORDER_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "EC_ORDER_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
