/**
 * MmQueryUnSignedDeliverSAPVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QueryUnSignedDeliver;

public class MmQueryUnSignedDeliverSAPVO  implements java.io.Serializable {
    private java.lang.String entityCode;

    private java.lang.String deliveryOrderNo;

    private java.lang.String transNo;

    private java.lang.String sapOrderNo;

    private java.lang.String deliveryCompany;

    private java.lang.String orderRegeditNo;

    private java.lang.Integer isSigned;

    private java.util.Calendar deliveryTime;

    public MmQueryUnSignedDeliverSAPVO() {
    }

    public MmQueryUnSignedDeliverSAPVO(
           java.lang.String entityCode,
           java.lang.String deliveryOrderNo,
           java.lang.String transNo,
           java.lang.String sapOrderNo,
           java.lang.String deliveryCompany,
           java.lang.String orderRegeditNo,
           java.lang.Integer isSigned,
           java.util.Calendar deliveryTime) {
           this.entityCode = entityCode;
           this.deliveryOrderNo = deliveryOrderNo;
           this.transNo = transNo;
           this.sapOrderNo = sapOrderNo;
           this.deliveryCompany = deliveryCompany;
           this.orderRegeditNo = orderRegeditNo;
           this.isSigned = isSigned;
           this.deliveryTime = deliveryTime;
    }


    /**
     * Gets the entityCode value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return entityCode
     */
    public java.lang.String getEntityCode() {
        return entityCode;
    }


    /**
     * Sets the entityCode value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param entityCode
     */
    public void setEntityCode(java.lang.String entityCode) {
        this.entityCode = entityCode;
    }


    /**
     * Gets the deliveryOrderNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return deliveryOrderNo
     */
    public java.lang.String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }


    /**
     * Sets the deliveryOrderNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param deliveryOrderNo
     */
    public void setDeliveryOrderNo(java.lang.String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }


    /**
     * Gets the transNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return transNo
     */
    public java.lang.String getTransNo() {
        return transNo;
    }


    /**
     * Sets the transNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param transNo
     */
    public void setTransNo(java.lang.String transNo) {
        this.transNo = transNo;
    }


    /**
     * Gets the sapOrderNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return sapOrderNo
     */
    public java.lang.String getSapOrderNo() {
        return sapOrderNo;
    }


    /**
     * Sets the sapOrderNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param sapOrderNo
     */
    public void setSapOrderNo(java.lang.String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }


    /**
     * Gets the deliveryCompany value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return deliveryCompany
     */
    public java.lang.String getDeliveryCompany() {
        return deliveryCompany;
    }


    /**
     * Sets the deliveryCompany value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param deliveryCompany
     */
    public void setDeliveryCompany(java.lang.String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }


    /**
     * Gets the orderRegeditNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return orderRegeditNo
     */
    public java.lang.String getOrderRegeditNo() {
        return orderRegeditNo;
    }


    /**
     * Sets the orderRegeditNo value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param orderRegeditNo
     */
    public void setOrderRegeditNo(java.lang.String orderRegeditNo) {
        this.orderRegeditNo = orderRegeditNo;
    }


    /**
     * Gets the isSigned value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return isSigned
     */
    public java.lang.Integer getIsSigned() {
        return isSigned;
    }


    /**
     * Sets the isSigned value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param isSigned
     */
    public void setIsSigned(java.lang.Integer isSigned) {
        this.isSigned = isSigned;
    }


    /**
     * Gets the deliveryTime value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @return deliveryTime
     */
    public java.util.Calendar getDeliveryTime() {
        return deliveryTime;
    }


    /**
     * Sets the deliveryTime value for this MmQueryUnSignedDeliverSAPVO.
     * 
     * @param deliveryTime
     */
    public void setDeliveryTime(java.util.Calendar deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MmQueryUnSignedDeliverSAPVO)) return false;
        MmQueryUnSignedDeliverSAPVO other = (MmQueryUnSignedDeliverSAPVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.entityCode==null && other.getEntityCode()==null) || 
             (this.entityCode!=null &&
              this.entityCode.equals(other.getEntityCode()))) &&
            ((this.deliveryOrderNo==null && other.getDeliveryOrderNo()==null) || 
             (this.deliveryOrderNo!=null &&
              this.deliveryOrderNo.equals(other.getDeliveryOrderNo()))) &&
            ((this.transNo==null && other.getTransNo()==null) || 
             (this.transNo!=null &&
              this.transNo.equals(other.getTransNo()))) &&
            ((this.sapOrderNo==null && other.getSapOrderNo()==null) || 
             (this.sapOrderNo!=null &&
              this.sapOrderNo.equals(other.getSapOrderNo()))) &&
            ((this.deliveryCompany==null && other.getDeliveryCompany()==null) || 
             (this.deliveryCompany!=null &&
              this.deliveryCompany.equals(other.getDeliveryCompany()))) &&
            ((this.orderRegeditNo==null && other.getOrderRegeditNo()==null) || 
             (this.orderRegeditNo!=null &&
              this.orderRegeditNo.equals(other.getOrderRegeditNo()))) &&
            ((this.isSigned==null && other.getIsSigned()==null) || 
             (this.isSigned!=null &&
              this.isSigned.equals(other.getIsSigned()))) &&
            ((this.deliveryTime==null && other.getDeliveryTime()==null) || 
             (this.deliveryTime!=null &&
              this.deliveryTime.equals(other.getDeliveryTime())));
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
        if (getEntityCode() != null) {
            _hashCode += getEntityCode().hashCode();
        }
        if (getDeliveryOrderNo() != null) {
            _hashCode += getDeliveryOrderNo().hashCode();
        }
        if (getTransNo() != null) {
            _hashCode += getTransNo().hashCode();
        }
        if (getSapOrderNo() != null) {
            _hashCode += getSapOrderNo().hashCode();
        }
        if (getDeliveryCompany() != null) {
            _hashCode += getDeliveryCompany().hashCode();
        }
        if (getOrderRegeditNo() != null) {
            _hashCode += getOrderRegeditNo().hashCode();
        }
        if (getIsSigned() != null) {
            _hashCode += getIsSigned().hashCode();
        }
        if (getDeliveryTime() != null) {
            _hashCode += getDeliveryTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MmQueryUnSignedDeliverSAPVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "MmQueryUnSignedDeliverSAPVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "entityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryOrderNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "deliveryOrderNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "transNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sapOrderNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "sapOrderNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryCompany");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "deliveryCompany"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderRegeditNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "orderRegeditNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSigned");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "isSigned"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QueryUnSignedDeliver/", "deliveryTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
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
