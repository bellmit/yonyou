/**
 * TrailCustVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms;

@SuppressWarnings("serial")
public class TrailCustVO  implements java.io.Serializable {
    private java.lang.String EC_ORDER_NO;

    private java.lang.String DEALER_CODE;

    private java.lang.String TEL;

    private java.util.Date TRAIL_DATE;

    public TrailCustVO() {
    }

    public TrailCustVO(
           java.lang.String EC_ORDER_NO,
           java.lang.String DEALER_CODE,
           java.lang.String TEL,
           java.util.Date TRAIL_DATE) {
           this.EC_ORDER_NO = EC_ORDER_NO;
           this.DEALER_CODE = DEALER_CODE;
           this.TEL = TEL;
           this.TRAIL_DATE = TRAIL_DATE;
    }


    /**
     * Gets the EC_ORDER_NO value for this TrailCustVO.
     * 
     * @return EC_ORDER_NO
     */
    public java.lang.String getEC_ORDER_NO() {
        return EC_ORDER_NO;
    }


    /**
     * Sets the EC_ORDER_NO value for this TrailCustVO.
     * 
     * @param EC_ORDER_NO
     */
    public void setEC_ORDER_NO(java.lang.String EC_ORDER_NO) {
        this.EC_ORDER_NO = EC_ORDER_NO;
    }


    /**
     * Gets the DEALER_CODE value for this TrailCustVO.
     * 
     * @return DEALER_CODE
     */
    public java.lang.String getDEALER_CODE() {
        return DEALER_CODE;
    }


    /**
     * Sets the DEALER_CODE value for this TrailCustVO.
     * 
     * @param DEALER_CODE
     */
    public void setDEALER_CODE(java.lang.String DEALER_CODE) {
        this.DEALER_CODE = DEALER_CODE;
    }


    /**
     * Gets the TEL value for this TrailCustVO.
     * 
     * @return TEL
     */
    public java.lang.String getTEL() {
        return TEL;
    }


    /**
     * Sets the TEL value for this TrailCustVO.
     * 
     * @param TEL
     */
    public void setTEL(java.lang.String TEL) {
        this.TEL = TEL;
    }


    /**
     * Gets the TRAIL_DATE value for this TrailCustVO.
     * 
     * @return TRAIL_DATE
     */
    public java.util.Date getTRAIL_DATE() {
        return TRAIL_DATE;
    }


    /**
     * Sets the TRAIL_DATE value for this TrailCustVO.
     * 
     * @param TRAIL_DATE
     */
    public void setTRAIL_DATE(java.util.Date TRAIL_DATE) {
        this.TRAIL_DATE = TRAIL_DATE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TrailCustVO)) return false;
        TrailCustVO other = (TrailCustVO) obj;
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
              this.EC_ORDER_NO.equals(other.getEC_ORDER_NO()))) &&
            ((this.DEALER_CODE==null && other.getDEALER_CODE()==null) || 
             (this.DEALER_CODE!=null &&
              this.DEALER_CODE.equals(other.getDEALER_CODE()))) &&
            ((this.TEL==null && other.getTEL()==null) || 
             (this.TEL!=null &&
              this.TEL.equals(other.getTEL()))) &&
            ((this.TRAIL_DATE==null && other.getTRAIL_DATE()==null) || 
             (this.TRAIL_DATE!=null &&
              this.TRAIL_DATE.equals(other.getTRAIL_DATE())));
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
        if (getDEALER_CODE() != null) {
            _hashCode += getDEALER_CODE().hashCode();
        }
        if (getTEL() != null) {
            _hashCode += getTEL().hashCode();
        }
        if (getTRAIL_DATE() != null) {
            _hashCode += getTRAIL_DATE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TrailCustVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org", "trailCustVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EC_ORDER_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EC_ORDER_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TEL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TEL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRAIL_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TRAIL_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
