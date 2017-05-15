package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SalesOrderDto;

/**
 * 经销商车辆实销数据
 * @author 夏威
 * result msg 1：成功 0：失败
 */
public interface SADCS008Cloud {
	
	public String receiveDate(List<SalesOrderDto> dto) throws Exception;

}
