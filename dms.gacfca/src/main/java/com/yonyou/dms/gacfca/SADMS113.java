package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;

/**
 * @description 未结算的工单作废上报接口
 * @author Administrator
 *
 */
public interface SADMS113 {
	public List<OpRepairOrderDTO> getSADMS113(String dealerCode,String roNo);
}
