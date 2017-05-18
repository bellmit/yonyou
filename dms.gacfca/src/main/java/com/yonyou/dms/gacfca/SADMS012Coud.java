package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairOrderDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 计划任务上报超过48小时未结算的索赔工单
 * @author Administrator
 *
 */
public interface SADMS012Coud {

	LinkedList<RoNotSettleAccountsRepairOrderDTO> getSADMS012(String dealerCode) throws ServiceBizException;

}
