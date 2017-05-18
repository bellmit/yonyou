/**
 * ZlvsdwsL31Matnr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.getSparePartsList;

public class ZlvsdwsL31Matnr  implements java.io.Serializable {
    private java.lang.String IMatnr;

    private java.math.BigDecimal IQtyreq;

    public ZlvsdwsL31Matnr() {
    }

    public ZlvsdwsL31Matnr(
           java.lang.String IMatnr,
           java.math.BigDecimal IQtyreq) {
           this.IMatnr = IMatnr;
           this.IQtyreq = IQtyreq;
    }


    /**
     * Gets the IMatnr value for this ZlvsdwsL31Matnr.
     * 
     * @return IMatnr
     */
    public java.lang.String getIMatnr() {
        return IMatnr;
    }


    /**
     * Sets the IMatnr value for this ZlvsdwsL31Matnr.
     * 
     * @param IMatnr
     */
    public void setIMatnr(java.lang.String IMatnr) {
        this.IMatnr = IMatnr;
    }


    /**
     * Gets the IQtyreq value for this ZlvsdwsL31Matnr.
     * 
     * @return IQtyreq
     */
    public java.math.BigDecimal getIQtyreq() {
        return IQtyreq;
    }


    /**
     * Sets the IQtyreq value for this ZlvsdwsL31Matnr.
     * 
     * @param IQtyreq
     */
    public void setIQtyreq(java.math.BigDecimal IQtyreq) {
        this.IQtyreq = IQtyreq;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsL31Matnr)) return false;
        ZlvsdwsL31Matnr other = (ZlvsdwsL31Matnr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IMatnr==null && other.getIMatnr()==null) || 
             (this.IMatnr!=null &&
              this.IMatnr.equals(other.getIMatnr()))) &&
            ((this.IQtyreq==null && other.getIQtyreq()==null) || 
             (this.IQtyreq!=null &&
              this.IQtyreq.equals(other.getIQtyreq())));
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
        if (getIMatnr() != null) {
            _hashCode += getIMatnr().hashCode();
        }
        if (getIQtyreq() != null) {
            _hashCode += getIQtyreq().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsL31Matnr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsL31Matnr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IMatnr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IMatnr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IQtyreq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IQtyreq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
