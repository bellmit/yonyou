package com.yonyou.dcs.de;

/**
 * 经销商之间车辆调拨下发
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS007 {
	
	public String sendData(Long vehicleId,Long inDealerId,Long outDealerId) throws Exception ;

}
