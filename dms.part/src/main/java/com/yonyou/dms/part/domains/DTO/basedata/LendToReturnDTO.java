/**
 * 
 */
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

/**
 * @author yangjie
 *
 */
public class LendToReturnDTO {

	private String lendNo;			//借出编号

	private String cusNo;			//客户编号

	private String cusName;			//客户姓名

	private List<Map<String, String>> dms_show;//归还数量

	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public List<Map<String, String>> getDms_show() {
		return dms_show;
	}

	public void setDms_show(List<Map<String, String>> dms_show) {
		this.dms_show = dms_show;
	}

}
