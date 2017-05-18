package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.RepairOrderDTO;


/**
 * @description 经销商上报车辆维修信息（维修结算上报）
 * @author Administrator
 *
 */
public interface SEDMS007Coud {
	public LinkedList<RepairOrderDTO> getSEDMS007(String dealerCode,Long userId,String roNo,String balanceNo,String refund);
}
