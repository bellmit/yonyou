package com.yonyou.dms.DTO.gacfca;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class sedcsP01PartInfoDTO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	
	private String imatnr;//物料号
	private Double iqtyreq;//需求量
	
	public String getImatnr() {
		return imatnr;
	}
	public void setImatnr(String imatnr) {
		this.imatnr = imatnr;
	}
	public Double getIqtyreq() {
		return iqtyreq;
	}
	public void setIqtyreq(Double iqtyreq) {
		this.iqtyreq = iqtyreq;
	}	
}
