/**
 * ZlvsdwsGf21Pri.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation;

public class ZlvsdwsGf21Pri  implements java.io.Serializable {
    private java.lang.String vbeln;

    private java.lang.String matnr;

    private java.math.BigDecimal kbetr;

    private java.math.BigDecimal netwr;

    public ZlvsdwsGf21Pri() {
    }

    public ZlvsdwsGf21Pri(
           java.lang.String vbeln,
           java.lang.String matnr,
           java.math.BigDecimal kbetr,
           java.math.BigDecimal netwr) {
           this.vbeln = vbeln;
           this.matnr = matnr;
           this.kbetr = kbetr;
           this.netwr = netwr;
    }


    /**
     * Gets the vbeln value for this ZlvsdwsGf21Pri.
     * 
     * @return vbeln
     */
    public java.lang.String getVbeln() {
        return vbeln;
    }


    /**
     * Sets the vbeln value for this ZlvsdwsGf21Pri.
     * 
     * @param vbeln
     */
    public void setVbeln(java.lang.String vbeln) {
        this.vbeln = vbeln;
    }


    /**
     * Gets the matnr value for this ZlvsdwsGf21Pri.
     * 
     * @return matnr
     */
    public java.lang.String getMatnr() {
        return matnr;
    }


    /**
     * Sets the matnr value for this ZlvsdwsGf21Pri.
     * 
     * @param matnr
     */
    public void setMatnr(java.lang.String matnr) {
        this.matnr = matnr;
    }


    /**
     * Gets the kbetr value for this ZlvsdwsGf21Pri.
     * 
     * @return kbetr
     */
    public java.math.BigDecimal getKbetr() {
        return kbetr;
    }


    /**
     * Sets the kbetr value for this ZlvsdwsGf21Pri.
     * 
     * @param kbetr
     */
    public void setKbetr(java.math.BigDecimal kbetr) {
        this.kbetr = kbetr;
    }


    /**
     * Gets the netwr value for this ZlvsdwsGf21Pri.
     * 
     * @return netwr
     */
    public java.math.BigDecimal getNetwr() {
        return netwr;
    }


    /**
     * Sets the netwr value for this ZlvsdwsGf21Pri.
     * 
     * @param netwr
     */
    public void setNetwr(java.math.BigDecimal netwr) {
        this.netwr = netwr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsGf21Pri)) return false;
        ZlvsdwsGf21Pri other = (ZlvsdwsGf21Pri) obj;
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
              this.vbeln.equals(other.getVbeln()))) &&
            ((this.matnr==null && other.getMatnr()==null) || 
             (this.matnr!=null &&
              this.matnr.equals(other.getMatnr()))) &&
            ((this.kbetr==null && other.getKbetr()==null) || 
             (this.kbetr!=null &&
              this.kbetr.equals(other.getKbetr()))) &&
            ((this.netwr==null && other.getNetwr()==null) || 
             (this.netwr!=null &&
              this.netwr.equals(other.getNetwr())));
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
        if (getMatnr() != null) {
            _hashCode += getMatnr().hashCode();
        }
        if (getKbetr() != null) {
            _hashCode += getKbetr().hashCode();
        }
        if (getNetwr() != null) {
            _hashCode += getNetwr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsGf21Pri.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf21Pri"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vbeln");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Vbeln"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matnr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Matnr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kbetr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Kbetr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netwr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Netwr"));
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
