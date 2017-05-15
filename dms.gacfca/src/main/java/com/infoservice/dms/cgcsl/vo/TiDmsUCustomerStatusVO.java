package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

import com.infoservice.dms.cgcsl.vo.BaseVO;

/**
 * 销售人员分配信息APP更新
 * author
 * 
 */
public class TiDmsUCustomerStatusVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private String uniquenessID;
	private Long FCAID;
	private String oppLevelID;
	private String giveUpType;
	private String compareCar;
	private String giveUpReason;
	private Date giveUpDate;
	private Date orderDate;
	private String dealerUserID;
	private String dealerCode;
	private Date buyCarDate;

	public String getUniquenessID() {
		return uniquenessID;
	}

	public void setUniquenessID(String uniquenessID) {
		this.uniquenessID = uniquenessID;
	}

	public Long getFCAID() {
		return FCAID;
	}

	public void setFCAID(Long fCAID) {
		FCAID = fCAID;
	}

	public String getOppLevelID() {
		return oppLevelID;
	}

	public void setOppLevelID(String oppLevelID) {
		this.oppLevelID = oppLevelID;
	}

	public String getGiveUpType() {
		return giveUpType;
	}

	public void setGiveUpType(String giveUpType) {
		this.giveUpType = giveUpType;
	}

	public String getCompareCar() {
		return compareCar;
	}

	public void setCompareCar(String compareCar) {
		this.compareCar = compareCar;
	}

	public String getGiveUpReason() {
		return giveUpReason;
	}

	public void setGiveUpReason(String giveUpReason) {
		this.giveUpReason = giveUpReason;
	}

	public Date getGiveUpDate() {
		return giveUpDate;
	}

	public void setGiveUpDate(Date giveUpDate) {
		this.giveUpDate = giveUpDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDealerUserID() {
		return dealerUserID;
	}

	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Date getBuyCarDate() {
		return buyCarDate;
	}

	public void setBuyCarDate(Date buyCarDate) {
		this.buyCarDate = buyCarDate;
	}

}
