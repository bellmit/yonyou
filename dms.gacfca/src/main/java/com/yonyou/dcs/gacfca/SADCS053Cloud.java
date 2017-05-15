package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.BigCustomerVisitItemDTO;

public interface SADCS053Cloud {
	public String handleExecutor(List<BigCustomerVisitItemDTO> dto) throws Exception;
}
