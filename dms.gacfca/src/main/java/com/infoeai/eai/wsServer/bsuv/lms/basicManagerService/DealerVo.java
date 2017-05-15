/**
 * DealerVo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.bsuv.lms.basicManagerService;

@SuppressWarnings("serial")
public class DealerVo  implements java.io.Serializable {
    private java.lang.String DEALER_CODE;

    private java.lang.String DEALER_SHORTNAME;

    private java.lang.String DEALER_NAME;

    private java.lang.String OFFICE_PHONE;

    private java.lang.String PROVINCE;

    private java.lang.String CITY;

    private java.lang.String ADDRESS;

    private int STATUS;

    private int IS_EC;

    public DealerVo() {
    }

    public DealerVo(
           java.lang.String DEALER_CODE,
           java.lang.String DEALER_SHORTNAME,
           java.lang.String DEALER_NAME,
           java.lang.String OFFICE_PHONE,
           java.lang.String PROVINCE,
           java.lang.String CITY,
           java.lang.String ADDRESS,
           int STATUS,
           int IS_EC) {
           this.DEALER_CODE = DEALER_CODE;
           this.DEALER_SHORTNAME = DEALER_SHORTNAME;
           this.DEALER_NAME = DEALER_NAME;
           this.OFFICE_PHONE = OFFICE_PHONE;
           this.PROVINCE = PROVINCE;
           this.CITY = CITY;
           this.ADDRESS = ADDRESS;
           this.STATUS = STATUS;
           this.IS_EC = IS_EC;
    }


    /**
     * Gets the DEALER_CODE value for this DealerVo.
     * 
     * @return DEALER_CODE
     */
    public java.lang.String getDEALER_CODE() {
        return DEALER_CODE;
    }


    /**
     * Sets the DEALER_CODE value for this DealerVo.
     * 
     * @param DEALER_CODE
     */
    public void setDEALER_CODE(java.lang.String DEALER_CODE) {
        this.DEALER_CODE = DEALER_CODE;
    }


    /**
     * Gets the DEALER_SHORTNAME value for this DealerVo.
     * 
     * @return DEALER_SHORTNAME
     */
    public java.lang.String getDEALER_SHORTNAME() {
        return DEALER_SHORTNAME;
    }


    /**
     * Sets the DEALER_SHORTNAME value for this DealerVo.
     * 
     * @param DEALER_SHORTNAME
     */
    public void setDEALER_SHORTNAME(java.lang.String DEALER_SHORTNAME) {
        this.DEALER_SHORTNAME = DEALER_SHORTNAME;
    }


    /**
     * Gets the DEALER_NAME value for this DealerVo.
     * 
     * @return DEALER_NAME
     */
    public java.lang.String getDEALER_NAME() {
        return DEALER_NAME;
    }


    /**
     * Sets the DEALER_NAME value for this DealerVo.
     * 
     * @param DEALER_NAME
     */
    public void setDEALER_NAME(java.lang.String DEALER_NAME) {
        this.DEALER_NAME = DEALER_NAME;
    }


    /**
     * Gets the OFFICE_PHONE value for this DealerVo.
     * 
     * @return OFFICE_PHONE
     */
    public java.lang.String getOFFICE_PHONE() {
        return OFFICE_PHONE;
    }


    /**
     * Sets the OFFICE_PHONE value for this DealerVo.
     * 
     * @param OFFICE_PHONE
     */
    public void setOFFICE_PHONE(java.lang.String OFFICE_PHONE) {
        this.OFFICE_PHONE = OFFICE_PHONE;
    }


    /**
     * Gets the PROVINCE value for this DealerVo.
     * 
     * @return PROVINCE
     */
    public java.lang.String getPROVINCE() {
        return PROVINCE;
    }


    /**
     * Sets the PROVINCE value for this DealerVo.
     * 
     * @param PROVINCE
     */
    public void setPROVINCE(java.lang.String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }


    /**
     * Gets the CITY value for this DealerVo.
     * 
     * @return CITY
     */
    public java.lang.String getCITY() {
        return CITY;
    }


    /**
     * Sets the CITY value for this DealerVo.
     * 
     * @param CITY
     */
    public void setCITY(java.lang.String CITY) {
        this.CITY = CITY;
    }


    /**
     * Gets the ADDRESS value for this DealerVo.
     * 
     * @return ADDRESS
     */
    public java.lang.String getADDRESS() {
        return ADDRESS;
    }


    /**
     * Sets the ADDRESS value for this DealerVo.
     * 
     * @param ADDRESS
     */
    public void setADDRESS(java.lang.String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }


    /**
     * Gets the STATUS value for this DealerVo.
     * 
     * @return STATUS
     */
    public int getSTATUS() {
        return STATUS;
    }


    /**
     * Sets the STATUS value for this DealerVo.
     * 
     * @param STATUS
     */
    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }


    /**
     * Gets the IS_EC value for this DealerVo.
     * 
     * @return IS_EC
     */
    public int getIS_EC() {
        return IS_EC;
    }


    /**
     * Sets the IS_EC value for this DealerVo.
     * 
     * @param IS_EC
     */
    public void setIS_EC(int IS_EC) {
        this.IS_EC = IS_EC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DealerVo)) return false;
        DealerVo other = (DealerVo) obj;
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
            ((this.DEALER_SHORTNAME==null && other.getDEALER_SHORTNAME()==null) || 
             (this.DEALER_SHORTNAME!=null &&
              this.DEALER_SHORTNAME.equals(other.getDEALER_SHORTNAME()))) &&
            ((this.DEALER_NAME==null && other.getDEALER_NAME()==null) || 
             (this.DEALER_NAME!=null &&
              this.DEALER_NAME.equals(other.getDEALER_NAME()))) &&
            ((this.OFFICE_PHONE==null && other.getOFFICE_PHONE()==null) || 
             (this.OFFICE_PHONE!=null &&
              this.OFFICE_PHONE.equals(other.getOFFICE_PHONE()))) &&
            ((this.PROVINCE==null && other.getPROVINCE()==null) || 
             (this.PROVINCE!=null &&
              this.PROVINCE.equals(other.getPROVINCE()))) &&
            ((this.CITY==null && other.getCITY()==null) || 
             (this.CITY!=null &&
              this.CITY.equals(other.getCITY()))) &&
            ((this.ADDRESS==null && other.getADDRESS()==null) || 
             (this.ADDRESS!=null &&
              this.ADDRESS.equals(other.getADDRESS()))) &&
            this.STATUS == other.getSTATUS() &&
            this.IS_EC == other.getIS_EC();
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
        if (getDEALER_SHORTNAME() != null) {
            _hashCode += getDEALER_SHORTNAME().hashCode();
        }
        if (getDEALER_NAME() != null) {
            _hashCode += getDEALER_NAME().hashCode();
        }
        if (getOFFICE_PHONE() != null) {
            _hashCode += getOFFICE_PHONE().hashCode();
        }
        if (getPROVINCE() != null) {
            _hashCode += getPROVINCE().hashCode();
        }
        if (getCITY() != null) {
            _hashCode += getCITY().hashCode();
        }
        if (getADDRESS() != null) {
            _hashCode += getADDRESS().hashCode();
        }
        _hashCode += getSTATUS();
        _hashCode += getIS_EC();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DealerVo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/BasicManagerService/", "DealerVo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_SHORTNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_SHORTNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OFFICE_PHONE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OFFICE_PHONE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PROVINCE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PROVINCE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CITY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CITY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDRESS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ADDRESS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_EC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IS_EC"));
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
