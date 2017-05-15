/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.VehicleShippingDetailDto;

/**
 * @author Administrator
 *
 */
public class VehicleShippingVO extends BaseVO {
	  private static final long                   serialVersionUID = 1L;

	    private String                              entityCode;

	    private String                              shippingOrderNo;

	    private String                              poNo;

	    private Date                                shippingDate;

	    private Integer                             deliveryType;

	    private String                              shipperName;

	    private String                              shipperLicense;

	    private String                              deliverymanName;

	    private String                              deliverymanPhone;

	    private Date                                arrivingDate;

	    private String                              shippingAddress;

	    private String                              remark;

	    private Date                                downTimestamp;

	    private Integer                             isValid;
	    private String                              ecOrderNo;            // 电商订单号

	    private LinkedList<VehicleShippingDetailDto> vehicleVoList;

		public String getEntityCode() {
			return entityCode;
		}

		public void setEntityCode(String entityCode) {
			this.entityCode = entityCode;
		}

		public String getShippingOrderNo() {
			return shippingOrderNo;
		}

		public void setShippingOrderNo(String shippingOrderNo) {
			this.shippingOrderNo = shippingOrderNo;
		}

		public String getPoNo() {
			return poNo;
		}

		public void setPoNo(String poNo) {
			this.poNo = poNo;
		}

		public Date getShippingDate() {
			return shippingDate;
		}

		public void setShippingDate(Date shippingDate) {
			this.shippingDate = shippingDate;
		}

		public Integer getDeliveryType() {
			return deliveryType;
		}

		public void setDeliveryType(Integer deliveryType) {
			this.deliveryType = deliveryType;
		}

		public String getShipperName() {
			return shipperName;
		}

		public void setShipperName(String shipperName) {
			this.shipperName = shipperName;
		}

		public String getShipperLicense() {
			return shipperLicense;
		}

		public void setShipperLicense(String shipperLicense) {
			this.shipperLicense = shipperLicense;
		}

		public String getDeliverymanName() {
			return deliverymanName;
		}

		public void setDeliverymanName(String deliverymanName) {
			this.deliverymanName = deliverymanName;
		}

		public String getDeliverymanPhone() {
			return deliverymanPhone;
		}

		public void setDeliverymanPhone(String deliverymanPhone) {
			this.deliverymanPhone = deliverymanPhone;
		}

		public Date getArrivingDate() {
			return arrivingDate;
		}

		public void setArrivingDate(Date arrivingDate) {
			this.arrivingDate = arrivingDate;
		}

		public String getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Date getDownTimestamp() {
			return downTimestamp;
		}

		public void setDownTimestamp(Date downTimestamp) {
			this.downTimestamp = downTimestamp;
		}

		public Integer getIsValid() {
			return isValid;
		}

		public void setIsValid(Integer isValid) {
			this.isValid = isValid;
		}

		public String getEcOrderNo() {
			return ecOrderNo;
		}

		public void setEcOrderNo(String ecOrderNo) {
			this.ecOrderNo = ecOrderNo;
		}

		public LinkedList<VehicleShippingDetailDto> getVehicleVoList() {
			return vehicleVoList;
		}

		public void setVehicleVoList(LinkedList<VehicleShippingDetailDto> vehicleVoList) {
			this.vehicleVoList = vehicleVoList;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    

}
