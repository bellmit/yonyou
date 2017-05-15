package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;

/**
 * @Description:未结算工单接收接口
 * @author xuqinqin 
 */
public interface SEDCS112Cloud {
	
	public String receiveData(List<OpRepairOrderDTO> dtos) throws Exception;
	
}
