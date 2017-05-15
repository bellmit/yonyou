/**
 * 
 */
package com.infoeai.eai.wsServer.bsuv.lms;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DMSTODCS002VO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ecOrderNo;//官网订单号
	private String entityCode;//DMS端经销商代码
	private String tel;//客户联系电话
	private Date trailDate;//跟进日期
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getTrailDate() {
		return trailDate;
	}
	public void setTrailDate(Date trailDate) {
		this.trailDate = trailDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
