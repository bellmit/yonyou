package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairOrderDTO;

/**
 * @description 经销商上报未结算的索赔工单
 * @author Administrator
 *
 */
public interface SEDMS012 {
	public List<RoNotSettleAccountsRepairOrderDTO> getSEDMS012(String dealerCode,Long userId,String roNo,String roNoInRepair);
}
