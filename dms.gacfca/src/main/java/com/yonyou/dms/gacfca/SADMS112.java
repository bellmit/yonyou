package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;

/**
 * @description 计划任务每日上报未结算的工单接口
 * @author Administrator
 *
 */
public interface SADMS112 {
	public List<OpRepairOrderDTO> getSADMS112(String dealerCode);
}
