package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.InsProposalDTO;

/**
 * 
 * Title:SADCS058Cloud
 * Description: 投保单上端接口
 * @author DC
 * @date 2017年4月7日 下午2:19:49
 * result msg 1：成功 0：失败
 */
public interface SADCS058Cloud {
	
	public String handleExecutor(List<InsProposalDTO> dtoList) throws Exception;

}
