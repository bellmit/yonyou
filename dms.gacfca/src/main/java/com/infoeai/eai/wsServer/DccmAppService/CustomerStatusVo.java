/**
 * CustomerStatusVo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.DccmAppService;

@SuppressWarnings("serial")
public class CustomerStatusVo  implements java.io.Serializable {
    private java.lang.String uniquenessID;

    private java.lang.String fcaID;

    private java.lang.String oppLevelID;

    private java.lang.String giveUpType;

    private java.lang.String compareCar;

    private java.lang.String giveUpReason;

    private java.lang.String giveUpDate;

    private java.lang.String orderDate;

    private java.lang.String dealerCode;

    private java.lang.String buyCarDate;

    private java.lang.String updateDate;

    public CustomerStatusVo() {
    }

    public CustomerStatusVo(
           java.lang.String uniquenessID,
           java.lang.String fcaID,
           java.lang.String oppLevelID,
           java.lang.String giveUpType,
           java.lang.String compareCar,
           java.lang.String giveUpReason,
           java.lang.String giveUpDate,
           java.lang.String orderDate,
           java.lang.String dealerCode,
           java.lang.String buyCarDate,
           java.lang.String updateDate) {
           this.uniquenessID = uniquenessID;
           this.fcaID = fcaID;
           this.oppLevelID = oppLevelID;
           this.giveUpType = giveUpType;
           this.compareCar = compareCar;
           this.giveUpReason = giveUpReason;
           this.giveUpDate = giveUpDate;
           this.orderDate = orderDate;
           this.dealerCode = dealerCode;
           this.buyCarDate = buyCarDate;
           this.updateDate = updateDate;
    }


    /**
     * Gets the uniquenessID value for this CustomerStatusVo.
     * 
     * @return uniquenessID
     */
    public java.lang.String getUniquenessID() {
        return uniquenessID;
    }


    /**
     * Sets the uniquenessID value for this CustomerStatusVo.
     * 
     * @param uniquenessID
     */
    public void setUniquenessID(java.lang.String uniquenessID) {
        this.uniquenessID = uniquenessID;
    }


    /**
     * Gets the fcaID value for this CustomerStatusVo.
     * 
     * @return fcaID
     */
    public java.lang.String getFcaID() {
        return fcaID;
    }


    /**
     * Sets the fcaID value for this CustomerStatusVo.
     * 
     * @param fcaID
     */
    public void setFcaID(java.lang.String fcaID) {
        this.fcaID = fcaID;
    }


    /**
     * Gets the oppLevelID value for this CustomerStatusVo.
     * 
     * @return oppLevelID
     */
    public java.lang.String getOppLevelID() {
        return oppLevelID;
    }


    /**
     * Sets the oppLevelID value for this CustomerStatusVo.
     * 
     * @param oppLevelID
     */
    public void setOppLevelID(java.lang.String oppLevelID) {
        this.oppLevelID = oppLevelID;
    }


    /**
     * Gets the giveUpType value for this CustomerStatusVo.
     * 
     * @return giveUpType
     */
    public java.lang.String getGiveUpType() {
        return giveUpType;
    }


    /**
     * Sets the giveUpType value for this CustomerStatusVo.
     * 
     * @param giveUpType
     */
    public void setGiveUpType(java.lang.String giveUpType) {
        this.giveUpType = giveUpType;
    }


    /**
     * Gets the compareCar value for this CustomerStatusVo.
     * 
     * @return compareCar
     */
    public java.lang.String getCompareCar() {
        return compareCar;
    }


    /**
     * Sets the compareCar value for this CustomerStatusVo.
     * 
     * @param compareCar
     */
    public void setCompareCar(java.lang.String compareCar) {
        this.compareCar = compareCar;
    }


    /**
     * Gets the giveUpReason value for this CustomerStatusVo.
     * 
     * @return giveUpReason
     */
    public java.lang.String getGiveUpReason() {
        return giveUpReason;
    }


    /**
     * Sets the giveUpReason value for this CustomerStatusVo.
     * 
     * @param giveUpReason
     */
    public void setGiveUpReason(java.lang.String giveUpReason) {
        this.giveUpReason = giveUpReason;
    }


    /**
     * Gets the giveUpDate value for this CustomerStatusVo.
     * 
     * @return giveUpDate
     */
    public java.lang.String getGiveUpDate() {
        return giveUpDate;
    }


    /**
     * Sets the giveUpDate value for this CustomerStatusVo.
     * 
     * @param giveUpDate
     */
    public void setGiveUpDate(java.lang.String giveUpDate) {
        this.giveUpDate = giveUpDate;
    }


    /**
     * Gets the orderDate value for this CustomerStatusVo.
     * 
     * @return orderDate
     */
    public java.lang.String getOrderDate() {
        return orderDate;
    }


    /**
     * Sets the orderDate value for this CustomerStatusVo.
     * 
     * @param orderDate
     */
    public void setOrderDate(java.lang.String orderDate) {
        this.orderDate = orderDate;
    }


    /**
     * Gets the dealerCode value for this CustomerStatusVo.
     * 
     * @return dealerCode
     */
    public java.lang.String getDealerCode() {
        return dealerCode;
    }


    /**
     * Sets the dealerCode value for this CustomerStatusVo.
     * 
     * @param dealerCode
     */
    public void setDealerCode(java.lang.String dealerCode) {
        this.dealerCode = dealerCode;
    }


    /**
     * Gets the buyCarDate value for this CustomerStatusVo.
     * 
     * @return buyCarDate
     */
    public java.lang.String getBuyCarDate() {
        return buyCarDate;
    }


    /**
     * Sets the buyCarDate value for this CustomerStatusVo.
     * 
     * @param buyCarDate
     */
    public void setBuyCarDate(java.lang.String buyCarDate) {
        this.buyCarDate = buyCarDate;
    }


    /**
     * Gets the updateDate value for this CustomerStatusVo.
     * 
     * @return updateDate
     */
    public java.lang.String getUpdateDate() {
        return updateDate;
    }


    /**
     * Sets the updateDate value for this CustomerStatusVo.
     * 
     * @param updateDate
     */
    public void setUpdateDate(java.lang.String updateDate) {
        this.updateDate = updateDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerStatusVo)) return false;
        CustomerStatusVo other = (CustomerStatusVo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.uniquenessID==null && other.getUniquenessID()==null) || 
             (this.uniquenessID!=null &&
              this.uniquenessID.equals(other.getUniquenessID()))) &&
            ((this.fcaID==null && other.getFcaID()==null) || 
             (this.fcaID!=null &&
              this.fcaID.equals(other.getFcaID()))) &&
            ((this.oppLevelID==null && other.getOppLevelID()==null) || 
             (this.oppLevelID!=null &&
              this.oppLevelID.equals(other.getOppLevelID()))) &&
            ((this.giveUpType==null && other.getGiveUpType()==null) || 
             (this.giveUpType!=null &&
              this.giveUpType.equals(other.getGiveUpType()))) &&
            ((this.compareCar==null && other.getCompareCar()==null) || 
             (this.compareCar!=null &&
              this.compareCar.equals(other.getCompareCar()))) &&
            ((this.giveUpReason==null && other.getGiveUpReason()==null) || 
             (this.giveUpReason!=null &&
              this.giveUpReason.equals(other.getGiveUpReason()))) &&
            ((this.giveUpDate==null && other.getGiveUpDate()==null) || 
             (this.giveUpDate!=null &&
              this.giveUpDate.equals(other.getGiveUpDate()))) &&
            ((this.orderDate==null && other.getOrderDate()==null) || 
             (this.orderDate!=null &&
              this.orderDate.equals(other.getOrderDate()))) &&
            ((this.dealerCode==null && other.getDealerCode()==null) || 
             (this.dealerCode!=null &&
              this.dealerCode.equals(other.getDealerCode()))) &&
            ((this.buyCarDate==null && other.getBuyCarDate()==null) || 
             (this.buyCarDate!=null &&
              this.buyCarDate.equals(other.getBuyCarDate()))) &&
            ((this.updateDate==null && other.getUpdateDate()==null) || 
             (this.updateDate!=null &&
              this.updateDate.equals(other.getUpdateDate())));
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
        if (getUniquenessID() != null) {
            _hashCode += getUniquenessID().hashCode();
        }
        if (getFcaID() != null) {
            _hashCode += getFcaID().hashCode();
        }
        if (getOppLevelID() != null) {
            _hashCode += getOppLevelID().hashCode();
        }
        if (getGiveUpType() != null) {
            _hashCode += getGiveUpType().hashCode();
        }
        if (getCompareCar() != null) {
            _hashCode += getCompareCar().hashCode();
        }
        if (getGiveUpReason() != null) {
            _hashCode += getGiveUpReason().hashCode();
        }
        if (getGiveUpDate() != null) {
            _hashCode += getGiveUpDate().hashCode();
        }
        if (getOrderDate() != null) {
            _hashCode += getOrderDate().hashCode();
        }
        if (getDealerCode() != null) {
            _hashCode += getDealerCode().hashCode();
        }
        if (getBuyCarDate() != null) {
            _hashCode += getBuyCarDate().hashCode();
        }
        if (getUpdateDate() != null) {
            _hashCode += getUpdateDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerStatusVo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "CustomerStatusVo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uniquenessID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uniquenessID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fcaID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fcaID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oppLevelID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oppLevelID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giveUpType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giveUpType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compareCar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "compareCar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giveUpReason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giveUpReason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giveUpDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giveUpDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyCarDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "buyCarDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateDate"));
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
