/**
 * MmQuerySJVTtPartBuyPO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsClient.parts.QuerySJVTtPartBuy;

public class MmQuerySJVTtPartBuyPO  implements java.io.Serializable {
    private java.lang.String entityCode;

    private java.lang.String deliveryOrderNo;

    private java.lang.String sapOrderNo;

    private java.lang.Integer partOrderType;

    private java.lang.String deliveryCompany;

    private java.lang.String transType;

    private java.lang.String partNo;

    private java.lang.String partName;

    private java.lang.String transNo;

    private java.lang.Double allowClaimQuantity;

    private java.lang.Double planPrice;

    private java.lang.Double totalAmount;

    private java.lang.Double supplyQty;

    private java.util.Calendar deliveryTime;

    private java.util.Calendar createDate;

    private java.lang.String stockInNo;

    private java.lang.String storageCode;

    private java.lang.Double storageInQuantity;

    private java.lang.String transStock;

    public MmQuerySJVTtPartBuyPO() {
    }

    public MmQuerySJVTtPartBuyPO(
           java.lang.String entityCode,
           java.lang.String deliveryOrderNo,
           java.lang.String sapOrderNo,
           java.lang.Integer partOrderType,
           java.lang.String deliveryCompany,
           java.lang.String transType,
           java.lang.String partNo,
           java.lang.String partName,
           java.lang.String transNo,
           java.lang.Double allowClaimQuantity,
           java.lang.Double planPrice,
           java.lang.Double totalAmount,
           java.lang.Double supplyQty,
           java.util.Calendar deliveryTime,
           java.util.Calendar createDate,
           java.lang.String stockInNo,
           java.lang.String storageCode,
           java.lang.Double storageInQuantity,
           java.lang.String transStock) {
           this.entityCode = entityCode;
           this.deliveryOrderNo = deliveryOrderNo;
           this.sapOrderNo = sapOrderNo;
           this.partOrderType = partOrderType;
           this.deliveryCompany = deliveryCompany;
           this.transType = transType;
           this.partNo = partNo;
           this.partName = partName;
           this.transNo = transNo;
           this.allowClaimQuantity = allowClaimQuantity;
           this.planPrice = planPrice;
           this.totalAmount = totalAmount;
           this.supplyQty = supplyQty;
           this.deliveryTime = deliveryTime;
           this.createDate = createDate;
           this.stockInNo = stockInNo;
           this.storageCode = storageCode;
           this.storageInQuantity = storageInQuantity;
           this.transStock = transStock;
    }


    /**
     * Gets the entityCode value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return entityCode
     */
    public java.lang.String getEntityCode() {
        return entityCode;
    }


    /**
     * Sets the entityCode value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param entityCode
     */
    public void setEntityCode(java.lang.String entityCode) {
        this.entityCode = entityCode;
    }


    /**
     * Gets the deliveryOrderNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return deliveryOrderNo
     */
    public java.lang.String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }


    /**
     * Sets the deliveryOrderNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param deliveryOrderNo
     */
    public void setDeliveryOrderNo(java.lang.String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }


    /**
     * Gets the sapOrderNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return sapOrderNo
     */
    public java.lang.String getSapOrderNo() {
        return sapOrderNo;
    }


    /**
     * Sets the sapOrderNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param sapOrderNo
     */
    public void setSapOrderNo(java.lang.String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }


    /**
     * Gets the partOrderType value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return partOrderType
     */
    public java.lang.Integer getPartOrderType() {
        return partOrderType;
    }


    /**
     * Sets the partOrderType value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param partOrderType
     */
    public void setPartOrderType(java.lang.Integer partOrderType) {
        this.partOrderType = partOrderType;
    }


    /**
     * Gets the deliveryCompany value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return deliveryCompany
     */
    public java.lang.String getDeliveryCompany() {
        return deliveryCompany;
    }


    /**
     * Sets the deliveryCompany value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param deliveryCompany
     */
    public void setDeliveryCompany(java.lang.String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }


    /**
     * Gets the transType value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return transType
     */
    public java.lang.String getTransType() {
        return transType;
    }


    /**
     * Sets the transType value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param transType
     */
    public void setTransType(java.lang.String transType) {
        this.transType = transType;
    }


    /**
     * Gets the partNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return partNo
     */
    public java.lang.String getPartNo() {
        return partNo;
    }


    /**
     * Sets the partNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param partNo
     */
    public void setPartNo(java.lang.String partNo) {
        this.partNo = partNo;
    }


    /**
     * Gets the partName value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return partName
     */
    public java.lang.String getPartName() {
        return partName;
    }


    /**
     * Sets the partName value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param partName
     */
    public void setPartName(java.lang.String partName) {
        this.partName = partName;
    }


    /**
     * Gets the transNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return transNo
     */
    public java.lang.String getTransNo() {
        return transNo;
    }


    /**
     * Sets the transNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param transNo
     */
    public void setTransNo(java.lang.String transNo) {
        this.transNo = transNo;
    }


    /**
     * Gets the allowClaimQuantity value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return allowClaimQuantity
     */
    public java.lang.Double getAllowClaimQuantity() {
        return allowClaimQuantity;
    }


    /**
     * Sets the allowClaimQuantity value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param allowClaimQuantity
     */
    public void setAllowClaimQuantity(java.lang.Double allowClaimQuantity) {
        this.allowClaimQuantity = allowClaimQuantity;
    }


    /**
     * Gets the planPrice value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return planPrice
     */
    public java.lang.Double getPlanPrice() {
        return planPrice;
    }


    /**
     * Sets the planPrice value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param planPrice
     */
    public void setPlanPrice(java.lang.Double planPrice) {
        this.planPrice = planPrice;
    }


    /**
     * Gets the totalAmount value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return totalAmount
     */
    public java.lang.Double getTotalAmount() {
        return totalAmount;
    }


    /**
     * Sets the totalAmount value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param totalAmount
     */
    public void setTotalAmount(java.lang.Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    /**
     * Gets the supplyQty value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return supplyQty
     */
    public java.lang.Double getSupplyQty() {
        return supplyQty;
    }


    /**
     * Sets the supplyQty value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param supplyQty
     */
    public void setSupplyQty(java.lang.Double supplyQty) {
        this.supplyQty = supplyQty;
    }


    /**
     * Gets the deliveryTime value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return deliveryTime
     */
    public java.util.Calendar getDeliveryTime() {
        return deliveryTime;
    }


    /**
     * Sets the deliveryTime value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param deliveryTime
     */
    public void setDeliveryTime(java.util.Calendar deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    /**
     * Gets the createDate value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the stockInNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return stockInNo
     */
    public java.lang.String getStockInNo() {
        return stockInNo;
    }


    /**
     * Sets the stockInNo value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param stockInNo
     */
    public void setStockInNo(java.lang.String stockInNo) {
        this.stockInNo = stockInNo;
    }


    /**
     * Gets the storageCode value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return storageCode
     */
    public java.lang.String getStorageCode() {
        return storageCode;
    }


    /**
     * Sets the storageCode value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param storageCode
     */
    public void setStorageCode(java.lang.String storageCode) {
        this.storageCode = storageCode;
    }


    /**
     * Gets the storageInQuantity value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return storageInQuantity
     */
    public java.lang.Double getStorageInQuantity() {
        return storageInQuantity;
    }


    /**
     * Sets the storageInQuantity value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param storageInQuantity
     */
    public void setStorageInQuantity(java.lang.Double storageInQuantity) {
        this.storageInQuantity = storageInQuantity;
    }


    /**
     * Gets the transStock value for this MmQuerySJVTtPartBuyPO.
     * 
     * @return transStock
     */
    public java.lang.String getTransStock() {
        return transStock;
    }


    /**
     * Sets the transStock value for this MmQuerySJVTtPartBuyPO.
     * 
     * @param transStock
     */
    public void setTransStock(java.lang.String transStock) {
        this.transStock = transStock;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MmQuerySJVTtPartBuyPO)) return false;
        MmQuerySJVTtPartBuyPO other = (MmQuerySJVTtPartBuyPO) obj;
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
            ((this.sapOrderNo==null && other.getSapOrderNo()==null) || 
             (this.sapOrderNo!=null &&
              this.sapOrderNo.equals(other.getSapOrderNo()))) &&
            ((this.partOrderType==null && other.getPartOrderType()==null) || 
             (this.partOrderType!=null &&
              this.partOrderType.equals(other.getPartOrderType()))) &&
            ((this.deliveryCompany==null && other.getDeliveryCompany()==null) || 
             (this.deliveryCompany!=null &&
              this.deliveryCompany.equals(other.getDeliveryCompany()))) &&
            ((this.transType==null && other.getTransType()==null) || 
             (this.transType!=null &&
              this.transType.equals(other.getTransType()))) &&
            ((this.partNo==null && other.getPartNo()==null) || 
             (this.partNo!=null &&
              this.partNo.equals(other.getPartNo()))) &&
            ((this.partName==null && other.getPartName()==null) || 
             (this.partName!=null &&
              this.partName.equals(other.getPartName()))) &&
            ((this.transNo==null && other.getTransNo()==null) || 
             (this.transNo!=null &&
              this.transNo.equals(other.getTransNo()))) &&
            ((this.allowClaimQuantity==null && other.getAllowClaimQuantity()==null) || 
             (this.allowClaimQuantity!=null &&
              this.allowClaimQuantity.equals(other.getAllowClaimQuantity()))) &&
            ((this.planPrice==null && other.getPlanPrice()==null) || 
             (this.planPrice!=null &&
              this.planPrice.equals(other.getPlanPrice()))) &&
            ((this.totalAmount==null && other.getTotalAmount()==null) || 
             (this.totalAmount!=null &&
              this.totalAmount.equals(other.getTotalAmount()))) &&
            ((this.supplyQty==null && other.getSupplyQty()==null) || 
             (this.supplyQty!=null &&
              this.supplyQty.equals(other.getSupplyQty()))) &&
            ((this.deliveryTime==null && other.getDeliveryTime()==null) || 
             (this.deliveryTime!=null &&
              this.deliveryTime.equals(other.getDeliveryTime()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.stockInNo==null && other.getStockInNo()==null) || 
             (this.stockInNo!=null &&
              this.stockInNo.equals(other.getStockInNo()))) &&
            ((this.storageCode==null && other.getStorageCode()==null) || 
             (this.storageCode!=null &&
              this.storageCode.equals(other.getStorageCode()))) &&
            ((this.storageInQuantity==null && other.getStorageInQuantity()==null) || 
             (this.storageInQuantity!=null &&
              this.storageInQuantity.equals(other.getStorageInQuantity()))) &&
            ((this.transStock==null && other.getTransStock()==null) || 
             (this.transStock!=null &&
              this.transStock.equals(other.getTransStock())));
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
        if (getSapOrderNo() != null) {
            _hashCode += getSapOrderNo().hashCode();
        }
        if (getPartOrderType() != null) {
            _hashCode += getPartOrderType().hashCode();
        }
        if (getDeliveryCompany() != null) {
            _hashCode += getDeliveryCompany().hashCode();
        }
        if (getTransType() != null) {
            _hashCode += getTransType().hashCode();
        }
        if (getPartNo() != null) {
            _hashCode += getPartNo().hashCode();
        }
        if (getPartName() != null) {
            _hashCode += getPartName().hashCode();
        }
        if (getTransNo() != null) {
            _hashCode += getTransNo().hashCode();
        }
        if (getAllowClaimQuantity() != null) {
            _hashCode += getAllowClaimQuantity().hashCode();
        }
        if (getPlanPrice() != null) {
            _hashCode += getPlanPrice().hashCode();
        }
        if (getTotalAmount() != null) {
            _hashCode += getTotalAmount().hashCode();
        }
        if (getSupplyQty() != null) {
            _hashCode += getSupplyQty().hashCode();
        }
        if (getDeliveryTime() != null) {
            _hashCode += getDeliveryTime().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getStockInNo() != null) {
            _hashCode += getStockInNo().hashCode();
        }
        if (getStorageCode() != null) {
            _hashCode += getStorageCode().hashCode();
        }
        if (getStorageInQuantity() != null) {
            _hashCode += getStorageInQuantity().hashCode();
        }
        if (getTransStock() != null) {
            _hashCode += getTransStock().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MmQuerySJVTtPartBuyPO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "MmQuerySJVTtPartBuyPO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "entityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryOrderNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "deliveryOrderNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sapOrderNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "sapOrderNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partOrderType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "partOrderType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryCompany");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "deliveryCompany"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "transType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "partNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "partName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "transNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowClaimQuantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "allowClaimQuantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("planPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "planPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "totalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supplyQty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "supplyQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "deliveryTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockInNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "stockInNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("storageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "storageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("storageInQuantity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "storageInQuantity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transStock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.example.org/QuerySJVTtPartBuy/", "transStock"));
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
