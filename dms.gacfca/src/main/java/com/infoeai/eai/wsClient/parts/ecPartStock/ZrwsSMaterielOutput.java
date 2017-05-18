/**
 * ZrwsSMaterielOutput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.ecPartStock;

public class ZrwsSMaterielOutput  implements java.io.Serializable {
    private java.lang.String matnr;

    private java.lang.String werks;

    private java.lang.String lgort;

    private java.lang.String lgobe;

    private java.math.BigDecimal qtycom;

    private java.lang.String menis;

    public ZrwsSMaterielOutput() {
    }

    public ZrwsSMaterielOutput(
           java.lang.String matnr,
           java.lang.String werks,
           java.lang.String lgort,
           java.lang.String lgobe,
           java.math.BigDecimal qtycom,
           java.lang.String menis) {
           this.matnr = matnr;
           this.werks = werks;
           this.lgort = lgort;
           this.lgobe = lgobe;
           this.qtycom = qtycom;
           this.menis = menis;
    }


    /**
     * Gets the matnr value for this ZrwsSMaterielOutput.
     * 
     * @return matnr
     */
    public java.lang.String getMatnr() {
        return matnr;
    }


    /**
     * Sets the matnr value for this ZrwsSMaterielOutput.
     * 
     * @param matnr
     */
    public void setMatnr(java.lang.String matnr) {
        this.matnr = matnr;
    }


    /**
     * Gets the werks value for this ZrwsSMaterielOutput.
     * 
     * @return werks
     */
    public java.lang.String getWerks() {
        return werks;
    }


    /**
     * Sets the werks value for this ZrwsSMaterielOutput.
     * 
     * @param werks
     */
    public void setWerks(java.lang.String werks) {
        this.werks = werks;
    }


    /**
     * Gets the lgort value for this ZrwsSMaterielOutput.
     * 
     * @return lgort
     */
    public java.lang.String getLgort() {
        return lgort;
    }


    /**
     * Sets the lgort value for this ZrwsSMaterielOutput.
     * 
     * @param lgort
     */
    public void setLgort(java.lang.String lgort) {
        this.lgort = lgort;
    }


    /**
     * Gets the lgobe value for this ZrwsSMaterielOutput.
     * 
     * @return lgobe
     */
    public java.lang.String getLgobe() {
        return lgobe;
    }


    /**
     * Sets the lgobe value for this ZrwsSMaterielOutput.
     * 
     * @param lgobe
     */
    public void setLgobe(java.lang.String lgobe) {
        this.lgobe = lgobe;
    }


    /**
     * Gets the qtycom value for this ZrwsSMaterielOutput.
     * 
     * @return qtycom
     */
    public java.math.BigDecimal getQtycom() {
        return qtycom;
    }


    /**
     * Sets the qtycom value for this ZrwsSMaterielOutput.
     * 
     * @param qtycom
     */
    public void setQtycom(java.math.BigDecimal qtycom) {
        this.qtycom = qtycom;
    }


    /**
     * Gets the menis value for this ZrwsSMaterielOutput.
     * 
     * @return menis
     */
    public java.lang.String getMenis() {
        return menis;
    }


    /**
     * Sets the menis value for this ZrwsSMaterielOutput.
     * 
     * @param menis
     */
    public void setMenis(java.lang.String menis) {
        this.menis = menis;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZrwsSMaterielOutput)) return false;
        ZrwsSMaterielOutput other = (ZrwsSMaterielOutput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matnr==null && other.getMatnr()==null) || 
             (this.matnr!=null &&
              this.matnr.equals(other.getMatnr()))) &&
            ((this.werks==null && other.getWerks()==null) || 
             (this.werks!=null &&
              this.werks.equals(other.getWerks()))) &&
            ((this.lgort==null && other.getLgort()==null) || 
             (this.lgort!=null &&
              this.lgort.equals(other.getLgort()))) &&
            ((this.lgobe==null && other.getLgobe()==null) || 
             (this.lgobe!=null &&
              this.lgobe.equals(other.getLgobe()))) &&
            ((this.qtycom==null && other.getQtycom()==null) || 
             (this.qtycom!=null &&
              this.qtycom.equals(other.getQtycom()))) &&
            ((this.menis==null && other.getMenis()==null) || 
             (this.menis!=null &&
              this.menis.equals(other.getMenis())));
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
        if (getMatnr() != null) {
            _hashCode += getMatnr().hashCode();
        }
        if (getWerks() != null) {
            _hashCode += getWerks().hashCode();
        }
        if (getLgort() != null) {
            _hashCode += getLgort().hashCode();
        }
        if (getLgobe() != null) {
            _hashCode += getLgobe().hashCode();
        }
        if (getQtycom() != null) {
            _hashCode += getQtycom().hashCode();
        }
        if (getMenis() != null) {
            _hashCode += getMenis().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZrwsSMaterielOutput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "ZrwsSMaterielOutput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matnr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Matnr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("werks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Werks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lgort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Lgort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lgobe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Lgobe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qtycom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Qtycom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("menis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Menis"));
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
