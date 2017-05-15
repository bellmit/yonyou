package com.yonyou.dms.vehicle.domains.DTO.retailReportQuery;

/**
 * 
 * @author DC
 *
 */
public class RetailReportReviewPassDTO {
	
	private String nvdrIds;
	private String nvdrId;
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNvdrId() {
		return nvdrId;
	}

	public void setNvdrId(String nvdrId) {
		this.nvdrId = nvdrId;
	}

	public String getNvdrIds() {
		return nvdrIds;
	}

	public void setNvdrIds(String nvdrIds) {
		this.nvdrIds = nvdrIds;
	}

}
