/**
 * ZlvsdwsGf20Out.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.sparePartsBoOutput;

public class ZlvsdwsGf20Out  implements java.io.Serializable {
    private java.lang.String ordertype;

    private java.lang.String vbeln;

    private java.lang.String audat;

    private java.lang.String matnr;

    private java.lang.String maktx;

    private java.lang.String zmeng;

    private java.lang.String dtstock;

    private java.lang.String dtpod;

    private java.lang.String resolved;

    private java.lang.String bstnk;

    public ZlvsdwsGf20Out() {
    }

    public ZlvsdwsGf20Out(
           java.lang.String ordertype,
           java.lang.String vbeln,
           java.lang.String audat,
           java.lang.String matnr,
           java.lang.String maktx,
           java.lang.String zmeng,
           java.lang.String dtstock,
           java.lang.String dtpod,
           java.lang.String resolved,
           java.lang.String bstnk) {
           this.ordertype = ordertype;
           this.vbeln = vbeln;
           this.audat = audat;
           this.matnr = matnr;
           this.maktx = maktx;
           this.zmeng = zmeng;
           this.dtstock = dtstock;
           this.dtpod = dtpod;
           this.resolved = resolved;
           this.bstnk = bstnk;
    }


    /**
     * Gets the ordertype value for this ZlvsdwsGf20Out.
     * 
     * @return ordertype
     */
    public java.lang.String getOrdertype() {
        return ordertype;
    }


    /**
     * Sets the ordertype value for this ZlvsdwsGf20Out.
     * 
     * @param ordertype
     */
    public void setOrdertype(java.lang.String ordertype) {
        this.ordertype = ordertype;
    }


    /**
     * Gets the vbeln value for this ZlvsdwsGf20Out.
     * 
     * @return vbeln
     */
    public java.lang.String getVbeln() {
        return vbeln;
    }


    /**
     * Sets the vbeln value for this ZlvsdwsGf20Out.
     * 
     * @param vbeln
     */
    public void setVbeln(java.lang.String vbeln) {
        this.vbeln = vbeln;
    }


    /**
     * Gets the audat value for this ZlvsdwsGf20Out.
     * 
     * @return audat
     */
    public java.lang.String getAudat() {
        return audat;
    }


    /**
     * Sets the audat value for this ZlvsdwsGf20Out.
     * 
     * @param audat
     */
    public void setAudat(java.lang.String audat) {
        this.audat = audat;
    }


    /**
     * Gets the matnr value for this ZlvsdwsGf20Out.
     * 
     * @return matnr
     */
    public java.lang.String getMatnr() {
        return matnr;
    }


    /**
     * Sets the matnr value for this ZlvsdwsGf20Out.
     * 
     * @param matnr
     */
    public void setMatnr(java.lang.String matnr) {
        this.matnr = matnr;
    }


    /**
     * Gets the maktx value for this ZlvsdwsGf20Out.
     * 
     * @return maktx
     */
    public java.lang.String getMaktx() {
        return maktx;
    }


    /**
     * Sets the maktx value for this ZlvsdwsGf20Out.
     * 
     * @param maktx
     */
    public void setMaktx(java.lang.String maktx) {
        this.maktx = maktx;
    }


    /**
     * Gets the zmeng value for this ZlvsdwsGf20Out.
     * 
     * @return zmeng
     */
    public java.lang.String getZmeng() {
        return zmeng;
    }


    /**
     * Sets the zmeng value for this ZlvsdwsGf20Out.
     * 
     * @param zmeng
     */
    public void setZmeng(java.lang.String zmeng) {
        this.zmeng = zmeng;
    }


    /**
     * Gets the dtstock value for this ZlvsdwsGf20Out.
     * 
     * @return dtstock
     */
    public java.lang.String getDtstock() {
        return dtstock;
    }


    /**
     * Sets the dtstock value for this ZlvsdwsGf20Out.
     * 
     * @param dtstock
     */
    public void setDtstock(java.lang.String dtstock) {
        this.dtstock = dtstock;
    }


    /**
     * Gets the dtpod value for this ZlvsdwsGf20Out.
     * 
     * @return dtpod
     */
    public java.lang.String getDtpod() {
        return dtpod;
    }


    /**
     * Sets the dtpod value for this ZlvsdwsGf20Out.
     * 
     * @param dtpod
     */
    public void setDtpod(java.lang.String dtpod) {
        this.dtpod = dtpod;
    }


    /**
     * Gets the resolved value for this ZlvsdwsGf20Out.
     * 
     * @return resolved
     */
    public java.lang.String getResolved() {
        return resolved;
    }


    /**
     * Sets the resolved value for this ZlvsdwsGf20Out.
     * 
     * @param resolved
     */
    public void setResolved(java.lang.String resolved) {
        this.resolved = resolved;
    }


    /**
     * Gets the bstnk value for this ZlvsdwsGf20Out.
     * 
     * @return bstnk
     */
    public java.lang.String getBstnk() {
        return bstnk;
    }


    /**
     * Sets the bstnk value for this ZlvsdwsGf20Out.
     * 
     * @param bstnk
     */
    public void setBstnk(java.lang.String bstnk) {
        this.bstnk = bstnk;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZlvsdwsGf20Out)) return false;
        ZlvsdwsGf20Out other = (ZlvsdwsGf20Out) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ordertype==null && other.getOrdertype()==null) || 
             (this.ordertype!=null &&
              this.ordertype.equals(other.getOrdertype()))) &&
            ((this.vbeln==null && other.getVbeln()==null) || 
             (this.vbeln!=null &&
              this.vbeln.equals(other.getVbeln()))) &&
            ((this.audat==null && other.getAudat()==null) || 
             (this.audat!=null &&
              this.audat.equals(other.getAudat()))) &&
            ((this.matnr==null && other.getMatnr()==null) || 
             (this.matnr!=null &&
              this.matnr.equals(other.getMatnr()))) &&
            ((this.maktx==null && other.getMaktx()==null) || 
             (this.maktx!=null &&
              this.maktx.equals(other.getMaktx()))) &&
            ((this.zmeng==null && other.getZmeng()==null) || 
             (this.zmeng!=null &&
              this.zmeng.equals(other.getZmeng()))) &&
            ((this.dtstock==null && other.getDtstock()==null) || 
             (this.dtstock!=null &&
              this.dtstock.equals(other.getDtstock()))) &&
            ((this.dtpod==null && other.getDtpod()==null) || 
             (this.dtpod!=null &&
              this.dtpod.equals(other.getDtpod()))) &&
            ((this.resolved==null && other.getResolved()==null) || 
             (this.resolved!=null &&
              this.resolved.equals(other.getResolved()))) &&
            ((this.bstnk==null && other.getBstnk()==null) || 
             (this.bstnk!=null &&
              this.bstnk.equals(other.getBstnk())));
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
        if (getOrdertype() != null) {
            _hashCode += getOrdertype().hashCode();
        }
        if (getVbeln() != null) {
            _hashCode += getVbeln().hashCode();
        }
        if (getAudat() != null) {
            _hashCode += getAudat().hashCode();
        }
        if (getMatnr() != null) {
            _hashCode += getMatnr().hashCode();
        }
        if (getMaktx() != null) {
            _hashCode += getMaktx().hashCode();
        }
        if (getZmeng() != null) {
            _hashCode += getZmeng().hashCode();
        }
        if (getDtstock() != null) {
            _hashCode += getDtstock().hashCode();
        }
        if (getDtpod() != null) {
            _hashCode += getDtpod().hashCode();
        }
        if (getResolved() != null) {
            _hashCode += getResolved().hashCode();
        }
        if (getBstnk() != null) {
            _hashCode += getBstnk().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZlvsdwsGf20Out.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZlvsdwsGf20Out"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordertype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Ordertype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vbeln");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Vbeln"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("audat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Audat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matnr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Matnr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maktx");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Maktx"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zmeng");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Zmeng"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtstock");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Dtstock"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtpod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Dtpod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resolved");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Resolved"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bstnk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Bstnk"));
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
