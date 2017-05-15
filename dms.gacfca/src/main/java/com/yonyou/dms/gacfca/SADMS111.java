package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.LimitPriceSeriesDTO;

/**
 * @description  限价车系及维修类型数据下发接口
 * @author Administrator
 *
 */
public interface SADMS111 {
	public int getSADMS111(String dealerCode,List<LimitPriceSeriesDTO> list);
}
