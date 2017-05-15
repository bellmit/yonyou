package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsSendVerificationDTO;

/**
 * 
 * Title:SOTDCS001Cloud
 * Description: 数据同步验证接口上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 上午11:12:39
 * result msg 1：成功 0：失败
 */
public interface SOTDCS001Cloud {
	public String handleExecutor(List<TiDmsSendVerificationDTO> dto) throws Exception;

}
