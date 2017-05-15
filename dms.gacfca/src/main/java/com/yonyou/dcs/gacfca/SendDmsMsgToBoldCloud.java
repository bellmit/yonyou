package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;

/**
 * 
 * Title:SendDmsMsgToBoldCloud
 * Description: 车主核实信息上报
 * @author DC
 * @date 2017年4月12日 下午6:04:58
 * result msg 1：成功 0：失败
 */
public interface SendDmsMsgToBoldCloud {
	
	public String handleExecutor(List<WarrantyRegistDTO> dto) throws Exception;
}
