/**
 * ZlvsdwsSjv01Vbeln.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.dealerDeliveryDocList;

public class ZlvsdwsSjv01Vbeln  implements java.io.Serializable {
    private java.lang.String vbeln;

    public ZlvsdwsSjv01Vbeln() {
    }

    public ZlvsdwsSjv01Vbeln(
           java.lang.String vbeln) {
           this.vbeln = vbeln;
    }


    /**
     * Gets the vbeln value for this ZlvsdwsSjv01Vbeln.
     * 
     * @return vbeln
     */
    public java.lang.String getVbeln() {
        return vbeln;
    }


    /**
     * Sets the vbeln value for this ZlvsdwsSjv01Vbeln.
     * 
     * @param vbeln
     */
    public void setVbeln(java.lang.String vbeln) {
        this.vbeln = vbeln;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsSjv01Vbeln)) return false;
        ZlvsdwsSjv01Vbeln other = (ZlvsdwsSjv01Vbeln) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vbeln==null && other.getVbeln()==null) || 
             (this.vbeln!=null &&
              this.vbeln.equals(other.getVbeln())));
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
        if (getVbeln() != null) {
            _hashCode += getVbeln().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsSjv01Vbeln.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsSjv01Vbeln"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vbeln");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Vbeln"));
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
