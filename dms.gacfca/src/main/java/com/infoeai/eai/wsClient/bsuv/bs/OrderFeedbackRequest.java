/**
 * OrderFeedbackRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.bs;

public class OrderFeedbackRequest  implements java.io.Serializable {
    private java.lang.String EC_ORDER_NO;

    private java.lang.String DEAL_ORDER_AFFIRM_DATE;

    private java.lang.String SAP_ORDER_NO;

    private java.lang.String ERROR_MSG;

    public OrderFeedbackRequest() {
    }

    public OrderFeedbackRequest(
           java.lang.String EC_ORDER_NO,
           java.lang.String DEAL_ORDER_AFFIRM_DATE,
           java.lang.String SAP_ORDER_NO,
           java.lang.String ERROR_MSG) {
           this.EC_ORDER_NO = EC_ORDER_NO;
           this.DEAL_ORDER_AFFIRM_DATE = DEAL_ORDER_AFFIRM_DATE;
           this.SAP_ORDER_NO = SAP_ORDER_NO;
           this.ERROR_MSG = ERROR_MSG;
    }


    /**
     * Gets the EC_ORDER_NO value for this OrderFeedbackRequest.
     * 
     * @return EC_ORDER_NO
     */
    public java.lang.String getEC_ORDER_NO() {
        return EC_ORDER_NO;
    }


    /**
     * Sets the EC_ORDER_NO value for this OrderFeedbackRequest.
     * 
     * @param EC_ORDER_NO
     */
    public void setEC_ORDER_NO(java.lang.String EC_ORDER_NO) {
        this.EC_ORDER_NO = EC_ORDER_NO;
    }


    /**
     * Gets the DEAL_ORDER_AFFIRM_DATE value for this OrderFeedbackRequest.
     * 
     * @return DEAL_ORDER_AFFIRM_DATE
     */
    public java.lang.String getDEAL_ORDER_AFFIRM_DATE() {
        return DEAL_ORDER_AFFIRM_DATE;
    }


    /**
     * Sets the DEAL_ORDER_AFFIRM_DATE value for this OrderFeedbackRequest.
     * 
     * @param DEAL_ORDER_AFFIRM_DATE
     */
    public void setDEAL_ORDER_AFFIRM_DATE(java.lang.String DEAL_ORDER_AFFIRM_DATE) {
        this.DEAL_ORDER_AFFIRM_DATE = DEAL_ORDER_AFFIRM_DATE;
    }


    /**
     * Gets the SAP_ORDER_NO value for this OrderFeedbackRequest.
     * 
     * @return SAP_ORDER_NO
     */
    public java.lang.String getSAP_ORDER_NO() {
        return SAP_ORDER_NO;
    }


    /**
     * Sets the SAP_ORDER_NO value for this OrderFeedbackRequest.
     * 
     * @param SAP_ORDER_NO
     */
    public void setSAP_ORDER_NO(java.lang.String SAP_ORDER_NO) {
        this.SAP_ORDER_NO = SAP_ORDER_NO;
    }


    /**
     * Gets the ERROR_MSG value for this OrderFeedbackRequest.
     * 
     * @return ERROR_MSG
     */
    public java.lang.String getERROR_MSG() {
        return ERROR_MSG;
    }


    /**
     * Sets the ERROR_MSG value for this OrderFeedbackRequest.
     * 
     * @param ERROR_MSG
     */
    public void setERROR_MSG(java.lang.String ERROR_MSG) {
        this.ERROR_MSG = ERROR_MSG;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderFeedbackRequest)) return false;
        OrderFeedbackRequest other = (OrderFeedbackRequest) obj;
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
            ((this.DEAL_ORDER_AFFIRM_DATE==null && other.getDEAL_ORDER_AFFIRM_DATE()==null) || 
             (this.DEAL_ORDER_AFFIRM_DATE!=null &&
              this.DEAL_ORDER_AFFIRM_DATE.equals(other.getDEAL_ORDER_AFFIRM_DATE()))) &&
            ((this.SAP_ORDER_NO==null && other.getSAP_ORDER_NO()==null) || 
             (this.SAP_ORDER_NO!=null &&
              this.SAP_ORDER_NO.equals(other.getSAP_ORDER_NO()))) &&
            ((this.ERROR_MSG==null && other.getERROR_MSG()==null) || 
             (this.ERROR_MSG!=null &&
              this.ERROR_MSG.equals(other.getERROR_MSG())));
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
        if (getDEAL_ORDER_AFFIRM_DATE() != null) {
            _hashCode += getDEAL_ORDER_AFFIRM_DATE().hashCode();
        }
        if (getSAP_ORDER_NO() != null) {
            _hashCode += getSAP_ORDER_NO().hashCode();
        }
        if (getERROR_MSG() != null) {
            _hashCode += getERROR_MSG().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderFeedbackRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", ">OrderFeedbackRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EC_ORDER_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "EC_ORDER_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEAL_ORDER_AFFIRM_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DEAL_ORDER_AFFIRM_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SAP_ORDER_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "SAP_ORDER_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERROR_MSG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "ERROR_MSG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
