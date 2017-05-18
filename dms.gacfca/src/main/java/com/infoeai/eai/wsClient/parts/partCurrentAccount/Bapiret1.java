/**
 * Bapiret1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.partCurrentAccount;

public class Bapiret1  implements java.io.Serializable {
    private java.lang.String type;

    private java.lang.String id;

    private java.lang.String number;

    private java.lang.String message;

    private java.lang.String logNo;

    private java.lang.String logMsgNo;

    private java.lang.String messageV1;

    private java.lang.String messageV2;

    private java.lang.String messageV3;

    private java.lang.String messageV4;

    public Bapiret1() {
    }

    public Bapiret1(
           java.lang.String type,
           java.lang.String id,
           java.lang.String number,
           java.lang.String message,
           java.lang.String logNo,
           java.lang.String logMsgNo,
           java.lang.String messageV1,
           java.lang.String messageV2,
           java.lang.String messageV3,
           java.lang.String messageV4) {
           this.type = type;
           this.id = id;
           this.number = number;
           this.message = message;
           this.logNo = logNo;
           this.logMsgNo = logMsgNo;
           this.messageV1 = messageV1;
           this.messageV2 = messageV2;
           this.messageV3 = messageV3;
           this.messageV4 = messageV4;
    }


    /**
     * Gets the type value for this Bapiret1.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Bapiret1.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the id value for this Bapiret1.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Bapiret1.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the number value for this Bapiret1.
     * 
     * @return number
     */
    public java.lang.String getNumber() {
        return number;
    }


    /**
     * Sets the number value for this Bapiret1.
     * 
     * @param number
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    /**
     * Gets the message value for this Bapiret1.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Bapiret1.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the logNo value for this Bapiret1.
     * 
     * @return logNo
     */
    public java.lang.String getLogNo() {
        return logNo;
    }


    /**
     * Sets the logNo value for this Bapiret1.
     * 
     * @param logNo
     */
    public void setLogNo(java.lang.String logNo) {
        this.logNo = logNo;
    }


    /**
     * Gets the logMsgNo value for this Bapiret1.
     * 
     * @return logMsgNo
     */
    public java.lang.String getLogMsgNo() {
        return logMsgNo;
    }


    /**
     * Sets the logMsgNo value for this Bapiret1.
     * 
     * @param logMsgNo
     */
    public void setLogMsgNo(java.lang.String logMsgNo) {
        this.logMsgNo = logMsgNo;
    }


    /**
     * Gets the messageV1 value for this Bapiret1.
     * 
     * @return messageV1
     */
    public java.lang.String getMessageV1() {
        return messageV1;
    }


    /**
     * Sets the messageV1 value for this Bapiret1.
     * 
     * @param messageV1
     */
    public void setMessageV1(java.lang.String messageV1) {
        this.messageV1 = messageV1;
    }


    /**
     * Gets the messageV2 value for this Bapiret1.
     * 
     * @return messageV2
     */
    public java.lang.String getMessageV2() {
        return messageV2;
    }


    /**
     * Sets the messageV2 value for this Bapiret1.
     * 
     * @param messageV2
     */
    public void setMessageV2(java.lang.String messageV2) {
        this.messageV2 = messageV2;
    }


    /**
     * Gets the messageV3 value for this Bapiret1.
     * 
     * @return messageV3
     */
    public java.lang.String getMessageV3() {
        return messageV3;
    }


    /**
     * Sets the messageV3 value for this Bapiret1.
     * 
     * @param messageV3
     */
    public void setMessageV3(java.lang.String messageV3) {
        this.messageV3 = messageV3;
    }


    /**
     * Gets the messageV4 value for this Bapiret1.
     * 
     * @return messageV4
     */
    public java.lang.String getMessageV4() {
        return messageV4;
    }


    /**
     * Sets the messageV4 value for this Bapiret1.
     * 
     * @param messageV4
     */
    public void setMessageV4(java.lang.String messageV4) {
        this.messageV4 = messageV4;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Bapiret1)) return false;
        Bapiret1 other = (Bapiret1) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.number==null && other.getNumber()==null) || 
             (this.number!=null &&
              this.number.equals(other.getNumber()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.logNo==null && other.getLogNo()==null) || 
             (this.logNo!=null &&
              this.logNo.equals(other.getLogNo()))) &&
            ((this.logMsgNo==null && other.getLogMsgNo()==null) || 
             (this.logMsgNo!=null &&
              this.logMsgNo.equals(other.getLogMsgNo()))) &&
            ((this.messageV1==null && other.getMessageV1()==null) || 
             (this.messageV1!=null &&
              this.messageV1.equals(other.getMessageV1()))) &&
            ((this.messageV2==null && other.getMessageV2()==null) || 
             (this.messageV2!=null &&
              this.messageV2.equals(other.getMessageV2()))) &&
            ((this.messageV3==null && other.getMessageV3()==null) || 
             (this.messageV3!=null &&
              this.messageV3.equals(other.getMessageV3()))) &&
            ((this.messageV4==null && other.getMessageV4()==null) || 
             (this.messageV4!=null &&
              this.messageV4.equals(other.getMessageV4())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getLogNo() != null) {
            _hashCode += getLogNo().hashCode();
        }
        if (getLogMsgNo() != null) {
            _hashCode += getLogMsgNo().hashCode();
        }
        if (getMessageV1() != null) {
            _hashCode += getMessageV1().hashCode();
        }
        if (getMessageV2() != null) {
            _hashCode += getMessageV2().hashCode();
        }
        if (getMessageV3() != null) {
            _hashCode += getMessageV3().hashCode();
        }
        if (getMessageV4() != null) {
            _hashCode += getMessageV4().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Bapiret1.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "Bapiret1"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LogNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("logMsgNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LogMsgNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageV1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MessageV1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageV2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MessageV2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageV3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MessageV3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageV4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MessageV4"));
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
