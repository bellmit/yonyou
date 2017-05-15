/**
 * EcCheckFeedBackVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms;

@SuppressWarnings("serial")
public class EcCheckFeedBackVO  implements java.io.Serializable {
    private java.lang.String DEALER_CODE;

    private java.lang.String DEALER_NAME;

    private java.lang.String TEL;

    private java.lang.String SOLD_BY;

    private java.lang.String SOLD_MOBILE;

    private int IS_HIT_SINGLE;

    public EcCheckFeedBackVO() {
    }

    public EcCheckFeedBackVO(
           java.lang.String DEALER_CODE,
           java.lang.String DEALER_NAME,
           java.lang.String TEL,
           java.lang.String SOLD_BY,
           java.lang.String SOLD_MOBILE,
           int IS_HIT_SINGLE) {
           this.DEALER_CODE = DEALER_CODE;
           this.DEALER_NAME = DEALER_NAME;
           this.TEL = TEL;
           this.SOLD_BY = SOLD_BY;
           this.SOLD_MOBILE = SOLD_MOBILE;
           this.IS_HIT_SINGLE = IS_HIT_SINGLE;
    }


    /**
     * Gets the DEALER_CODE value for this EcCheckFeedBackVO.
     * 
     * @return DEALER_CODE
     */
    public java.lang.String getDEALER_CODE() {
        return DEALER_CODE;
    }


    /**
     * Sets the DEALER_CODE value for this EcCheckFeedBackVO.
     * 
     * @param DEALER_CODE
     */
    public void setDEALER_CODE(java.lang.String DEALER_CODE) {
        this.DEALER_CODE = DEALER_CODE;
    }


    /**
     * Gets the DEALER_NAME value for this EcCheckFeedBackVO.
     * 
     * @return DEALER_NAME
     */
    public java.lang.String getDEALER_NAME() {
        return DEALER_NAME;
    }


    /**
     * Sets the DEALER_NAME value for this EcCheckFeedBackVO.
     * 
     * @param DEALER_NAME
     */
    public void setDEALER_NAME(java.lang.String DEALER_NAME) {
        this.DEALER_NAME = DEALER_NAME;
    }


    /**
     * Gets the TEL value for this EcCheckFeedBackVO.
     * 
     * @return TEL
     */
    public java.lang.String getTEL() {
        return TEL;
    }


    /**
     * Sets the TEL value for this EcCheckFeedBackVO.
     * 
     * @param TEL
     */
    public void setTEL(java.lang.String TEL) {
        this.TEL = TEL;
    }


    /**
     * Gets the SOLD_BY value for this EcCheckFeedBackVO.
     * 
     * @return SOLD_BY
     */
    public java.lang.String getSOLD_BY() {
        return SOLD_BY;
    }


    /**
     * Sets the SOLD_BY value for this EcCheckFeedBackVO.
     * 
     * @param SOLD_BY
     */
    public void setSOLD_BY(java.lang.String SOLD_BY) {
        this.SOLD_BY = SOLD_BY;
    }


    /**
     * Gets the SOLD_MOBILE value for this EcCheckFeedBackVO.
     * 
     * @return SOLD_MOBILE
     */
    public java.lang.String getSOLD_MOBILE() {
        return SOLD_MOBILE;
    }


    /**
     * Sets the SOLD_MOBILE value for this EcCheckFeedBackVO.
     * 
     * @param SOLD_MOBILE
     */
    public void setSOLD_MOBILE(java.lang.String SOLD_MOBILE) {
        this.SOLD_MOBILE = SOLD_MOBILE;
    }


    /**
     * Gets the IS_HIT_SINGLE value for this EcCheckFeedBackVO.
     * 
     * @return IS_HIT_SINGLE
     */
    public int getIS_HIT_SINGLE() {
        return IS_HIT_SINGLE;
    }


    /**
     * Sets the IS_HIT_SINGLE value for this EcCheckFeedBackVO.
     * 
     * @param IS_HIT_SINGLE
     */
    public void setIS_HIT_SINGLE(int IS_HIT_SINGLE) {
        this.IS_HIT_SINGLE = IS_HIT_SINGLE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EcCheckFeedBackVO)) return false;
        EcCheckFeedBackVO other = (EcCheckFeedBackVO) obj;
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
              this.TEL.equals(other.getTEL()))) &&
            ((this.SOLD_BY==null && other.getSOLD_BY()==null) || 
             (this.SOLD_BY!=null &&
              this.SOLD_BY.equals(other.getSOLD_BY()))) &&
            ((this.SOLD_MOBILE==null && other.getSOLD_MOBILE()==null) || 
             (this.SOLD_MOBILE!=null &&
              this.SOLD_MOBILE.equals(other.getSOLD_MOBILE()))) &&
            this.IS_HIT_SINGLE == other.getIS_HIT_SINGLE();
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
        if (getSOLD_BY() != null) {
            _hashCode += getSOLD_BY().hashCode();
        }
        if (getSOLD_MOBILE() != null) {
            _hashCode += getSOLD_MOBILE().hashCode();
        }
        _hashCode += getIS_HIT_SINGLE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EcCheckFeedBackVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org", "ecCheckFeedBackVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TEL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TEL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SOLD_BY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SOLD_BY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SOLD_MOBILE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SOLD_MOBILE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_HIT_SINGLE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IS_HIT_SINGLE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
