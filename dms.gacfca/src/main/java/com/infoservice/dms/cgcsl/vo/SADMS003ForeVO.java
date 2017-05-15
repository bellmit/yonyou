package com.infoservice.dms.cgcsl.vo;
/**
 * 
* @ClassName: SADMS003ForeDTO 
* @Description: DCC建档客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午5:56:46 
*
 */
@SuppressWarnings("serial")
public class SADMS003ForeVO extends BaseVO{
	
	private String phone;
	private String dealerCode;
	private Long nid ;//上端主键
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	

}
