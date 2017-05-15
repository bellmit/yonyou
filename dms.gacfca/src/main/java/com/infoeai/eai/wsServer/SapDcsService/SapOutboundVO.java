/**
 * SapOutboundVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.SapDcsService;

import com.infoeai.eai.action.ncserp.intf.ISAPOutBoundVO;

@SuppressWarnings("serial")
public class SapOutboundVO  implements java.io.Serializable,ISAPOutBoundVO {
    private java.lang.String actionCode;

    private java.lang.String actionDate;

    private java.lang.String actionTime;

    private java.lang.String vin;

    private java.lang.String engineNumber;

    private java.lang.String productionDate;

    private java.lang.String primaryStatus;

    private java.lang.String secondaryStatus;

    private java.lang.String shipDate;

    private java.lang.String eta;

    private java.lang.String model;

    private java.lang.String modelyear;

    private java.lang.String characteristicColour;

    private java.lang.String characteristicTrim;

    private java.lang.String characteristicFactoryStandardOptions;

    private java.lang.String characteristicFactoryOptions;

    private java.lang.String characteristicLocalOptions;

    private java.lang.String soldto;

    private java.lang.String invoiceNumber;

    private java.lang.String vehicleUsage;

    private java.lang.String standardPrice;

    private java.lang.String wholesalePrice;

    private java.lang.String portofdestination;

    public SapOutboundVO() {
    }

    public SapOutboundVO(
           java.lang.String actionCode,
           java.lang.String actionDate,
           java.lang.String actionTime,
           java.lang.String vin,
           java.lang.String engineNumber,
           java.lang.String productionDate,
           java.lang.String primaryStatus,
           java.lang.String secondaryStatus,
           java.lang.String shipDate,
           java.lang.String eta,
           java.lang.String model,
           java.lang.String modelyear,
           java.lang.String characteristicColour,
           java.lang.String characteristicTrim,
           java.lang.String characteristicFactoryStandardOptions,
           java.lang.String characteristicFactoryOptions,
           java.lang.String characteristicLocalOptions,
           java.lang.String soldto,
           java.lang.String invoiceNumber,
           java.lang.String vehicleUsage,
           java.lang.String standardPrice,
           java.lang.String wholesalePrice,
           java.lang.String portofdestination) {
           this.actionCode = actionCode;
           this.actionDate = actionDate;
           this.actionTime = actionTime;
           this.vin = vin;
           this.engineNumber = engineNumber;
           this.productionDate = productionDate;
           this.primaryStatus = primaryStatus;
           this.secondaryStatus = secondaryStatus;
           this.shipDate = shipDate;
           this.eta = eta;
           this.model = model;
           this.modelyear = modelyear;
           this.characteristicColour = characteristicColour;
           this.characteristicTrim = characteristicTrim;
           this.characteristicFactoryStandardOptions = characteristicFactoryStandardOptions;
           this.characteristicFactoryOptions = characteristicFactoryOptions;
           this.characteristicLocalOptions = characteristicLocalOptions;
           this.soldto = soldto;
           this.invoiceNumber = invoiceNumber;
           this.vehicleUsage = vehicleUsage;
           this.standardPrice = standardPrice;
           this.wholesalePrice = wholesalePrice;
           this.portofdestination = portofdestination;
    }


    /**
     * Gets the actionCode value for this SapOutboundVO.
     * 
     * @return actionCode
     */
    public java.lang.String getActionCode() {
        return actionCode;
    }


    /**
     * Sets the actionCode value for this SapOutboundVO.
     * 
     * @param actionCode
     */
    public void setActionCode(java.lang.String actionCode) {
        this.actionCode = actionCode;
    }


    /**
     * Gets the actionDate value for this SapOutboundVO.
     * 
     * @return actionDate
     */
    public java.lang.String getActionDate() {
        return actionDate;
    }


    /**
     * Sets the actionDate value for this SapOutboundVO.
     * 
     * @param actionDate
     */
    public void setActionDate(java.lang.String actionDate) {
        this.actionDate = actionDate;
    }


    /**
     * Gets the actionTime value for this SapOutboundVO.
     * 
     * @return actionTime
     */
    public java.lang.String getActionTime() {
        return actionTime;
    }


    /**
     * Sets the actionTime value for this SapOutboundVO.
     * 
     * @param actionTime
     */
    public void setActionTime(java.lang.String actionTime) {
        this.actionTime = actionTime;
    }


    /**
     * Gets the vin value for this SapOutboundVO.
     * 
     * @return vin
     */
    public java.lang.String getVin() {
        return vin;
    }


    /**
     * Sets the vin value for this SapOutboundVO.
     * 
     * @param vin
     */
    public void setVin(java.lang.String vin) {
        this.vin = vin;
    }


    /**
     * Gets the engineNumber value for this SapOutboundVO.
     * 
     * @return engineNumber
     */
    public java.lang.String getEngineNumber() {
        return engineNumber;
    }


    /**
     * Sets the engineNumber value for this SapOutboundVO.
     * 
     * @param engineNumber
     */
    public void setEngineNumber(java.lang.String engineNumber) {
        this.engineNumber = engineNumber;
    }


    /**
     * Gets the productionDate value for this SapOutboundVO.
     * 
     * @return productionDate
     */
    public java.lang.String getProductionDate() {
        return productionDate;
    }


    /**
     * Sets the productionDate value for this SapOutboundVO.
     * 
     * @param productionDate
     */
    public void setProductionDate(java.lang.String productionDate) {
        this.productionDate = productionDate;
    }


    /**
     * Gets the primaryStatus value for this SapOutboundVO.
     * 
     * @return primaryStatus
     */
    public java.lang.String getPrimaryStatus() {
        return primaryStatus;
    }


    /**
     * Sets the primaryStatus value for this SapOutboundVO.
     * 
     * @param primaryStatus
     */
    public void setPrimaryStatus(java.lang.String primaryStatus) {
        this.primaryStatus = primaryStatus;
    }


    /**
     * Gets the secondaryStatus value for this SapOutboundVO.
     * 
     * @return secondaryStatus
     */
    public java.lang.String getSecondaryStatus() {
        return secondaryStatus;
    }


    /**
     * Sets the secondaryStatus value for this SapOutboundVO.
     * 
     * @param secondaryStatus
     */
    public void setSecondaryStatus(java.lang.String secondaryStatus) {
        this.secondaryStatus = secondaryStatus;
    }


    /**
     * Gets the shipDate value for this SapOutboundVO.
     * 
     * @return shipDate
     */
    public java.lang.String getShipDate() {
        return shipDate;
    }


    /**
     * Sets the shipDate value for this SapOutboundVO.
     * 
     * @param shipDate
     */
    public void setShipDate(java.lang.String shipDate) {
        this.shipDate = shipDate;
    }


    /**
     * Gets the eta value for this SapOutboundVO.
     * 
     * @return eta
     */
    public java.lang.String getEta() {
        return eta;
    }


    /**
     * Sets the eta value for this SapOutboundVO.
     * 
     * @param eta
     */
    public void setEta(java.lang.String eta) {
        this.eta = eta;
    }


    /**
     * Gets the model value for this SapOutboundVO.
     * 
     * @return model
     */
    public java.lang.String getModel() {
        return model;
    }


    /**
     * Sets the model value for this SapOutboundVO.
     * 
     * @param model
     */
    public void setModel(java.lang.String model) {
        this.model = model;
    }


    /**
     * Gets the modelyear value for this SapOutboundVO.
     * 
     * @return modelyear
     */
    public java.lang.String getModelyear() {
        return modelyear;
    }


    /**
     * Sets the modelyear value for this SapOutboundVO.
     * 
     * @param modelyear
     */
    public void setModelyear(java.lang.String modelyear) {
        this.modelyear = modelyear;
    }


    /**
     * Gets the characteristicColour value for this SapOutboundVO.
     * 
     * @return characteristicColour
     */
    public java.lang.String getCharacteristicColour() {
        return characteristicColour;
    }


    /**
     * Sets the characteristicColour value for this SapOutboundVO.
     * 
     * @param characteristicColour
     */
    public void setCharacteristicColour(java.lang.String characteristicColour) {
        this.characteristicColour = characteristicColour;
    }


    /**
     * Gets the characteristicTrim value for this SapOutboundVO.
     * 
     * @return characteristicTrim
     */
    public java.lang.String getCharacteristicTrim() {
        return characteristicTrim;
    }


    /**
     * Sets the characteristicTrim value for this SapOutboundVO.
     * 
     * @param characteristicTrim
     */
    public void setCharacteristicTrim(java.lang.String characteristicTrim) {
        this.characteristicTrim = characteristicTrim;
    }


    /**
     * Gets the characteristicFactoryStandardOptions value for this SapOutboundVO.
     * 
     * @return characteristicFactoryStandardOptions
     */
    public java.lang.String getCharacteristicFactoryStandardOptions() {
        return characteristicFactoryStandardOptions;
    }


    /**
     * Sets the characteristicFactoryStandardOptions value for this SapOutboundVO.
     * 
     * @param characteristicFactoryStandardOptions
     */
    public void setCharacteristicFactoryStandardOptions(java.lang.String characteristicFactoryStandardOptions) {
        this.characteristicFactoryStandardOptions = characteristicFactoryStandardOptions;
    }


    /**
     * Gets the characteristicFactoryOptions value for this SapOutboundVO.
     * 
     * @return characteristicFactoryOptions
     */
    public java.lang.String getCharacteristicFactoryOptions() {
        return characteristicFactoryOptions;
    }


    /**
     * Sets the characteristicFactoryOptions value for this SapOutboundVO.
     * 
     * @param characteristicFactoryOptions
     */
    public void setCharacteristicFactoryOptions(java.lang.String characteristicFactoryOptions) {
        this.characteristicFactoryOptions = characteristicFactoryOptions;
    }


    /**
     * Gets the characteristicLocalOptions value for this SapOutboundVO.
     * 
     * @return characteristicLocalOptions
     */
    public java.lang.String getCharacteristicLocalOptions() {
        return characteristicLocalOptions;
    }


    /**
     * Sets the characteristicLocalOptions value for this SapOutboundVO.
     * 
     * @param characteristicLocalOptions
     */
    public void setCharacteristicLocalOptions(java.lang.String characteristicLocalOptions) {
        this.characteristicLocalOptions = characteristicLocalOptions;
    }


    /**
     * Gets the soldto value for this SapOutboundVO.
     * 
     * @return soldto
     */
    public java.lang.String getSoldto() {
        return soldto;
    }


    /**
     * Sets the soldto value for this SapOutboundVO.
     * 
     * @param soldto
     */
    public void setSoldto(java.lang.String soldto) {
        this.soldto = soldto;
    }


    /**
     * Gets the invoiceNumber value for this SapOutboundVO.
     * 
     * @return invoiceNumber
     */
    public java.lang.String getInvoiceNumber() {
        return invoiceNumber;
    }


    /**
     * Sets the invoiceNumber value for this SapOutboundVO.
     * 
     * @param invoiceNumber
     */
    public void setInvoiceNumber(java.lang.String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    /**
     * Gets the vehicleUsage value for this SapOutboundVO.
     * 
     * @return vehicleUsage
     */
    public java.lang.String getVehicleUsage() {
        return vehicleUsage;
    }


    /**
     * Sets the vehicleUsage value for this SapOutboundVO.
     * 
     * @param vehicleUsage
     */
    public void setVehicleUsage(java.lang.String vehicleUsage) {
        this.vehicleUsage = vehicleUsage;
    }


    /**
     * Gets the standardPrice value for this SapOutboundVO.
     * 
     * @return standardPrice
     */
    public java.lang.String getStandardPrice() {
        return standardPrice;
    }


    /**
     * Sets the standardPrice value for this SapOutboundVO.
     * 
     * @param standardPrice
     */
    public void setStandardPrice(java.lang.String standardPrice) {
        this.standardPrice = standardPrice;
    }


    /**
     * Gets the wholesalePrice value for this SapOutboundVO.
     * 
     * @return wholesalePrice
     */
    public java.lang.String getWholesalePrice() {
        return wholesalePrice;
    }


    /**
     * Sets the wholesalePrice value for this SapOutboundVO.
     * 
     * @param wholesalePrice
     */
    public void setWholesalePrice(java.lang.String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }


    /**
     * Gets the portofdestination value for this SapOutboundVO.
     * 
     * @return portofdestination
     */
    public java.lang.String getPortofdestination() {
        return portofdestination;
    }


    /**
     * Sets the portofdestination value for this SapOutboundVO.
     * 
     * @param portofdestination
     */
    public void setPortofdestination(java.lang.String portofdestination) {
        this.portofdestination = portofdestination;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SapOutboundVO)) return false;
        SapOutboundVO other = (SapOutboundVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actionCode==null && other.getActionCode()==null) || 
             (this.actionCode!=null &&
              this.actionCode.equals(other.getActionCode()))) &&
            ((this.actionDate==null && other.getActionDate()==null) || 
             (this.actionDate!=null &&
              this.actionDate.equals(other.getActionDate()))) &&
            ((this.actionTime==null && other.getActionTime()==null) || 
             (this.actionTime!=null &&
              this.actionTime.equals(other.getActionTime()))) &&
            ((this.vin==null && other.getVin()==null) || 
             (this.vin!=null &&
              this.vin.equals(other.getVin()))) &&
            ((this.engineNumber==null && other.getEngineNumber()==null) || 
             (this.engineNumber!=null &&
              this.engineNumber.equals(other.getEngineNumber()))) &&
            ((this.productionDate==null && other.getProductionDate()==null) || 
             (this.productionDate!=null &&
              this.productionDate.equals(other.getProductionDate()))) &&
            ((this.primaryStatus==null && other.getPrimaryStatus()==null) || 
             (this.primaryStatus!=null &&
              this.primaryStatus.equals(other.getPrimaryStatus()))) &&
            ((this.secondaryStatus==null && other.getSecondaryStatus()==null) || 
             (this.secondaryStatus!=null &&
              this.secondaryStatus.equals(other.getSecondaryStatus()))) &&
            ((this.shipDate==null && other.getShipDate()==null) || 
             (this.shipDate!=null &&
              this.shipDate.equals(other.getShipDate()))) &&
            ((this.eta==null && other.getEta()==null) || 
             (this.eta!=null &&
              this.eta.equals(other.getEta()))) &&
            ((this.model==null && other.getModel()==null) || 
             (this.model!=null &&
              this.model.equals(other.getModel()))) &&
            ((this.modelyear==null && other.getModelyear()==null) || 
             (this.modelyear!=null &&
              this.modelyear.equals(other.getModelyear()))) &&
            ((this.characteristicColour==null && other.getCharacteristicColour()==null) || 
             (this.characteristicColour!=null &&
              this.characteristicColour.equals(other.getCharacteristicColour()))) &&
            ((this.characteristicTrim==null && other.getCharacteristicTrim()==null) || 
             (this.characteristicTrim!=null &&
              this.characteristicTrim.equals(other.getCharacteristicTrim()))) &&
            ((this.characteristicFactoryStandardOptions==null && other.getCharacteristicFactoryStandardOptions()==null) || 
             (this.characteristicFactoryStandardOptions!=null &&
              this.characteristicFactoryStandardOptions.equals(other.getCharacteristicFactoryStandardOptions()))) &&
            ((this.characteristicFactoryOptions==null && other.getCharacteristicFactoryOptions()==null) || 
             (this.characteristicFactoryOptions!=null &&
              this.characteristicFactoryOptions.equals(other.getCharacteristicFactoryOptions()))) &&
            ((this.characteristicLocalOptions==null && other.getCharacteristicLocalOptions()==null) || 
             (this.characteristicLocalOptions!=null &&
              this.characteristicLocalOptions.equals(other.getCharacteristicLocalOptions()))) &&
            ((this.soldto==null && other.getSoldto()==null) || 
             (this.soldto!=null &&
              this.soldto.equals(other.getSoldto()))) &&
            ((this.invoiceNumber==null && other.getInvoiceNumber()==null) || 
             (this.invoiceNumber!=null &&
              this.invoiceNumber.equals(other.getInvoiceNumber()))) &&
            ((this.vehicleUsage==null && other.getVehicleUsage()==null) || 
             (this.vehicleUsage!=null &&
              this.vehicleUsage.equals(other.getVehicleUsage()))) &&
            ((this.standardPrice==null && other.getStandardPrice()==null) || 
             (this.standardPrice!=null &&
              this.standardPrice.equals(other.getStandardPrice()))) &&
            ((this.wholesalePrice==null && other.getWholesalePrice()==null) || 
             (this.wholesalePrice!=null &&
              this.wholesalePrice.equals(other.getWholesalePrice()))) &&
            ((this.portofdestination==null && other.getPortofdestination()==null) || 
             (this.portofdestination!=null &&
              this.portofdestination.equals(other.getPortofdestination())));
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
        if (getActionCode() != null) {
            _hashCode += getActionCode().hashCode();
        }
        if (getActionDate() != null) {
            _hashCode += getActionDate().hashCode();
        }
        if (getActionTime() != null) {
            _hashCode += getActionTime().hashCode();
        }
        if (getVin() != null) {
            _hashCode += getVin().hashCode();
        }
        if (getEngineNumber() != null) {
            _hashCode += getEngineNumber().hashCode();
        }
        if (getProductionDate() != null) {
            _hashCode += getProductionDate().hashCode();
        }
        if (getPrimaryStatus() != null) {
            _hashCode += getPrimaryStatus().hashCode();
        }
        if (getSecondaryStatus() != null) {
            _hashCode += getSecondaryStatus().hashCode();
        }
        if (getShipDate() != null) {
            _hashCode += getShipDate().hashCode();
        }
        if (getEta() != null) {
            _hashCode += getEta().hashCode();
        }
        if (getModel() != null) {
            _hashCode += getModel().hashCode();
        }
        if (getModelyear() != null) {
            _hashCode += getModelyear().hashCode();
        }
        if (getCharacteristicColour() != null) {
            _hashCode += getCharacteristicColour().hashCode();
        }
        if (getCharacteristicTrim() != null) {
            _hashCode += getCharacteristicTrim().hashCode();
        }
        if (getCharacteristicFactoryStandardOptions() != null) {
            _hashCode += getCharacteristicFactoryStandardOptions().hashCode();
        }
        if (getCharacteristicFactoryOptions() != null) {
            _hashCode += getCharacteristicFactoryOptions().hashCode();
        }
        if (getCharacteristicLocalOptions() != null) {
            _hashCode += getCharacteristicLocalOptions().hashCode();
        }
        if (getSoldto() != null) {
            _hashCode += getSoldto().hashCode();
        }
        if (getInvoiceNumber() != null) {
            _hashCode += getInvoiceNumber().hashCode();
        }
        if (getVehicleUsage() != null) {
            _hashCode += getVehicleUsage().hashCode();
        }
        if (getStandardPrice() != null) {
            _hashCode += getStandardPrice().hashCode();
        }
        if (getWholesalePrice() != null) {
            _hashCode += getWholesalePrice().hashCode();
        }
        if (getPortofdestination() != null) {
            _hashCode += getPortofdestination().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SapOutboundVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dcs.chrysler.com.cn/SapDcsService/", "sapOutboundVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engineNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "engineNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondaryStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secondaryStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shipDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("model");
        elemField.setXmlName(new javax.xml.namespace.QName("", "model"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelyear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modelyear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characteristicColour");
        elemField.setXmlName(new javax.xml.namespace.QName("", "characteristicColour"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characteristicTrim");
        elemField.setXmlName(new javax.xml.namespace.QName("", "characteristicTrim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characteristicFactoryStandardOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "characteristicFactoryStandardOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characteristicFactoryOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "characteristicFactoryOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("characteristicLocalOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "characteristicLocalOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soldto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soldto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoiceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invoiceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleUsage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vehicleUsage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("standardPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "standardPrice "));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wholesalePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wholesalePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portofdestination");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portofdestination"));
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
