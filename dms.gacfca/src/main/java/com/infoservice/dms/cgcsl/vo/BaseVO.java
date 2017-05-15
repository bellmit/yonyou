package com.infoservice.dms.cgcsl.vo;

import java.io.Serializable;
import java.util.Date;

public class BaseVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entityCode; //下端：经销商代码  CHAR(8)  上端： 
	private Date downTimestamp; //下发的时间
	private Integer isValid; //是否有效
	private String errorMsg; //错误消息
	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}

}
