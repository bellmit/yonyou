/**
 * ZlvsdwsGf01Out.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry;

public class ZlvsdwsGf01Out  implements java.io.Serializable {
    private java.lang.String werks;

    private java.lang.String zzcliente;

    private java.lang.String marca;

    private java.lang.String dealerUsr;

    private java.math.BigDecimal creditLimit;

    private java.math.BigDecimal receivable;

    private java.math.BigDecimal salesValue;

    private java.math.BigDecimal creditExposure;

    public ZlvsdwsGf01Out() {
    }

    public ZlvsdwsGf01Out(
           java.lang.String werks,
           java.lang.String zzcliente,
           java.lang.String marca,
           java.lang.String dealerUsr,
           java.math.BigDecimal creditLimit,
           java.math.BigDecimal receivable,
           java.math.BigDecimal salesValue,
           java.math.BigDecimal creditExposure) {
           this.werks = werks;
           this.zzcliente = zzcliente;
           this.marca = marca;
           this.dealerUsr = dealerUsr;
           this.creditLimit = creditLimit;
           this.receivable = receivable;
           this.salesValue = salesValue;
           this.creditExposure = creditExposure;
    }


    /**
     * Gets the werks value for this ZlvsdwsGf01Out.
     * 
     * @return werks
     */
    public java.lang.String getWerks() {
        return werks;
    }


    /**
     * Sets the werks value for this ZlvsdwsGf01Out.
     * 
     * @param werks
     */
    public void setWerks(java.lang.String werks) {
        this.werks = werks;
    }


    /**
     * Gets the zzcliente value for this ZlvsdwsGf01Out.
     * 
     * @return zzcliente
     */
    public java.lang.String getZzcliente() {
        return zzcliente;
    }


    /**
     * Sets the zzcliente value for this ZlvsdwsGf01Out.
     * 
     * @param zzcliente
     */
    public void setZzcliente(java.lang.String zzcliente) {
        this.zzcliente = zzcliente;
    }


    /**
     * Gets the marca value for this ZlvsdwsGf01Out.
     * 
     * @return marca
     */
    public java.lang.String getMarca() {
        return marca;
    }


    /**
     * Sets the marca value for this ZlvsdwsGf01Out.
     * 
     * @param marca
     */
    public void setMarca(java.lang.String marca) {
        this.marca = marca;
    }


    /**
     * Gets the dealerUsr value for this ZlvsdwsGf01Out.
     * 
     * @return dealerUsr
     */
    public java.lang.String getDealerUsr() {
        return dealerUsr;
    }


    /**
     * Sets the dealerUsr value for this ZlvsdwsGf01Out.
     * 
     * @param dealerUsr
     */
    public void setDealerUsr(java.lang.String dealerUsr) {
        this.dealerUsr = dealerUsr;
    }


    /**
     * Gets the creditLimit value for this ZlvsdwsGf01Out.
     * 
     * @return creditLimit
     */
    public java.math.BigDecimal getCreditLimit() {
        return creditLimit;
    }


    /**
     * Sets the creditLimit value for this ZlvsdwsGf01Out.
     * 
     * @param creditLimit
     */
    public void setCreditLimit(java.math.BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }


    /**
     * Gets the receivable value for this ZlvsdwsGf01Out.
     * 
     * @return receivable
     */
    public java.math.BigDecimal getReceivable() {
        return receivable;
    }


    /**
     * Sets the receivable value for this ZlvsdwsGf01Out.
     * 
     * @param receivable
     */
    public void setReceivable(java.math.BigDecimal receivable) {
        this.receivable = receivable;
    }


    /**
     * Gets the salesValue value for this ZlvsdwsGf01Out.
     * 
     * @return salesValue
     */
    public java.math.BigDecimal getSalesValue() {
        return salesValue;
    }


    /**
     * Sets the salesValue value for this ZlvsdwsGf01Out.
     * 
     * @param salesValue
     */
    public void setSalesValue(java.math.BigDecimal salesValue) {
        this.salesValue = salesValue;
    }


    /**
     * Gets the creditExposure value for this ZlvsdwsGf01Out.
     * 
     * @return creditExposure
     */
    public java.math.BigDecimal getCreditExposure() {
        return creditExposure;
    }


    /**
     * Sets the creditExposure value for this ZlvsdwsGf01Out.
     * 
     * @param creditExposure
     */
    public void setCreditExposure(java.math.BigDecimal creditExposure) {
        this.creditExposure = creditExposure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsGf01Out)) return false;
        ZlvsdwsGf01Out other = (ZlvsdwsGf01Out) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.werks==null && other.getWerks()==null) || 
             (this.werks!=null &&
              this.werks.equals(other.getWerks()))) &&
            ((this.zzcliente==null && other.getZzcliente()==null) || 
             (this.zzcliente!=null &&
              this.zzcliente.equals(other.getZzcliente()))) &&
            ((this.marca==null && other.getMarca()==null) || 
             (this.marca!=null &&
              this.marca.equals(other.getMarca()))) &&
            ((this.dealerUsr==null && other.getDealerUsr()==null) || 
             (this.dealerUsr!=null &&
              this.dealerUsr.equals(other.getDealerUsr()))) &&
            ((this.creditLimit==null && other.getCreditLimit()==null) || 
             (this.creditLimit!=null &&
              this.creditLimit.equals(other.getCreditLimit()))) &&
            ((this.receivable==null && other.getReceivable()==null) || 
             (this.receivable!=null &&
              this.receivable.equals(other.getReceivable()))) &&
            ((this.salesValue==null && other.getSalesValue()==null) || 
             (this.salesValue!=null &&
              this.salesValue.equals(other.getSalesValue()))) &&
            ((this.creditExposure==null && other.getCreditExposure()==null) || 
             (this.creditExposure!=null &&
              this.creditExposure.equals(other.getCreditExposure())));
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
        if (getWerks() != null) {
            _hashCode += getWerks().hashCode();
        }
        if (getZzcliente() != null) {
            _hashCode += getZzcliente().hashCode();
        }
        if (getMarca() != null) {
            _hashCode += getMarca().hashCode();
        }
        if (getDealerUsr() != null) {
            _hashCode += getDealerUsr().hashCode();
        }
        if (getCreditLimit() != null) {
            _hashCode += getCreditLimit().hashCode();
        }
        if (getReceivable() != null) {
            _hashCode += getReceivable().hashCode();
        }
        if (getSalesValue() != null) {
            _hashCode += getSalesValue().hashCode();
        }
        if (getCreditExposure() != null) {
            _hashCode += getCreditExposure().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsGf01Out.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf01Out"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("werks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Werks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zzcliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Zzcliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("marca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Marca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealerUsr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DealerUsr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receivable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Receivable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SalesValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditExposure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CreditExposure"));
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
