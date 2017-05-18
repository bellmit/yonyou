/**
 * ZlvsdwsL31Mat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.getSparePartsList;

public class ZlvsdwsL31Mat  implements java.io.Serializable {
    private java.lang.String matnrSap;

    private java.lang.String matnrDms;

    public ZlvsdwsL31Mat() {
    }

    public ZlvsdwsL31Mat(
           java.lang.String matnrSap,
           java.lang.String matnrDms) {
           this.matnrSap = matnrSap;
           this.matnrDms = matnrDms;
    }


    /**
     * Gets the matnrSap value for this ZlvsdwsL31Mat.
     * 
     * @return matnrSap
     */
    public java.lang.String getMatnrSap() {
        return matnrSap;
    }


    /**
     * Sets the matnrSap value for this ZlvsdwsL31Mat.
     * 
     * @param matnrSap
     */
    public void setMatnrSap(java.lang.String matnrSap) {
        this.matnrSap = matnrSap;
    }


    /**
     * Gets the matnrDms value for this ZlvsdwsL31Mat.
     * 
     * @return matnrDms
     */
    public java.lang.String getMatnrDms() {
        return matnrDms;
    }


    /**
     * Sets the matnrDms value for this ZlvsdwsL31Mat.
     * 
     * @param matnrDms
     */
    public void setMatnrDms(java.lang.String matnrDms) {
        this.matnrDms = matnrDms;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsL31Mat)) return false;
        ZlvsdwsL31Mat other = (ZlvsdwsL31Mat) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matnrSap==null && other.getMatnrSap()==null) || 
             (this.matnrSap!=null &&
              this.matnrSap.equals(other.getMatnrSap()))) &&
            ((this.matnrDms==null && other.getMatnrDms()==null) || 
             (this.matnrDms!=null &&
              this.matnrDms.equals(other.getMatnrDms())));
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
        if (getMatnrSap() != null) {
            _hashCode += getMatnrSap().hashCode();
        }
        if (getMatnrDms() != null) {
            _hashCode += getMatnrDms().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsL31Mat.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsL31Mat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matnrSap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MatnrSap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matnrDms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MatnrDms"));
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
