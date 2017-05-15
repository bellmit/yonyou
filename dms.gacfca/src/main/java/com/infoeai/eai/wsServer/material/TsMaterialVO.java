/**
 * TsMaterialVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.material;

public class TsMaterialVO  implements java.io.Serializable {
    private java.lang.Long materialId;

    private java.lang.String materialCode;

    private java.lang.String materialName;

    private java.lang.Integer materialLevel;

    private java.lang.String modelYear;

    private java.lang.String parentMaterialCode;

    private java.lang.String remark;

    private java.lang.Integer status;

    private java.lang.Integer isDel;

    private java.lang.String oemCompanyCode;

    private java.lang.String oemCompanyName;

    public TsMaterialVO() {
    }

    public TsMaterialVO(
           java.lang.Long materialId,
           java.lang.String materialCode,
           java.lang.String materialName,
           java.lang.Integer materialLevel,
           java.lang.String modelYear,
           java.lang.String parentMaterialCode,
           java.lang.String remark,
           java.lang.Integer status,
           java.lang.Integer isDel,
           java.lang.String oemCompanyCode,
           java.lang.String oemCompanyName) {
           this.materialId = materialId;
           this.materialCode = materialCode;
           this.materialName = materialName;
           this.materialLevel = materialLevel;
           this.modelYear = modelYear;
           this.parentMaterialCode = parentMaterialCode;
           this.remark = remark;
           this.status = status;
           this.isDel = isDel;
           this.oemCompanyCode = oemCompanyCode;
           this.oemCompanyName = oemCompanyName;
    }


    /**
     * Gets the materialId value for this TsMaterialVO.
     * 
     * @return materialId
     */
    public java.lang.Long getMaterialId() {
        return materialId;
    }


    /**
     * Sets the materialId value for this TsMaterialVO.
     * 
     * @param materialId
     */
    public void setMaterialId(java.lang.Long materialId) {
        this.materialId = materialId;
    }


    /**
     * Gets the materialCode value for this TsMaterialVO.
     * 
     * @return materialCode
     */
    public java.lang.String getMaterialCode() {
        return materialCode;
    }


    /**
     * Sets the materialCode value for this TsMaterialVO.
     * 
     * @param materialCode
     */
    public void setMaterialCode(java.lang.String materialCode) {
        this.materialCode = materialCode;
    }


    /**
     * Gets the materialName value for this TsMaterialVO.
     * 
     * @return materialName
     */
    public java.lang.String getMaterialName() {
        return materialName;
    }


    /**
     * Sets the materialName value for this TsMaterialVO.
     * 
     * @param materialName
     */
    public void setMaterialName(java.lang.String materialName) {
        this.materialName = materialName;
    }


    /**
     * Gets the materialLevel value for this TsMaterialVO.
     * 
     * @return materialLevel
     */
    public java.lang.Integer getMaterialLevel() {
        return materialLevel;
    }


    /**
     * Sets the materialLevel value for this TsMaterialVO.
     * 
     * @param materialLevel
     */
    public void setMaterialLevel(java.lang.Integer materialLevel) {
        this.materialLevel = materialLevel;
    }


    /**
     * Gets the modelYear value for this TsMaterialVO.
     * 
     * @return modelYear
     */
    public java.lang.String getModelYear() {
        return modelYear;
    }


    /**
     * Sets the modelYear value for this TsMaterialVO.
     * 
     * @param modelYear
     */
    public void setModelYear(java.lang.String modelYear) {
        this.modelYear = modelYear;
    }


    /**
     * Gets the parentMaterialCode value for this TsMaterialVO.
     * 
     * @return parentMaterialCode
     */
    public java.lang.String getParentMaterialCode() {
        return parentMaterialCode;
    }


    /**
     * Sets the parentMaterialCode value for this TsMaterialVO.
     * 
     * @param parentMaterialCode
     */
    public void setParentMaterialCode(java.lang.String parentMaterialCode) {
        this.parentMaterialCode = parentMaterialCode;
    }


    /**
     * Gets the remark value for this TsMaterialVO.
     * 
     * @return remark
     */
    public java.lang.String getRemark() {
        return remark;
    }


    /**
     * Sets the remark value for this TsMaterialVO.
     * 
     * @param remark
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }


    /**
     * Gets the status value for this TsMaterialVO.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this TsMaterialVO.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }


    /**
     * Gets the isDel value for this TsMaterialVO.
     * 
     * @return isDel
     */
    public java.lang.Integer getIsDel() {
        return isDel;
    }


    /**
     * Sets the isDel value for this TsMaterialVO.
     * 
     * @param isDel
     */
    public void setIsDel(java.lang.Integer isDel) {
        this.isDel = isDel;
    }


    /**
     * Gets the oemCompanyCode value for this TsMaterialVO.
     * 
     * @return oemCompanyCode
     */
    public java.lang.String getOemCompanyCode() {
        return oemCompanyCode;
    }


    /**
     * Sets the oemCompanyCode value for this TsMaterialVO.
     * 
     * @param oemCompanyCode
     */
    public void setOemCompanyCode(java.lang.String oemCompanyCode) {
        this.oemCompanyCode = oemCompanyCode;
    }


    /**
     * Gets the oemCompanyName value for this TsMaterialVO.
     * 
     * @return oemCompanyName
     */
    public java.lang.String getOemCompanyName() {
        return oemCompanyName;
    }


    /**
     * Sets the oemCompanyName value for this TsMaterialVO.
     * 
     * @param oemCompanyName
     */
    public void setOemCompanyName(java.lang.String oemCompanyName) {
        this.oemCompanyName = oemCompanyName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TsMaterialVO)) return false;
        TsMaterialVO other = (TsMaterialVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.materialId==null && other.getMaterialId()==null) || 
             (this.materialId!=null &&
              this.materialId.equals(other.getMaterialId()))) &&
            ((this.materialCode==null && other.getMaterialCode()==null) || 
             (this.materialCode!=null &&
              this.materialCode.equals(other.getMaterialCode()))) &&
            ((this.materialName==null && other.getMaterialName()==null) || 
             (this.materialName!=null &&
              this.materialName.equals(other.getMaterialName()))) &&
            ((this.materialLevel==null && other.getMaterialLevel()==null) || 
             (this.materialLevel!=null &&
              this.materialLevel.equals(other.getMaterialLevel()))) &&
            ((this.modelYear==null && other.getModelYear()==null) || 
             (this.modelYear!=null &&
              this.modelYear.equals(other.getModelYear()))) &&
            ((this.parentMaterialCode==null && other.getParentMaterialCode()==null) || 
             (this.parentMaterialCode!=null &&
              this.parentMaterialCode.equals(other.getParentMaterialCode()))) &&
            ((this.remark==null && other.getRemark()==null) || 
             (this.remark!=null &&
              this.remark.equals(other.getRemark()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.isDel==null && other.getIsDel()==null) || 
             (this.isDel!=null &&
              this.isDel.equals(other.getIsDel()))) &&
            ((this.oemCompanyCode==null && other.getOemCompanyCode()==null) || 
             (this.oemCompanyCode!=null &&
              this.oemCompanyCode.equals(other.getOemCompanyCode()))) &&
            ((this.oemCompanyName==null && other.getOemCompanyName()==null) || 
             (this.oemCompanyName!=null &&
              this.oemCompanyName.equals(other.getOemCompanyName())));
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
        if (getMaterialId() != null) {
            _hashCode += getMaterialId().hashCode();
        }
        if (getMaterialCode() != null) {
            _hashCode += getMaterialCode().hashCode();
        }
        if (getMaterialName() != null) {
            _hashCode += getMaterialName().hashCode();
        }
        if (getMaterialLevel() != null) {
            _hashCode += getMaterialLevel().hashCode();
        }
        if (getModelYear() != null) {
            _hashCode += getModelYear().hashCode();
        }
        if (getParentMaterialCode() != null) {
            _hashCode += getParentMaterialCode().hashCode();
        }
        if (getRemark() != null) {
            _hashCode += getRemark().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getIsDel() != null) {
            _hashCode += getIsDel().hashCode();
        }
        if (getOemCompanyCode() != null) {
            _hashCode += getOemCompanyCode().hashCode();
        }
        if (getOemCompanyName() != null) {
            _hashCode += getOemCompanyName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TsMaterialVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsServer.eai.infoeai.com/material/", "tsMaterialVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modelYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentMaterialCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentMaterialCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remark");
        elemField.setXmlName(new javax.xml.namespace.QName("", "remark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isDel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oemCompanyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oemCompanyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oemCompanyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oemCompanyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
