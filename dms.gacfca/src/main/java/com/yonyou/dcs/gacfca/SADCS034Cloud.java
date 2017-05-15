package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiCoOverTotalReportDTO;


/**
 * @Description: TODO(接收DMS上报超过60天未交车订单且未交车原因为空的订单) 
 * @author xuqinqin
 */
public interface SADCS034Cloud {
	public String receiveDate(List<TiCoOverTotalReportDTO> dtos) throws Exception;
}
