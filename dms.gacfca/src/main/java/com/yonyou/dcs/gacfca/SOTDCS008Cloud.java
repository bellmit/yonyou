package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerInfoDto;

/**
 * 
 * Title:SOTDCS008Cloud
 * Description: 更新客户信息（客户接待信息/需求分析）(DMS更新)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午6:21:24
 * result msg 1：成功 0：失败
 */
public interface SOTDCS008Cloud {
	
	public String handleExecutor(List<TiDmsUCustomerInfoDto> dto) throws Exception;


}
