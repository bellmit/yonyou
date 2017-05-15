package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDTO;


/**
 * @Description: TODO(PDI检查上报接口) 
 * @author xuqinqin
 */
public interface SADCS064Cloud {
	public String receiveDate(List<TtVehiclePdiResultDTO> dtos) throws Exception;
}
