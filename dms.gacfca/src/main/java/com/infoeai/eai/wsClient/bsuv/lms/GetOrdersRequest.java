/**
 * GetOrdersRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class GetOrdersRequest  implements java.io.Serializable {
    private java.lang.String FROM;

    private java.lang.String TO;

    public GetOrdersRequest() {
    }

    public GetOrdersRequest(
           java.lang.String FROM,
           java.lang.String TO) {
           this.FROM = FROM;
           this.TO = TO;
    }


    /**
     * Gets the FROM value for this GetOrdersRequest.
     * 
     * @return FROM
     */
    public java.lang.String getFROM() {
        return FROM;
    }


    /**
     * Sets the FROM value for this GetOrdersRequest.
     * 
     * @param FROM
     */
    public void setFROM(java.lang.String FROM) {
        this.FROM = FROM;
    }


    /**
     * Gets the TO value for this GetOrdersRequest.
     * 
     * @return TO
     */
    public java.lang.String getTO() {
        return TO;
    }


    /**
     * Sets the TO value for this GetOrdersRequest.
     * 
     * @param TO
     */
    public void setTO(java.lang.String TO) {
        this.TO = TO;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOrdersRequest)) return false;
        GetOrdersRequest other = (GetOrdersRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.FROM==null && other.getFROM()==null) || 
             (this.FROM!=null &&
              this.FROM.equals(other.getFROM()))) &&
            ((this.TO==null && other.getTO()==null) || 
             (this.TO!=null &&
              this.TO.equals(other.getTO())));
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
        if (getFROM() != null) {
            _hashCode += getFROM().hashCode();
        }
        if (getTO() != null) {
            _hashCode += getTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOrdersRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">GetOrdersRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FROM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "FROM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "TO"));
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
