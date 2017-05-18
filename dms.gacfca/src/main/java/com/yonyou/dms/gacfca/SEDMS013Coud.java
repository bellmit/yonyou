package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairDelDTO;

/**
 * @description  经销商上报未结算的索赔工单的变更删除业务
 * @author Administrator
 *
 */
public interface SEDMS013Coud {
	/**
	 * @description 针对工单的操作都在这个方法里面，
	 * 					如果要 修改工单时已上报未结算索赔工单的变更 ，就填写dealerCode,roNoInUpdate这两个参数，
	 * 					如果要 作废工单时已上报未结算索赔工单的变更，就填写dealerCode,roNoInDelete这两个参数,
	 * 					如果要 结算工单时已上报未结算索赔工单的变更，就填写dealerCode,roNoInBalance这两个参数,
	 * @param dealerCode
	 * @param roNoInUpdate
	 * @param roNoInDelete
	 * @param roNoInBalance
	 * @return
	 */
	public List<RoNotSettleAccountsRepairDelDTO> getSEDMS013(String dealerCode,String roNoInUpdate,String roNoInDelete,String roNoInBalance);
}
