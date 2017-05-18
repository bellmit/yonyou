/**
 * ZrwsSOrderInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ecPartOrder;

public class ZrwsSOrderInput  implements java.io.Serializable {
    private java.lang.String partNo;

    private java.lang.String reqQty;

    public ZrwsSOrderInput() {
    }

    public ZrwsSOrderInput(
           java.lang.String partNo,
           java.lang.String reqQty) {
           this.partNo = partNo;
           this.reqQty = reqQty;
    }


    /**
     * Gets the partNo value for this ZrwsSOrderInput.
     * 
     * @return partNo
     */
    public java.lang.String getPartNo() {
        return partNo;
    }


    /**
     * Sets the partNo value for this ZrwsSOrderInput.
     * 
     * @param partNo
     */
    public void setPartNo(java.lang.String partNo) {
        this.partNo = partNo;
    }


    /**
     * Gets the reqQty value for this ZrwsSOrderInput.
     * 
     * @return reqQty
     */
    public java.lang.String getReqQty() {
        return reqQty;
    }


    /**
     * Sets the reqQty value for this ZrwsSOrderInput.
     * 
     * @param reqQty
     */
    public void setReqQty(java.lang.String reqQty) {
        this.reqQty = reqQty;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZrwsSOrderInput)) return false;
        ZrwsSOrderInput other = (ZrwsSOrderInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.partNo==null && other.getPartNo()==null) || 
             (this.partNo!=null &&
              this.partNo.equals(other.getPartNo()))) &&
            ((this.reqQty==null && other.getReqQty()==null) || 
             (this.reqQty!=null &&
              this.reqQty.equals(other.getReqQty())));
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
        if (getPartNo() != null) {
            _hashCode += getPartNo().hashCode();
        }
        if (getReqQty() != null) {
            _hashCode += getReqQty().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZrwsSOrderInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZrwsSOrderInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PartNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ReqQty"));
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
