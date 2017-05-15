/**
 * Order.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.bsuv.lms;

public class Order  implements java.io.Serializable {
    private java.lang.String EC_ORDER_NO;

    private java.lang.String BRAND_CODE;

    private java.lang.String SERIES_CODE;

    private java.lang.String MODEL_CODE;

    private java.lang.String MODEL_YEAR;

    private java.lang.String GROUP_CODE;

    private java.lang.String TRIM_CODE;

    private java.lang.String COLOR_CODE;

    private java.lang.String CUSTOMER_NAME;

    private java.lang.String CUSTOMER_TEL;

    private java.lang.String ID_CRAD;

    private java.lang.String DETERMINED_TIME;

    private java.lang.String DEALER_CODE;

    private java.lang.String RETAIL_FINANCE;

    private java.lang.Double DEPOSIT_AMOUNT;

    private java.lang.String ORDER_CANCEL_TIME;

    private int OPERATION_FLAG;

    private int EC_ORDER_TYPE;

    public Order() {
    }

    public Order(
           java.lang.String EC_ORDER_NO,
           java.lang.String BRAND_CODE,
           java.lang.String SERIES_CODE,
           java.lang.String MODEL_CODE,
           java.lang.String MODEL_YEAR,
           java.lang.String GROUP_CODE,
           java.lang.String TRIM_CODE,
           java.lang.String COLOR_CODE,
           java.lang.String CUSTOMER_NAME,
           java.lang.String CUSTOMER_TEL,
           java.lang.String ID_CRAD,
           java.lang.String DETERMINED_TIME,
           java.lang.String DEALER_CODE,
           java.lang.String RETAIL_FINANCE,
           java.lang.Double DEPOSIT_AMOUNT,
           java.lang.String ORDER_CANCEL_TIME,
           int OPERATION_FLAG,
           int EC_ORDER_TYPE) {
           this.EC_ORDER_NO = EC_ORDER_NO;
           this.BRAND_CODE = BRAND_CODE;
           this.SERIES_CODE = SERIES_CODE;
           this.MODEL_CODE = MODEL_CODE;
           this.MODEL_YEAR = MODEL_YEAR;
           this.GROUP_CODE = GROUP_CODE;
           this.TRIM_CODE = TRIM_CODE;
           this.COLOR_CODE = COLOR_CODE;
           this.CUSTOMER_NAME = CUSTOMER_NAME;
           this.CUSTOMER_TEL = CUSTOMER_TEL;
           this.ID_CRAD = ID_CRAD;
           this.DETERMINED_TIME = DETERMINED_TIME;
           this.DEALER_CODE = DEALER_CODE;
           this.RETAIL_FINANCE = RETAIL_FINANCE;
           this.DEPOSIT_AMOUNT = DEPOSIT_AMOUNT;
           this.ORDER_CANCEL_TIME = ORDER_CANCEL_TIME;
           this.OPERATION_FLAG = OPERATION_FLAG;
           this.EC_ORDER_TYPE = EC_ORDER_TYPE;
    }


    /**
     * Gets the EC_ORDER_NO value for this Order.
     * 
     * @return EC_ORDER_NO
     */
    public java.lang.String getEC_ORDER_NO() {
        return EC_ORDER_NO;
    }


    /**
     * Sets the EC_ORDER_NO value for this Order.
     * 
     * @param EC_ORDER_NO
     */
    public void setEC_ORDER_NO(java.lang.String EC_ORDER_NO) {
        this.EC_ORDER_NO = EC_ORDER_NO;
    }


    /**
     * Gets the BRAND_CODE value for this Order.
     * 
     * @return BRAND_CODE
     */
    public java.lang.String getBRAND_CODE() {
        return BRAND_CODE;
    }


    /**
     * Sets the BRAND_CODE value for this Order.
     * 
     * @param BRAND_CODE
     */
    public void setBRAND_CODE(java.lang.String BRAND_CODE) {
        this.BRAND_CODE = BRAND_CODE;
    }


    /**
     * Gets the SERIES_CODE value for this Order.
     * 
     * @return SERIES_CODE
     */
    public java.lang.String getSERIES_CODE() {
        return SERIES_CODE;
    }


    /**
     * Sets the SERIES_CODE value for this Order.
     * 
     * @param SERIES_CODE
     */
    public void setSERIES_CODE(java.lang.String SERIES_CODE) {
        this.SERIES_CODE = SERIES_CODE;
    }


    /**
     * Gets the MODEL_CODE value for this Order.
     * 
     * @return MODEL_CODE
     */
    public java.lang.String getMODEL_CODE() {
        return MODEL_CODE;
    }


    /**
     * Sets the MODEL_CODE value for this Order.
     * 
     * @param MODEL_CODE
     */
    public void setMODEL_CODE(java.lang.String MODEL_CODE) {
        this.MODEL_CODE = MODEL_CODE;
    }


    /**
     * Gets the MODEL_YEAR value for this Order.
     * 
     * @return MODEL_YEAR
     */
    public java.lang.String getMODEL_YEAR() {
        return MODEL_YEAR;
    }


    /**
     * Sets the MODEL_YEAR value for this Order.
     * 
     * @param MODEL_YEAR
     */
    public void setMODEL_YEAR(java.lang.String MODEL_YEAR) {
        this.MODEL_YEAR = MODEL_YEAR;
    }


    /**
     * Gets the GROUP_CODE value for this Order.
     * 
     * @return GROUP_CODE
     */
    public java.lang.String getGROUP_CODE() {
        return GROUP_CODE;
    }


    /**
     * Sets the GROUP_CODE value for this Order.
     * 
     * @param GROUP_CODE
     */
    public void setGROUP_CODE(java.lang.String GROUP_CODE) {
        this.GROUP_CODE = GROUP_CODE;
    }


    /**
     * Gets the TRIM_CODE value for this Order.
     * 
     * @return TRIM_CODE
     */
    public java.lang.String getTRIM_CODE() {
        return TRIM_CODE;
    }


    /**
     * Sets the TRIM_CODE value for this Order.
     * 
     * @param TRIM_CODE
     */
    public void setTRIM_CODE(java.lang.String TRIM_CODE) {
        this.TRIM_CODE = TRIM_CODE;
    }


    /**
     * Gets the COLOR_CODE value for this Order.
     * 
     * @return COLOR_CODE
     */
    public java.lang.String getCOLOR_CODE() {
        return COLOR_CODE;
    }


    /**
     * Sets the COLOR_CODE value for this Order.
     * 
     * @param COLOR_CODE
     */
    public void setCOLOR_CODE(java.lang.String COLOR_CODE) {
        this.COLOR_CODE = COLOR_CODE;
    }


    /**
     * Gets the CUSTOMER_NAME value for this Order.
     * 
     * @return CUSTOMER_NAME
     */
    public java.lang.String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }


    /**
     * Sets the CUSTOMER_NAME value for this Order.
     * 
     * @param CUSTOMER_NAME
     */
    public void setCUSTOMER_NAME(java.lang.String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }


    /**
     * Gets the CUSTOMER_TEL value for this Order.
     * 
     * @return CUSTOMER_TEL
     */
    public java.lang.String getCUSTOMER_TEL() {
        return CUSTOMER_TEL;
    }


    /**
     * Sets the CUSTOMER_TEL value for this Order.
     * 
     * @param CUSTOMER_TEL
     */
    public void setCUSTOMER_TEL(java.lang.String CUSTOMER_TEL) {
        this.CUSTOMER_TEL = CUSTOMER_TEL;
    }


    /**
     * Gets the ID_CRAD value for this Order.
     * 
     * @return ID_CRAD
     */
    public java.lang.String getID_CRAD() {
        return ID_CRAD;
    }


    /**
     * Sets the ID_CRAD value for this Order.
     * 
     * @param ID_CRAD
     */
    public void setID_CRAD(java.lang.String ID_CRAD) {
        this.ID_CRAD = ID_CRAD;
    }


    /**
     * Gets the DETERMINED_TIME value for this Order.
     * 
     * @return DETERMINED_TIME
     */
    public java.lang.String getDETERMINED_TIME() {
        return DETERMINED_TIME;
    }


    /**
     * Sets the DETERMINED_TIME value for this Order.
     * 
     * @param DETERMINED_TIME
     */
    public void setDETERMINED_TIME(java.lang.String DETERMINED_TIME) {
        this.DETERMINED_TIME = DETERMINED_TIME;
    }


    /**
     * Gets the DEALER_CODE value for this Order.
     * 
     * @return DEALER_CODE
     */
    public java.lang.String getDEALER_CODE() {
        return DEALER_CODE;
    }


    /**
     * Sets the DEALER_CODE value for this Order.
     * 
     * @param DEALER_CODE
     */
    public void setDEALER_CODE(java.lang.String DEALER_CODE) {
        this.DEALER_CODE = DEALER_CODE;
    }


    /**
     * Gets the RETAIL_FINANCE value for this Order.
     * 
     * @return RETAIL_FINANCE
     */
    public java.lang.String getRETAIL_FINANCE() {
        return RETAIL_FINANCE;
    }


    /**
     * Sets the RETAIL_FINANCE value for this Order.
     * 
     * @param RETAIL_FINANCE
     */
    public void setRETAIL_FINANCE(java.lang.String RETAIL_FINANCE) {
        this.RETAIL_FINANCE = RETAIL_FINANCE;
    }


    /**
     * Gets the DEPOSIT_AMOUNT value for this Order.
     * 
     * @return DEPOSIT_AMOUNT
     */
    public java.lang.Double getDEPOSIT_AMOUNT() {
        return DEPOSIT_AMOUNT;
    }


    /**
     * Sets the DEPOSIT_AMOUNT value for this Order.
     * 
     * @param DEPOSIT_AMOUNT
     */
    public void setDEPOSIT_AMOUNT(java.lang.Double DEPOSIT_AMOUNT) {
        this.DEPOSIT_AMOUNT = DEPOSIT_AMOUNT;
    }


    /**
     * Gets the ORDER_CANCEL_TIME value for this Order.
     * 
     * @return ORDER_CANCEL_TIME
     */
    public java.lang.String getORDER_CANCEL_TIME() {
        return ORDER_CANCEL_TIME;
    }


    /**
     * Sets the ORDER_CANCEL_TIME value for this Order.
     * 
     * @param ORDER_CANCEL_TIME
     */
    public void setORDER_CANCEL_TIME(java.lang.String ORDER_CANCEL_TIME) {
        this.ORDER_CANCEL_TIME = ORDER_CANCEL_TIME;
    }


    /**
     * Gets the OPERATION_FLAG value for this Order.
     * 
     * @return OPERATION_FLAG
     */
    public int getOPERATION_FLAG() {
        return OPERATION_FLAG;
    }


    /**
     * Sets the OPERATION_FLAG value for this Order.
     * 
     * @param OPERATION_FLAG
     */
    public void setOPERATION_FLAG(int OPERATION_FLAG) {
        this.OPERATION_FLAG = OPERATION_FLAG;
    }


    /**
     * Gets the EC_ORDER_TYPE value for this Order.
     * 
     * @return EC_ORDER_TYPE
     */
    public int getEC_ORDER_TYPE() {
        return EC_ORDER_TYPE;
    }


    /**
     * Sets the EC_ORDER_TYPE value for this Order.
     * 
     * @param EC_ORDER_TYPE
     */
    public void setEC_ORDER_TYPE(int EC_ORDER_TYPE) {
        this.EC_ORDER_TYPE = EC_ORDER_TYPE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Order)) return false;
        Order other = (Order) obj;
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
            ((this.BRAND_CODE==null && other.getBRAND_CODE()==null) || 
             (this.BRAND_CODE!=null &&
              this.BRAND_CODE.equals(other.getBRAND_CODE()))) &&
            ((this.SERIES_CODE==null && other.getSERIES_CODE()==null) || 
             (this.SERIES_CODE!=null &&
              this.SERIES_CODE.equals(other.getSERIES_CODE()))) &&
            ((this.MODEL_CODE==null && other.getMODEL_CODE()==null) || 
             (this.MODEL_CODE!=null &&
              this.MODEL_CODE.equals(other.getMODEL_CODE()))) &&
            ((this.MODEL_YEAR==null && other.getMODEL_YEAR()==null) || 
             (this.MODEL_YEAR!=null &&
              this.MODEL_YEAR.equals(other.getMODEL_YEAR()))) &&
            ((this.GROUP_CODE==null && other.getGROUP_CODE()==null) || 
             (this.GROUP_CODE!=null &&
              this.GROUP_CODE.equals(other.getGROUP_CODE()))) &&
            ((this.TRIM_CODE==null && other.getTRIM_CODE()==null) || 
             (this.TRIM_CODE!=null &&
              this.TRIM_CODE.equals(other.getTRIM_CODE()))) &&
            ((this.COLOR_CODE==null && other.getCOLOR_CODE()==null) || 
             (this.COLOR_CODE!=null &&
              this.COLOR_CODE.equals(other.getCOLOR_CODE()))) &&
            ((this.CUSTOMER_NAME==null && other.getCUSTOMER_NAME()==null) || 
             (this.CUSTOMER_NAME!=null &&
              this.CUSTOMER_NAME.equals(other.getCUSTOMER_NAME()))) &&
            ((this.CUSTOMER_TEL==null && other.getCUSTOMER_TEL()==null) || 
             (this.CUSTOMER_TEL!=null &&
              this.CUSTOMER_TEL.equals(other.getCUSTOMER_TEL()))) &&
            ((this.ID_CRAD==null && other.getID_CRAD()==null) || 
             (this.ID_CRAD!=null &&
              this.ID_CRAD.equals(other.getID_CRAD()))) &&
            ((this.DETERMINED_TIME==null && other.getDETERMINED_TIME()==null) || 
             (this.DETERMINED_TIME!=null &&
              this.DETERMINED_TIME.equals(other.getDETERMINED_TIME()))) &&
            ((this.DEALER_CODE==null && other.getDEALER_CODE()==null) || 
             (this.DEALER_CODE!=null &&
              this.DEALER_CODE.equals(other.getDEALER_CODE()))) &&
            ((this.RETAIL_FINANCE==null && other.getRETAIL_FINANCE()==null) || 
             (this.RETAIL_FINANCE!=null &&
              this.RETAIL_FINANCE.equals(other.getRETAIL_FINANCE()))) &&
            ((this.DEPOSIT_AMOUNT==null && other.getDEPOSIT_AMOUNT()==null) || 
             (this.DEPOSIT_AMOUNT!=null &&
              this.DEPOSIT_AMOUNT.equals(other.getDEPOSIT_AMOUNT()))) &&
            ((this.ORDER_CANCEL_TIME==null && other.getORDER_CANCEL_TIME()==null) || 
             (this.ORDER_CANCEL_TIME!=null &&
              this.ORDER_CANCEL_TIME.equals(other.getORDER_CANCEL_TIME()))) &&
            this.OPERATION_FLAG == other.getOPERATION_FLAG() &&
            this.EC_ORDER_TYPE == other.getEC_ORDER_TYPE();
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
        if (getBRAND_CODE() != null) {
            _hashCode += getBRAND_CODE().hashCode();
        }
        if (getSERIES_CODE() != null) {
            _hashCode += getSERIES_CODE().hashCode();
        }
        if (getMODEL_CODE() != null) {
            _hashCode += getMODEL_CODE().hashCode();
        }
        if (getMODEL_YEAR() != null) {
            _hashCode += getMODEL_YEAR().hashCode();
        }
        if (getGROUP_CODE() != null) {
            _hashCode += getGROUP_CODE().hashCode();
        }
        if (getTRIM_CODE() != null) {
            _hashCode += getTRIM_CODE().hashCode();
        }
        if (getCOLOR_CODE() != null) {
            _hashCode += getCOLOR_CODE().hashCode();
        }
        if (getCUSTOMER_NAME() != null) {
            _hashCode += getCUSTOMER_NAME().hashCode();
        }
        if (getCUSTOMER_TEL() != null) {
            _hashCode += getCUSTOMER_TEL().hashCode();
        }
        if (getID_CRAD() != null) {
            _hashCode += getID_CRAD().hashCode();
        }
        if (getDETERMINED_TIME() != null) {
            _hashCode += getDETERMINED_TIME().hashCode();
        }
        if (getDEALER_CODE() != null) {
            _hashCode += getDEALER_CODE().hashCode();
        }
        if (getRETAIL_FINANCE() != null) {
            _hashCode += getRETAIL_FINANCE().hashCode();
        }
        if (getDEPOSIT_AMOUNT() != null) {
            _hashCode += getDEPOSIT_AMOUNT().hashCode();
        }
        if (getORDER_CANCEL_TIME() != null) {
            _hashCode += getORDER_CANCEL_TIME().hashCode();
        }
        _hashCode += getOPERATION_FLAG();
        _hashCode += getEC_ORDER_TYPE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Order.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "Order"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EC_ORDER_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "EC_ORDER_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BRAND_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "BRAND_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERIES_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "SERIES_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MODEL_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "MODEL_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MODEL_YEAR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "MODEL_YEAR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GROUP_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "GROUP_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRIM_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "TRIM_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("COLOR_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "COLOR_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUSTOMER_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "CUSTOMER_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUSTOMER_TEL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "CUSTOMER_TEL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID_CRAD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "ID_CRAD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DETERMINED_TIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DETERMINED_TIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DEALER_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RETAIL_FINANCE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "RETAIL_FINANCE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPOSIT_AMOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "DEPOSIT_AMOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ORDER_CANCEL_TIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "ORDER_CANCEL_TIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OPERATION_FLAG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "OPERATION_FLAG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EC_ORDER_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://models.generate.boldseas.com", "EC_ORDER_TYPE"));
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
