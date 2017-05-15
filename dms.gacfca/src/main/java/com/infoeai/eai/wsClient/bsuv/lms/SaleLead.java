/**
 * SaleLead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class SaleLead  implements java.io.Serializable {
    private java.lang.String DEALER_CODE;

    private java.lang.String DEALER_NAME;

    private java.lang.String TEL;

    public SaleLead() {
    }

    public SaleLead(
           java.lang.String DEALER_CODE,
           java.lang.String DEALER_NAME,
           java.lang.String TEL) {
           this.DEALER_CODE = DEALER_CODE;
           this.DEALER_NAME = DEALER_NAME;
           this.TEL = TEL;
    }


    /**
     * Gets the DEALER_CODE value for this SaleLead.
     * 
     * @return DEALER_CODE
     */
    public java.lang.String getDEALER_CODE() {
        return DEALER_CODE;
    }


    /**
     * Sets the DEALER_CODE value for this SaleLead.
     * 
     * @param DEALER_CODE
     */
    public void setDEALER_CODE(java.lang.String DEALER_CODE) {
        this.DEALER_CODE = DEALER_CODE;
    }


    /**
     * Gets the DEALER_NAME value for this SaleLead.
     * 
     * @return DEALER_NAME
     */
    public java.lang.String getDEALER_NAME() {
        return DEALER_NAME;
    }


    /**
     * Sets the DEALER_NAME value for this SaleLead.
     * 
     * @param DEALER_NAME
     */
    public void setDEALER_NAME(java.lang.String DEALER_NAME) {
        this.DEALER_NAME = DEALER_NAME;
    }


    /**
     * Gets the TEL value for this SaleLead.
     * 
     * @return TEL
     */
    public java.lang.String getTEL() {
        return TEL;
    }


    /**
     * Sets the TEL value for this SaleLead.
     * 
     * @param TEL
     */
    public void setTEL(java.lang.String TEL) {
        this.TEL = TEL;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SaleLead)) return false;
        SaleLead other = (SaleLead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.DEALER_CODE==null && other.getDEALER_CODE()==null) || 
             (this.DEALER_CODE!=null &&
              this.DEALER_CODE.equals(other.getDEALER_CODE()))) &&
            ((this.DEALER_NAME==null && other.getDEALER_NAME()==null) || 
             (this.DEALER_NAME!=null &&
              this.DEALER_NAME.equals(other.getDEALER_NAME()))) &&
            ((this.TEL==null && other.getTEL()==null) || 
             (this.TEL!=null &&
              this.TEL.equals(other.getTEL())));
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
        if (getDEALER_CODE() != null) {
            _hashCode += getDEALER_CODE().hashCode();
        }
        if (getDEALER_NAME() != null) {
            _hashCode += getDEALER_NAME().hashCode();
        }
        if (getTEL() != null) {
            _hashCode += getTEL().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SaleLead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "SaleLead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DEALER_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DEALER_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TEL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "TEL"));
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
