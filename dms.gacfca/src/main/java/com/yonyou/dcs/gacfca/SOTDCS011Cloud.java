package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsUFinancialDTO;
/**
 * 
 * Title:SOTDCS0011Cloud
 * Description: 更新客户信息（金融报价）(DMS更新)接收DMS->DCS
 * @author DC
 * @date 2017年4月14日 下午2:46:30
 * result msg 1：成功 0：失败
 */
public interface SOTDCS011Cloud {
	
	public String handleExecutor(List<TiDmsUFinancialDTO> dto) throws Exception;

}
