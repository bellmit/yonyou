package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDTO;


/**
 * @Description: TODO(PDI检查上报接口) 
 * @author xuqinqin
 */
public interface SADCS064Cloud extends BaseCloud{
	public String handleExecutor(List<TtVehiclePdiResultDTO> dtos) throws Exception;
}
