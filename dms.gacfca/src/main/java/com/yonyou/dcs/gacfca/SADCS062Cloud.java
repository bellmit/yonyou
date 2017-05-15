package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RenewalFailedDTO;

/**
 * 
 * Title:SADCS062Cloud
 * Description: 续保战败接口   接收
 * @author DC
 * @date 2017年4月10日 下午4:39:24
 * result msg 1：成功 0：失败
 */
public interface SADCS062Cloud {
	public String handleExecutor(List<RenewalFailedDTO> dtoList) throws Exception;

}
