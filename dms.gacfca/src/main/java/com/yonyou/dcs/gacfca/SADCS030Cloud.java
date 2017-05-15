package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmWxReserverInfoReportDTO;

/**
 * 微信预约信息DMS上报
 * 
 * @author lingxinglu
 *
 */
public interface SADCS030Cloud {
	public String handleExecutor(List<TmWxReserverInfoReportDTO> dto) throws Exception;
}
